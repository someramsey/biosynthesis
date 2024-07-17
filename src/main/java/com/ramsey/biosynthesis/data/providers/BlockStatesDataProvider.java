package com.ramsey.biosynthesis.data.providers;

import com.ramsey.biosynthesis.Main;
import com.ramsey.biosynthesis.data.providers.block.BlockStateModelProvider;
import com.ramsey.biosynthesis.data.providers.block.common.branch.BranchBlockModelProvider;
import com.ramsey.biosynthesis.data.providers.block.common.stem.BranchStemBlockModelProvider;
import com.ramsey.biosynthesis.registrate.BlockRegistry;
import net.minecraft.data.DataGenerator;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.client.model.generators.BlockStateProvider;
import net.minecraftforge.client.model.generators.ConfiguredModel;
import net.minecraftforge.client.model.generators.MultiPartBlockStateBuilder;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.Function;

public class BlockStatesDataProvider extends BlockStateProvider {
    public BlockStatesDataProvider(DataGenerator gen, ExistingFileHelper exFileHelper) {
        super(gen, Main.MODID, exFileHelper);
    }

    @Override
    protected void registerStatesAndModels() {
        useVariantBuilder(BlockRegistry.branchBlock, BranchBlockModelProvider::getModelInstance);
        useVariantBuilder(BlockRegistry.stemBlock, BranchStemBlockModelProvider::getModelInstance);
    }

    private void useVariantBuilder(RegistryObject<Block> pBlock, BlockStateModelProvider pModelProvider) {
        getVariantBuilder(pBlock.get()).forAllStates(use(pModelProvider));
    }

    private Function<BlockState, ConfiguredModel[]> use(BlockStateModelProvider pModelProvider) {
        return (blockState) -> pModelProvider.getModel(blockState).build(this);
    }
}
