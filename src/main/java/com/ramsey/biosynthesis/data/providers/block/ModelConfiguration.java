package com.ramsey.biosynthesis.data.providers.block;

import com.ramsey.biosynthesis.data.providers.BlockStatesDataProvider;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.client.model.generators.ConfiguredModel;

public class ModelConfiguration {
    private String modelPath;
    private int rotationX;
    private int rotationY;

    public void setModelPath(String modelPath) {
        this.modelPath = modelPath;
    }

    public void setRotationX(int rotationX) {
        this.rotationX = rotationX;
    }

    public void setRotationY(int rotationY) {
        this.rotationY = rotationY;
    }

    public ConfiguredModel[] build(BlockStatesDataProvider provider) {
        return ConfiguredModel.builder()
            .modelFile(provider.models().getExistingFile(provider.modLoc(modelPath)))
            .rotationX(rotationX)
            .rotationY(rotationY)
            .build();
    }

    public interface Provider {
        ModelConfiguration getModelConfiguration(BlockState pBlockState);
    }
}
