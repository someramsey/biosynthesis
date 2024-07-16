package com.ramsey.biosynthesis.data.providers.block;

import net.minecraft.world.level.block.state.BlockState;

public interface BlockModelProvider {
    int getRotationX(BlockState blockState);

    int getRotationY(BlockState blockState);

    String getModelPath(BlockState blockState);
}
