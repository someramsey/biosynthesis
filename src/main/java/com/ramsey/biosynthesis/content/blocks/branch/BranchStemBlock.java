package com.ramsey.biosynthesis.content.blocks.branch;

import com.ramsey.biosynthesis.content.blocks.vessel.Alignment;
import com.ramsey.biosynthesis.content.blocks.vessel.VesselBlock;
import com.ramsey.biosynthesis.content.blocks.vessel.VesselHeadBlockEntity;
import com.ramsey.biosynthesis.data.providers.block.common.stem.BranchStemBlockShapeProvider;
import com.ramsey.biosynthesis.registry.BlockRegistry;
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
    public static final int MAX_AGE = 4;
    public static final IntegerProperty AgeProperty = IntegerProperty.create("age", 0, MAX_AGE);
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

    //TODO: implement actual branch navigation
    //TODO: make branches only spread on supporting blocks
    public static class Spreader extends VesselHeadBlockEntity.Spreader {
        private final boolean rooted;
        private final Orientation orientation;

        public int nextPart = -1;
        public int age = 0;
        public int absoluteAge = 0;

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
            absoluteAge++;

            if (this.age < BranchStemBlock.MAX_AGE) {
                BlockState blockState = pLevel.getBlockState(this.blockPos)
                    .setValue(AgeProperty, ++this.age);

                pLevel.setBlock(this.blockPos, blockState, 3);

                if (this.age == BranchStemBlock.MAX_AGE) {
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

        public void mature(Level pLevel, RandomSource pRandom) {
            Alignment alignment = Alignment.fromOrientation(this.orientation);
            BlockState vesselBlockState = BlockRegistry.vesselBlock.get().defaultBlockState()
                .setValue(VesselBlock.AlignmentProperty, alignment);

            pLevel.setBlock(this.blockPos, vesselBlockState, 3);

            VesselBlock.Spreader vesselSpreader = new VesselBlock.Spreader(this.head, this.blockPos);
            this.head.replacePart(vesselSpreader, this.id);

            if (nextPart == -1) {
                return;
            }

            VesselHeadBlockEntity.Spreader stemSpreader = this.head.parts.get(this.nextPart);

            BlockState branchBlockState = BlockRegistry.stemBlock.get().defaultBlockState()
                .setValue(BranchStemBlock.OrientationProperty, this.orientation)
                .setValue(BranchStemBlock.RootedProperty, true)
                .setValue(BranchStemBlock.AgeProperty, BranchStemBlock.MAX_AGE);

            pLevel.setBlock(stemSpreader.blockPos, branchBlockState, 3);
        }
    }
}
