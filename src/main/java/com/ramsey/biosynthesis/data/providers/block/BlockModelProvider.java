package com.ramsey.biosynthesis.data.providers.block;

import net.minecraft.world.level.block.state.BlockState;

public interface BlockModelProvider extends BlockModelKeyProvider {
    int getRotationX(BlockState blockState);

    int getRotationY(BlockState blockState);
}
