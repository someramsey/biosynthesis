package com.ramsey.biosynthesis.data.providers.block.common.branch;

import com.ramsey.biosynthesis.content.blocks.branch.BranchBlock;
import com.ramsey.biosynthesis.content.blocks.branch.OrientationState;
import com.ramsey.biosynthesis.data.providers.block.BlockShapeProvider;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.VoxelShape;

import java.util.stream.Stream;

public abstract class BranchBlockShapeProvider extends BlockShapeProvider {
    public static VoxelShape getShape(BlockState pState) {
        String modelKey = BranchBlockModelProvider.getModelKey(pState);
        Stream<UnbakedShapeFragment> shape = buildShape(modelKey)
            .map(pFragment -> transformShape(pFragment, pState));

        return bakeShape(shape);
    }

    private static UnbakedShapeFragment transformShape(UnbakedShapeFragment pFragment, BlockState pBlockState) {
        Direction direction = pBlockState.getValue(BranchBlock.FacingProperty);
        OrientationState orientation = pBlockState.getValue(BranchBlock.OrientationProperty);

        if (orientation != OrientationState.Horizontal) {
            pFragment.transform(pFragment.minX, 1 - pFragment.minZ, pFragment.minY, pFragment.maxX, 1 - pFragment.maxZ, pFragment.maxY);
        }

        BlockShapeProvider.rotateHorizontally(pFragment, direction);

        return pFragment;
    }

    private static Stream<UnbakedShapeFragment> buildShape(String pModelKey) {
        return switch (pModelKey) {
            case "both/down_down" -> Stream.of(
                box(5, 0, 0, 11, 1, 16),
                box(11, 0, 5, 16, 1, 11),
                box(0, 0, 5, 5, 1, 11),
                box(0, 0, 5, 0, 0, 11),
                box(16, 0, 5, 16, 0, 11),
                box(0, 0, 5, 0, 1, 11),
                box(16, 0, 5, 16, 1, 11)
            );

            case "both/down_flat" -> Stream.of(
                box(5, 0, 0, 11, 1, 16),
                box(11, 0, 5, 16, 1, 11),
                box(0, 0, 5, 5, 1, 11),
                box(0, 0, 5, 0, 0, 11),
                box(0, 0, 5, 0, 1, 11)
            );

            case "both/flat_flat" -> Stream.of(
                box(5, 0, 0, 11, 1, 16),
                box(11, 0, 5, 16, 1, 11),
                box(0, 0, 5, 5, 1, 11)
            );

            case "both/flat_up" -> Stream.of(
                box(5, 0, 0, 11, 1, 16),
                box(11, 0, 5, 15, 1, 11),
                box(0, 0, 5, 5, 1, 11),
                box(15, 1, 5, 16, 16, 11),
                box(15, 0, 5, 16, 1, 11)
            );

            case "both/up_flat" -> Stream.of(
                box(5, 0, 0, 11, 1, 16),
                box(11, 0, 5, 16, 1, 11),
                box(1, 0, 5, 5, 1, 11),
                box(0, 1, 5, 1, 16, 11),
                box(0, 0, 5, 1, 1, 11)
            );

            case "both/up_up" -> Stream.of(
                box(5, 0, 0, 11, 1, 16),
                box(11, 0, 5, 15, 1, 11),
                box(1, 0, 5, 5, 1, 11),
                box(0, 1, 5, 1, 16, 11),
                box(15, 1, 5, 16, 16, 11),
                box(0, 0, 5, 1, 1, 11),
                box(15, 0, 5, 16, 1, 11)
            );

            case "edge" -> Stream.of(
                box(5, 0, 0, 11, 1, 1)
            );

            case "left/down" -> Stream.of(
                box(5, 0, 0, 11, 1, 16),
                box(0, 0, 5, 0, 0, 11),
                box(0, 0, 5, 5, 1, 11),
                box(0, 0, 5, 0, 1, 11)
            );

            case "left/flat" -> Stream.of(
                box(5, 0, 0, 11, 1, 16),
                box(0, 0, 5, 5, 1, 11)
            );

            case "left/up" -> Stream.of(
                box(5, 0, 0, 11, 1, 16),
                box(0, 1, 5, 1, 16, 11),
                box(1, 0, 5, 5, 1, 11),
                box(0, 0, 5, 1, 1, 11)
            );

            case "right/down" -> Stream.of(
                box(5, 0, 0, 11, 1, 16),
                box(11, 0, 5, 16, 1, 11),
                box(16, 0, 5, 16, 1, 11),
                box(16, 0, 5, 16, 0, 11)
            );

            case "right/flat" -> Stream.of(
                box(5, 0, 0, 11, 1, 16),
                box(11, 0, 5, 16, 1, 11)
            );

            case "right/up" -> Stream.of(
                box(5, 0, 0, 11, 1, 16),
                box(11, 0, 5, 15, 1, 11),
                box(15, 0, 5, 16, 1, 11),
                box(15, 1, 5, 16, 16, 11)
            );

            case "straight/down" -> Stream.of(
                box(5, 0, 0, 11, 1, 16),
                box(5, 0, 0, 11, 1, 0),
                box(5, 0, 0, 11, 0, 0)
            );

            case "straight/flat" -> Stream.of(
                box(5, 0, 0, 11, 1, 16)
            );

            case "straight/up" -> Stream.of(
                box(5, 0, 1, 11, 1, 16),
                box(5, 0, 0, 11, 1, 1),
                box(5, 1, 0, 11, 16, 1)
            );

            default -> throw new IllegalArgumentException("Invalid model: " + pModelKey);
        };
    }
}

