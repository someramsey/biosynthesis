package com.ramsey.biosynthesis.data.providers.block;

import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.client.model.generators.BlockStateProvider;
import net.minecraftforge.client.model.generators.ConfiguredModel;

import java.util.function.Function;

public abstract class BlockStateMapper implements Function<BlockState, ConfiguredModel[]> {
    protected final BlockStateProvider provider;

    public BlockStateMapper(BlockStateProvider provider) {
        this.provider = provider;
    }
}
