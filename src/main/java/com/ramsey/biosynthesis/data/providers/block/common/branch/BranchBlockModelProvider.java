package com.ramsey.biosynthesis.data.providers.block.common.branch;

import com.ramsey.biosynthesis.content.blocks.branch.BranchBlock;
import com.ramsey.biosynthesis.content.blocks.branch.OrientationState;
import com.ramsey.biosynthesis.data.providers.block.BlockStateModelProvider;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.state.BlockState;

public abstract class BranchBlockModelProvider {
    public static int getRotationX(BlockState blockState) {
        OrientationState orientation = blockState.getValue(BranchBlock.OrientationProperty);

        return switch (orientation) {
            case Up -> 270;
            case Horizontal -> 0;
            case Down -> 90;
        };
    }

    public static int getRotationY(BlockState blockState) {
        Direction facing = blockState.getValue(BranchBlock.FacingProperty);
        OrientationState orientation = blockState.getValue(BranchBlock.OrientationProperty);

        int rotation = switch (facing) {
            case SOUTH -> 180;
            case EAST -> 90;
            case WEST -> 270;
            default -> 0;
        };

        if (orientation == OrientationState.Down) {
            rotation = (rotation + 180) % 360;
        }

        return rotation;
    }

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
            return "both/" + "up_up"; //TODO: add rest of the models
        } else if (left != BranchBlock.ConnectionState.None) {
            return "left/" + left.getSerializedName();
        } else {
            return "right/" + right.getSerializedName();
        }
    }

    public static BlockStateModelProvider.ModelInstance getModelInstance(BlockState blockState) {
        String modelPath = "block/branch/" + getModelKey(blockState);
        int rotationX = getRotationX(blockState);
        int rotationY = getRotationY(blockState);

        return new BlockStateModelProvider.ModelInstance(modelPath, rotationX, rotationY);
    }
}
