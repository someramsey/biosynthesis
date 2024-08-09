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

    public static class Spreader extends VesselHeadBlockEntity.Spreader {
        private int nextPart = -1;

        public Spreader(VesselHeadBlockEntity pHead, BlockPos pBlockPos) {
            super(pHead, pBlockPos);
        }

        private void extend(Level pLevel, BlockState pCurrentBlockState) {
            if (!pCurrentBlockState.getValue(RootedProperty)) {
                BlockState branchBlockState = BlockRegistry.branchBlock.get().defaultBlockState()
                    .setValue(BranchBlock.OrientationProperty, pCurrentBlockState.getValue(OrientationProperty));

                pLevel.setBlock(this.blockPos, branchBlockState, 3);
            }

            Orientation orientation = pCurrentBlockState.getValue(OrientationProperty);

            BlockPos nextStemBlockPos = orientation.relative(this.blockPos);
            BlockState nextStemBlockState = BlockRegistry.stemBlock.get().defaultBlockState()
                .setValue(OrientationProperty, orientation);

            pLevel.setBlock(nextStemBlockPos, nextStemBlockState, 3);

            Spreader spreader = new Spreader(this.head, this.blockPos);
            this.nextPart = this.head.addPart(spreader);
        }

        @Override
        public void spread(Level pLevel, RandomSource pRandom) {
            BlockState currentBlockState = pLevel.getBlockState(this.blockPos);
            int age = currentBlockState.getValue(AgeProperty);

            if (age < BranchStemBlock.MaxAge) {
                pLevel.setBlock(this.blockPos, currentBlockState.setValue(AgeProperty, age + 1), 3);

                if (age == BranchStemBlock.MaxAge - 1) {
                    extend(pLevel, currentBlockState);
                }
                return;
            }

            if (nextPart == -1) {
                extend(pLevel, currentBlockState);
                return;
            }

            VesselHeadBlockEntity.Spreader nextPart = this.head.parts.get(this.nextPart);
            nextPart.spread(pLevel, pRandom);
        }
    }
}
