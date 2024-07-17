package com.ramsey.biosynthesis.content.blocks;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.item.FallingBlockEntity;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.FallingBlock;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;

public class WasteBlock extends FallingBlock {
    public WasteBlock(Properties pProperties) {
        super(pProperties);
    }

    @Override
    public void tick(@NotNull BlockState pState, ServerLevel pLevel, BlockPos pPos, @NotNull RandomSource pRandom) {
        if (pPos.getY() >= pLevel.getMinBuildHeight()) {
            FallingBlockEntity.fall(pLevel, pPos, pState);
        }
    }


    @Override
    public int getDustColor(@NotNull BlockState pState, @NotNull BlockGetter pLevel, @NotNull BlockPos pPos) {
        return -11003575;
    }
}
