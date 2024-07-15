package com.ramsey.biosynthesis.data.providers.block;

import com.ramsey.biosynthesis.content.blocks.BranchBlock;
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
        BranchBlock.Side side = blockState.getValue(BranchBlock.SideProperty);
        BranchBlock.OrientationState orientation = blockState.getValue(BranchBlock.OrientationProperty);

        int rotation = switch (side) {
            case North -> 0;
            case South -> 180;
            case East -> 90;
            case West -> 270;
        };

        if (orientation == BranchBlock.OrientationState.Down) {
            rotation = (rotation + 180) % 360;
        }

        return rotation;
    }

    @Override
    public ModelInstance getModelInstance(BlockState blockState) {
        BranchBlock.ConnectionState left = blockState.getValue(BranchBlock.ConnectedLeftProperty);
        BranchBlock.ConnectionState right = blockState.getValue(BranchBlock.ConnectedRightProperty);
        BranchBlock.ConnectionState front = blockState.getValue(BranchBlock.ConnectedFrontProperty);

        String basePath = "block/branch/";

        if (left == BranchBlock.ConnectionState.None && right == BranchBlock.ConnectionState.None) {
            if (front == BranchBlock.ConnectionState.None) {
                return new ModelInstance(basePath + "edge", null);
            }

            return new ModelInstance(basePath + "straight/" + front.getSerializedName(), null);
        } else if (left != BranchBlock.ConnectionState.None && right != BranchBlock.ConnectionState.None) {
            return new ModelInstance(basePath + "both/" + "up_up", null);
        } else if (left != BranchBlock.ConnectionState.None) {
            return new ModelInstance(basePath + "left/" + left.getSerializedName(), null);
        } else {
            return new ModelInstance(basePath + "right/" + right.getSerializedName(), null);
        }
    }
}
