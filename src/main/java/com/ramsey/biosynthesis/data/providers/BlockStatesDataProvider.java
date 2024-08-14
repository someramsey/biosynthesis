package com.ramsey.biosynthesis.data.providers;

import com.ramsey.biosynthesis.Main;
import com.ramsey.biosynthesis.data.providers.block.ModelConfiguration;
import com.ramsey.biosynthesis.data.providers.block.common.branch.BranchBlockModelProvider;
import com.ramsey.biosynthesis.data.providers.block.common.stem.BranchStemBlockModelProvider;
import com.ramsey.biosynthesis.registry.BlockRegistry;
import net.minecraft.data.DataGenerator;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.client.model.generators.BlockStateProvider;
import net.minecraftforge.client.model.generators.ConfiguredModel;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.Function;

public class BlockStatesDataProvider extends BlockStateProvider {
    public BlockStatesDataProvider(DataGenerator gen, ExistingFileHelper exFileHelper) {
        super(gen, Main.MODID, exFileHelper);
    }

    @Override
    protected void registerStatesAndModels() {
        registerVariantBuilder(BlockRegistry.branchBlock, BranchBlockModelProvider::getModelConfiguration);
        registerVariantBuilder(BlockRegistry.stemBlock, BranchStemBlockModelProvider::getModelConfiguration);
    }

    private void registerVariantBuilder(RegistryObject<Block> pBlock, ModelConfiguration.Provider pProvider) {
        getVariantBuilder(pBlock.get()).forAllStates(getMapper(pProvider));
    }

    private Function<BlockState, ConfiguredModel[]> getMapper(ModelConfiguration.Provider pProvider) {
        return (blockState) -> pProvider.getModelConfiguration(blockState).build(this);
    }
}
