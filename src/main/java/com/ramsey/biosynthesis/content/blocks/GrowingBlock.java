package com.ramsey.biosynthesis.content.blocks;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.state.BlockState;

public interface GrowingBlock {
    void grow(ServerLevel pLevel, BlockState pState, BlockPos pPos, RandomSource pRandom);
}
