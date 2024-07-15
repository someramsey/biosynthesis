package com.ramsey.biosynthesis.data.providers.block;

import com.ramsey.biosynthesis.content.blocks.BranchBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.client.model.generators.BlockStateProvider;
import net.minecraftforge.client.model.generators.ConfiguredModel;
import net.minecraftforge.client.model.generators.ModelFile;

public class BranchBlockStateMapper extends BlockStateMapper {
    public BranchBlockStateMapper(BlockStateProvider provider) {
        super(provider);
    }

    @Override
    public ConfiguredModel[] apply(BlockState blockState) {
        System.out.println(blockState);
        System.out.println("dw");

        BranchBlock.ConnectionState left = blockState.getValue(BranchBlock.ConnectedLeftProperty);
        BranchBlock.ConnectionState right = blockState.getValue(BranchBlock.ConnectedRightProperty);






        ModelFile modelFile = provider.models().getExistingFile(provider.modLoc("block/branch/straight"));
        return ConfiguredModel.builder().modelFile(modelFile).build();
    }
}
