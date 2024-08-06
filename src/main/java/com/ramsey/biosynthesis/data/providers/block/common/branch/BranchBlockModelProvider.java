package com.ramsey.biosynthesis.data.providers.block.common.branch;

import com.ramsey.biosynthesis.content.blocks.branch.BranchBlock;
import com.ramsey.biosynthesis.content.blocks.branch.Orientation;
import com.ramsey.biosynthesis.data.providers.block.ModelConfiguration;
import net.minecraft.world.level.block.state.BlockState;

public abstract class BranchBlockModelProvider {
    public static String getModelKey(BlockState pBlockState) {
        BranchBlock.ConnectionState front = pBlockState.getValue(BranchBlock.FrontConnectionProperty);
        boolean left = pBlockState.getValue(BranchBlock.ConnectedLeftProperty);
        boolean right = pBlockState.getValue(BranchBlock.ConnectedRightProperty);

        return front.getSerializedName() + "_" + left + "_" + right;
    }

    public static ModelConfiguration getModelConfiguration(BlockState pBlockState) {
        ModelConfiguration configuration = new ModelConfiguration();
        Orientation orientation = pBlockState.getValue(BranchBlock.OrientationProperty);

        configuration.setRotationX(orientation.getXRotation());
        configuration.setRotationY(orientation.getYRotation());
        configuration.setModelPath("block/branch/" + getModelKey(pBlockState));


        return configuration;
    }
}
