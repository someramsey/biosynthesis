package com.ramsey.biosynthesis.content.blocks.branch;

import com.ramsey.biosynthesis.content.blocks.vessel.VesselHeadBlockEntity;
import com.ramsey.biosynthesis.data.providers.block.common.stem.BranchStemBlockShapeProvider;
import com.ramsey.biosynthesis.registrate.BlockRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.NotNull;

public class BranchStemBlock extends Block {
    public static final int MaxAge = 4;
    public static final IntegerProperty AgeProperty = IntegerProperty.create("age", 0, MaxAge);
    public static final BooleanProperty RootedProperty = BooleanProperty.create("rooted");
    public static final EnumProperty<Orientation> OrientationProperty = EnumProperty.create("orientation", Orientation.class);

    public BranchStemBlock(Properties pProperties) {
        super(pProperties);

        this.registerDefaultState(
            this.stateDefinition.any()
                .setValue(AgeProperty, 0)
                .setValue(RootedProperty, false)
                .setValue(OrientationProperty, Orientation.UpN)
        );
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.@NotNull Builder<Block, BlockState> pBuilder) {
        super.createBlockStateDefinition(pBuilder);

        pBuilder.add(AgeProperty);
        pBuilder.add(OrientationProperty);
        pBuilder.add(RootedProperty);
    }

    @Override
    public @NotNull VoxelShape getShape(@NotNull BlockState pState, @NotNull BlockGetter pLevel, @NotNull BlockPos pPos, @NotNull CollisionContext pContext) {
        return BranchStemBlockShapeProvider.buildShape(pState);
    }

    //TODO: make get block or get state calls safe
    //TODO: implement actual branch navigation
    //TODO: make branches only spread on supporting blocks
    //TODO: make sure vessel only places above if its neighbours are vessels
    public static class Spreader extends VesselHeadBlockEntity.Spreader {
        private final boolean rooted;
        private final Orientation orientation;
        private int nextPart = -1;
        private int age = 0;

        public Spreader(VesselHeadBlockEntity pHead, BlockPos pBlockPos, Orientation pOrientation, boolean pRooted) {
            super(pHead, pBlockPos);
            this.orientation = pOrientation;
            this.rooted = pRooted;
        }

        private void extend(Level pLevel) {
            if (!this.rooted) {
                BlockState branchBlockState = BlockRegistry.branchBlock.get().defaultBlockState()
                    .setValue(BranchBlock.OrientationProperty, this.orientation);

                pLevel.setBlock(this.blockPos, branchBlockState, 3);
            }

            Orientation nextStemOrientation = this.orientation;
            BlockPos nextStemBlockPos = nextStemOrientation.relative(this.blockPos);
            BlockState nextStemBlockState = BlockRegistry.stemBlock.get().defaultBlockState()
                .setValue(OrientationProperty, nextStemOrientation);

            pLevel.setBlock(nextStemBlockPos, nextStemBlockState, 3);

            Spreader spreader = new Spreader(this.head, nextStemBlockPos, nextStemOrientation, false);
            this.nextPart = this.head.addPart(spreader);
        }

        @Override
        public void spread(Level pLevel, RandomSource pRandom) {
            if (this.age < BranchStemBlock.MaxAge) {
                BlockState blockState = pLevel.getBlockState(this.blockPos)
                    .setValue(AgeProperty, ++this.age);

                pLevel.setBlock(this.blockPos, blockState, 3);

                if (this.age == BranchStemBlock.MaxAge) {
                    extend(pLevel);
                }
                return;
            }

            if (nextPart == -1) {
                extend(pLevel);
                return;
            }

            VesselHeadBlockEntity.Spreader nextPart = this.head.parts.get(this.nextPart);
            nextPart.spread(pLevel, pRandom);
        }
    }
}
