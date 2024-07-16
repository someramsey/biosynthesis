package com.ramsey.biosynthesis.data.providers.block;

import com.ramsey.biosynthesis.content.blocks.BranchBlock;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.client.model.generators.BlockStateProvider;

public class BranchBlockStateMapper extends BlockStateMapper {
    public BranchBlockStateMapper(BlockStateProvider provider) {
        super(provider);
    }

    @Override
    public int getRotationX(BlockState blockState) {
        BranchBlock.OrientationState orientation = blockState.getValue(BranchBlock.OrientationProperty);

        return switch (orientation) {
            case Up -> 270;
            case Horizontal -> 0;
            case Down -> 90;
        };
    }

    @Override
    public int getRotationY(BlockState blockState) {
        Direction facing = blockState.getValue(BranchBlock.FacingProperty);
        BranchBlock.OrientationState orientation = blockState.getValue(BranchBlock.OrientationProperty);

        int rotation = switch (facing) {
            case SOUTH -> 180;
            case EAST -> 90;
            case WEST -> 270;
            default -> 0;
        };

        if (orientation == BranchBlock.OrientationState.Down) {
            rotation = (rotation + 180) % 360;
        }

        return rotation;
    }

    @Override
    public String getModelPath(BlockState blockState) {
        BranchBlock.ConnectionState left = blockState.getValue(BranchBlock.ConnectedLeftProperty);
        BranchBlock.ConnectionState right = blockState.getValue(BranchBlock.ConnectedRightProperty);
        BranchBlock.ConnectionState front = blockState.getValue(BranchBlock.ConnectedFrontProperty);

        String basePath = "block/branch/";

        if (left == BranchBlock.ConnectionState.None && right == BranchBlock.ConnectionState.None) {
            if (front == BranchBlock.ConnectionState.None) {
                return basePath + "edge";
            }

            return basePath + "straight/" + front.getSerializedName();
        } else if (left != BranchBlock.ConnectionState.None && right != BranchBlock.ConnectionState.None) {
            return basePath + "both/" + "up_up";
        } else if (left != BranchBlock.ConnectionState.None) {
            return basePath + "left/" + left.getSerializedName();
        } else {
            return basePath + "right/" + right.getSerializedName();
        }
    }
}
