package com.ramsey.biosynthesis.data.providers.block.common.branch;

import com.ramsey.biosynthesis.content.blocks.branch.BranchBlock;
import com.ramsey.biosynthesis.content.blocks.branch.Orientation;
import com.ramsey.biosynthesis.data.providers.block.BlockStateModelProvider;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.state.BlockState;

public abstract class BranchBlockModelProvider implements BlockStateModelProvider {
    public static String getModelKey(BlockState pBlockState) {
        BranchBlock.ConnectionState left = pBlockState.getValue(BranchBlock.ConnectedLeftProperty);
        BranchBlock.ConnectionState right = pBlockState.getValue(BranchBlock.ConnectedRightProperty);
        BranchBlock.ConnectionState front = pBlockState.getValue(BranchBlock.ConnectedFrontProperty);

        if (left == BranchBlock.ConnectionState.None && right == BranchBlock.ConnectionState.None) {
            if (front == BranchBlock.ConnectionState.None) {
                return "edge";
            }

            return "straight/" + front.getSerializedName();
        } else if (left != BranchBlock.ConnectionState.None && right != BranchBlock.ConnectionState.None) {
            return "both/" + left.getSerializedName() + "_" + right.getSerializedName();
        } else if (left != BranchBlock.ConnectionState.None) {
            return "left/" + left.getSerializedName();
        } else {
            return "right/" + right.getSerializedName();
        }
    }

    public static BlockStateModelProvider.ModelInstance getModelInstance(BlockState blockState) {
        String modelPath = "block/branch/" + getModelKey(blockState);
        Orientation orientation = blockState.getValue(BranchBlock.OrientationProperty);

        return new BlockStateModelProvider.ModelInstance(modelPath, orientation.getXRotation(), orientation.getYRotation());
    }
}
