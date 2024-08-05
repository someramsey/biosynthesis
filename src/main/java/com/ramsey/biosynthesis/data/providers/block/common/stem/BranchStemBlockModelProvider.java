package com.ramsey.biosynthesis.data.providers.block.common.stem;

import com.ramsey.biosynthesis.content.blocks.branch.BranchBlock;
import com.ramsey.biosynthesis.content.blocks.branch.BranchStemBlock;
import com.ramsey.biosynthesis.content.blocks.branch.Orientation;
import com.ramsey.biosynthesis.data.providers.block.ModelConfiguration;
import net.minecraft.world.level.block.state.BlockState;

public abstract class BranchStemBlockModelProvider {
    private static String getModelKey(BlockState blockState) {
        StringBuilder builder = new StringBuilder();

        if (blockState.getValue(BranchStemBlock.RootedProperty)) {
            builder.append("rooted/");
        } else {
            builder.append("unrooted/");
        }

        builder.append("age");
        builder.append(blockState.getValue(BranchStemBlock.AgeProperty));

        return builder.toString();
    }

    public static ModelConfiguration getModelConfiguration(BlockState blockState) {
        ModelConfiguration configuration = new ModelConfiguration();
        Orientation orientation = blockState.getValue(BranchBlock.OrientationProperty);

        configuration.setRotationX(orientation.getXRotation());
        configuration.setRotationY(orientation.getYRotation());
        configuration.setModelPath("block/stem/" + getModelKey(blockState));

        return configuration;
    }
}
