package com.ramsey.biosynthesis.data.providers.block;

import net.minecraft.world.level.block.state.BlockState;

public interface BlockModelKeyProvider {
    String getModelKey(BlockState pBlockState);
}
