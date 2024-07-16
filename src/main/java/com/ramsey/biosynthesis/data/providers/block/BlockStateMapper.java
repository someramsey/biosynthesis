package com.ramsey.biosynthesis.data.providers.block;

import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.client.model.generators.BlockStateProvider;
import net.minecraftforge.client.model.generators.ConfiguredModel;
import net.minecraftforge.client.model.generators.ModelFile;

import java.util.function.Function;

public abstract class BlockStateMapper implements Function<BlockState, ConfiguredModel[]> {
    protected final BlockStateProvider provider;

    public BlockStateMapper(BlockStateProvider provider) {
        this.provider = provider;
    }

    public abstract int getRotationX(BlockState blockState);
    public abstract int getRotationY(BlockState blockState);
    public abstract String getModelPath(BlockState blockState);

    @Override
    public ConfiguredModel[] apply(BlockState blockState) {
        String modelPath = getModelPath(blockState);
        ModelFile file = provider.models().getExistingFile(provider.modLoc(modelPath));

        int rotationX = getRotationX(blockState);
        int rotationY = getRotationY(blockState);

        return ConfiguredModel.builder()
            .modelFile(file)
            .rotationX(rotationX)
            .rotationY(rotationY)
            .build();
    }
}
