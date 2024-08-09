package com.ramsey.biosynthesis.content.blocks.vessel;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class VesselHeadBlock extends VesselBlock {
    public VesselHeadBlock(Properties pProperties) {
        super(pProperties);
    }

    @Override
    public boolean isRandomlyTicking(@NotNull BlockState pState) {
        return true;
    }

    @Override
    public void randomTick(@NotNull BlockState pState, @NotNull ServerLevel pLevel, @NotNull BlockPos pPos, @NotNull RandomSource pRandom) {
        if (pLevel.isClientSide()) {
            return;
        }

        BlockEntity blockEntity = pLevel.getBlockEntity(pPos);

        if (blockEntity instanceof VesselHeadBlockEntity vesselHeadBlockEntity) {
            vesselHeadBlockEntity.base.spread(pLevel, pRandom);
        }
    }

    @Override
    public @Nullable BlockEntity newBlockEntity(@NotNull BlockPos pPos, @NotNull BlockState pState) {
        return new VesselHeadBlockEntity(pPos, pState);
    }
}
