package com.ramsey.biosynthesis.data.providers;

import com.ramsey.biosynthesis.Main;
import com.ramsey.biosynthesis.data.providers.block.BlockStateModelProvider;
import com.ramsey.biosynthesis.data.providers.block.common.branch.BranchBlockModelProvider;
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
        getVariantBuilder(BlockRegistry.branchBlock.get()).forAllStates(use(BranchBlockModelProvider::getModelInstance));
    }

    private Function<BlockState, ConfiguredModel[]> use(BlockStateModelProvider pModelProvider) {
        return (blockState) -> pModelProvider.getModel(blockState).build(this);
    }
}
