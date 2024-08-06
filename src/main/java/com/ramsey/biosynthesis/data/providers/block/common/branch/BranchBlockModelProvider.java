package com.ramsey.biosynthesis.data.providers.block.common.branch;

import com.ramsey.biosynthesis.content.blocks.branch.BranchBlock;
import com.ramsey.biosynthesis.content.blocks.branch.Orientation;
import com.ramsey.biosynthesis.data.providers.block.ModelConfiguration;
import net.minecraft.world.level.block.state.BlockState;

public abstract class BranchBlockModelProvider {
    public static String getModelKey(BlockState pBlockState) {
        BranchBlock.ConnectionState front = pBlockState.getValue(BranchBlock.FrontConnectionProperty);
        BranchBlock.SideConnectionState side = pBlockState.getValue(BranchBlock.SideConnectionProperty);

        if (front != BranchBlock.ConnectionState.None && side != BranchBlock.SideConnectionState.None) {
            return front.getSerializedName() + "_" + side.getSerializedName();
        } else if (front != BranchBlock.ConnectionState.None) {
            return front.getSerializedName();
        } else if (side != BranchBlock.SideConnectionState.None) {
            return side.getSerializedName();
        }

        return "edge";
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
