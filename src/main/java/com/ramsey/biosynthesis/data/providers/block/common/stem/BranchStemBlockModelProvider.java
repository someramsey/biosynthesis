package com.ramsey.biosynthesis.data.providers.block.common.stem;

import com.ramsey.biosynthesis.content.blocks.branch.BranchBlock;
import com.ramsey.biosynthesis.content.blocks.branch.BranchStemBlock;
import com.ramsey.biosynthesis.content.blocks.branch.Orientation;
import com.ramsey.biosynthesis.data.providers.block.BlockStateModelProvider;
import net.minecraft.world.level.block.state.BlockState;

public abstract class BranchStemBlockModelProvider implements BlockStateModelProvider {
    public static ModelInstance getModelInstance(BlockState blockState) {
        String basePath = "block/stem/";

        Orientation orientation = blockState.getValue(BranchBlock.OrientationProperty);
        Boolean rooted = blockState.getValue(BranchStemBlock.RootedProperty);

        if (rooted) {
            basePath += "rooted/";
        } else {
            basePath += "unrooted/";
        }

        String modelPath = basePath + "age" + blockState.getValue(BranchStemBlock.AgeProperty);

        return new ModelInstance(modelPath, orientation.getXRotation(), orientation.getYRotation());
    }
}
