package com.ramsey.biosynthesis.data.providers;

import com.ramsey.biosynthesis.Main;
import com.ramsey.biosynthesis.data.providers.block.BranchBlockStateMapper;
import com.ramsey.biosynthesis.registrate.BlockRegistry;
import net.minecraft.data.DataGenerator;
import net.minecraftforge.client.model.generators.BlockStateProvider;
import net.minecraftforge.client.model.generators.ConfiguredModel;
import net.minecraftforge.client.model.generators.ModelFile;
import net.minecraftforge.common.data.ExistingFileHelper;

public class BlockStatesDataProvider extends BlockStateProvider {
    public BlockStatesDataProvider(DataGenerator gen, ExistingFileHelper exFileHelper) {
        super(gen, Main.MODID, exFileHelper);
    }

    @Override
    protected void registerStatesAndModels() {
        getVariantBuilder(BlockRegistry.branchBlock.get()).forAllStates(new BranchBlockStateMapper(this));
    }
}
