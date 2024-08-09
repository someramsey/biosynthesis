package com.ramsey.biosynthesis.content.blocks.vessel;

import com.ramsey.biosynthesis.content.blocks.branch.BranchStemBlock;
import com.ramsey.biosynthesis.data.providers.block.common.vessel.VesselBlockShapeProvider;
import com.ramsey.biosynthesis.registrate.BlockRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class VesselBlock extends BaseEntityBlock {
    public static final int MaxAge = 5;
    public static EnumProperty<Alignment> AlignmentProperty = EnumProperty.create("alignment", Alignment.class);
    public static IntegerProperty AgeProperty = IntegerProperty.create("age", 0, MaxAge);

    public VesselBlock(Properties pProperties) {
        super(pProperties);

        this.registerDefaultState(stateDefinition.any()
            .setValue(VesselBlock.AlignmentProperty, Alignment.Middle)
            .setValue(VesselBlock.AgeProperty, 0));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> pBuilder) {
        pBuilder.add(AlignmentProperty);
        pBuilder.add(AgeProperty);
    }

    @Override
    public @NotNull RenderShape getRenderShape(@NotNull BlockState pState) {
        return RenderShape.ENTITYBLOCK_ANIMATED;
    }

    @Override
    public @NotNull VoxelShape getShape(@NotNull BlockState pState, @NotNull BlockGetter pLevel, @NotNull BlockPos pPos, @NotNull CollisionContext pContext) {
        return VesselBlockShapeProvider.buildShape(pState);
    }

    @Override
    public @Nullable BlockEntity newBlockEntity(@NotNull BlockPos pPos, @NotNull BlockState pState) {
        return new VesselBlockEntity(pPos, pState);
    }

    public static class Spreader extends VesselHeadBlockEntity.Spreader {
        public Spreader(VesselHeadBlockEntity pHead, BlockPos pPos) {
            super(pHead, pPos, 4);
        }

        private void grow(Level pLevel) {
            BlockState blockState = pLevel.getBlockState(blockPos);
            int age = blockState.getValue(VesselBlock.AgeProperty);

            pLevel.setBlock(blockPos, blockState.setValue(VesselBlock.AgeProperty, age + 1), 3);
        }

        @Override
        public void spread(Level pLevel, RandomSource pRandom) {
            BlockState blockState = pLevel.getBlockState(blockPos);

            if (blockState.getValue(VesselBlock.AgeProperty) < VesselBlock.MaxAge) {
                grow(pLevel);
            }

            if (pRandom.nextBoolean()) {
                this.propagate(pRandom);
                return;
            }

            if (this.hasUnsetNeighbours()) {
                BlockPos stemPos = blockPos.relative(Direction.getRandom(pRandom));
                BlockState stemBlockState = BlockRegistry.stemBlock.get().defaultBlockState()
                    .setValue(BranchStemBlock.RootedProperty, true);

                pLevel.setBlock(stemPos, stemBlockState, 3);

                this.connect(new BranchStemBlock.Spreader(head, stemPos));
            }
        }
    }
}
