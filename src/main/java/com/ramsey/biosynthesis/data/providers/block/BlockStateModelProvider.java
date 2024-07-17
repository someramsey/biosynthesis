package com.ramsey.biosynthesis.data.providers.block;

import com.ramsey.biosynthesis.data.providers.BlockStatesDataProvider;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.client.model.generators.ConfiguredModel;

public interface BlockStateModelProvider {
    ModelInstance getModel(BlockState pBlockState);

    record ModelInstance(String modelPath, int rotationX, int rotationY) {
        public ConfiguredModel[] build(BlockStatesDataProvider provider) {
            return ConfiguredModel.builder()
                .modelFile(provider.models().getExistingFile(provider.modLoc(modelPath)))
                .rotationX(rotationX)
                .rotationY(rotationY)
                .build();
        }
    }
}
