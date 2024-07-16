package com.ramsey.biosynthesis.data.providers;

import com.ramsey.biosynthesis.Main;
import com.ramsey.biosynthesis.data.providers.block.BlockModelProvider;
import com.ramsey.biosynthesis.data.providers.block.branch.BranchBlockModelProvider;
import com.ramsey.biosynthesis.registrate.BlockRegistry;
import net.minecraft.data.DataGenerator;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.client.model.generators.BlockStateProvider;
import net.minecraftforge.client.model.generators.ConfiguredModel;
import net.minecraftforge.common.data.ExistingFileHelper;

import java.util.function.Function;

public class BlockStatesDataProvider extends BlockStateProvider {
    public BlockStatesDataProvider(DataGenerator gen, ExistingFileHelper exFileHelper) {
        super(gen, Main.MODID, exFileHelper);
    }

    @Override
    protected void registerStatesAndModels() {
        getVariantBuilder(BlockRegistry.branchBlock.get()).forAllStates(applyProvider(new BranchBlockModelProvider(), "block/branch/"));
    }

    private Function<BlockState, ConfiguredModel[]> applyProvider(BlockModelProvider modelProvider, String pBasePath) {
        return (blockState) -> {
            String modelPath = pBasePath + modelProvider.getModelPath(blockState);
            int rotationX = modelProvider.getRotationX(blockState);
            int rotationY = modelProvider.getRotationY(blockState);

            return ConfiguredModel.builder()
                .modelFile(models().getExistingFile(modLoc(modelPath)))
                .rotationX(rotationX)
                .rotationY(rotationY)
                .build();
        };
    }
}
