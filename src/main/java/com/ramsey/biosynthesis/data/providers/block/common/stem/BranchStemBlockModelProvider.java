package com.ramsey.biosynthesis.data.providers.block.common.stem;

import com.ramsey.biosynthesis.content.blocks.branch.BranchBlock;
import com.ramsey.biosynthesis.content.blocks.branch.BranchStemBlock;
import com.ramsey.biosynthesis.content.blocks.branch.OrientationState;
import com.ramsey.biosynthesis.data.providers.block.BlockStateModelProvider;
import com.ramsey.biosynthesis.data.providers.block.common.branch.BranchBlockModelProvider;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.StemBlock;
import net.minecraft.world.level.block.state.BlockState;

public abstract class BranchStemBlockModelProvider {
    public static BlockStateModelProvider.ModelInstance getModelInstance(BlockState blockState) {
        String modelPath = "block/stem/age" + blockState.getValue(BranchStemBlock.AgeProperty);
        int rotationX = BranchBlockModelProvider.getRotationX(blockState);
        int rotationY = BranchBlockModelProvider.getRotationY(blockState);

        return new BlockStateModelProvider.ModelInstance(modelPath, rotationX, rotationY);
    }
}
