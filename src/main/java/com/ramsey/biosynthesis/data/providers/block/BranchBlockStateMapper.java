package com.ramsey.biosynthesis.data.providers.block;

import com.ramsey.biosynthesis.content.blocks.BranchBlock;
import net.minecraft.core.Direction;
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
        ModelFile modelFile = provider.models().getExistingFile(provider.modLoc(getModelPath(blockState)));

        int rotationX = getRotationX(blockState);
        int rotationY = getRotationY(blockState);

        if (rotationX == 90) {
            rotationY = (rotationY + 180) % 360;
        }

        return ConfiguredModel.builder()
            .modelFile(modelFile)
            .rotationX(rotationX)
            .rotationY(rotationY)
            .build();
    }

    private static String getModelPath(BlockState blockState) {
        String basePath = "block/branch/";

        BranchBlock.ConnectionState left = blockState.getValue(BranchBlock.ConnectedLeftProperty);
        BranchBlock.ConnectionState right = blockState.getValue(BranchBlock.ConnectedRightProperty);
        BranchBlock.ConnectionState front = blockState.getValue(BranchBlock.ConnectedFrontProperty);

        if (left == BranchBlock.ConnectionState.None && right == BranchBlock.ConnectionState.None) {
            if(front == BranchBlock.ConnectionState.None) {
                return basePath + "edge";
            }

            return basePath + "straight/" + front.getSerializedName();
        } else if (left != BranchBlock.ConnectionState.None && right != BranchBlock.ConnectionState.None) {
//            return basePath + "both/" + left.getSerializedName() + "_" + right.getSerializedName();
            return basePath + "both/" + "up_up";
        } else if (left != BranchBlock.ConnectionState.None) {
            return basePath + "left/" + left.getSerializedName();
        } else {
            return basePath + "right/" + right.getSerializedName();
        }
    }

    private static int getRotationY(BlockState blockState) {
        BranchBlock.Side side = blockState.getValue(BranchBlock.SideProperty);

        return switch (side) {
            case North -> 0;
            case South -> 180;
            case East -> 90;
            case West -> 270;
        };
    }

    private static int getRotationX(BlockState blockState) {
        BranchBlock.OrientationState orientation = blockState.getValue(BranchBlock.OrientationProperty);

        return switch (orientation) {
            case Up -> 270;
            case Horizontal -> 0;
            case Down -> 90;
        };
    }
}
