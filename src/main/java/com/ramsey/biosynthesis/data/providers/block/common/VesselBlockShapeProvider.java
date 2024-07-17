package com.ramsey.biosynthesis.data.providers.block.common;

import com.ramsey.biosynthesis.content.blocks.vessel.VesselBlock;
import com.ramsey.biosynthesis.data.providers.block.BlockShapeProvider;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.VoxelShape;

import java.util.List;
import java.util.stream.Stream;

public abstract class VesselBlockShapeProvider extends BlockShapeProvider {
    public static VoxelShape getShape(BlockState pState) {
        Stream<UnbakedShapeFragment> shape = buildShape(pState)
            .map(pFragment -> transformShape(pFragment, pState));

        return bakeShape(shape);
    }

    private static UnbakedShapeFragment transformShape(UnbakedShapeFragment pFragment, BlockState pBlockState) {
        Direction direction = pBlockState.getValue(VesselBlock.FacingProperty);
        BlockShapeProvider.transformByDirection(pFragment, direction);

        return pFragment;
    }

    private static Stream<UnbakedShapeFragment> buildShape(BlockState pState) {
        Direction direction = pState.getValue(VesselBlock.FacingProperty);
        int age = pState.getValue(VesselBlock.AgeProperty);

        if (age == 5) {
            return Stream.of(
                box(0, 0, 0, 16, 16, 16)
            );
        }

        if (direction == Direction.UP) {
            return List.of(
                Stream.of(
                    box(9, 0, 9, 15, 6, 15),
                    box(8, 0, 1, 13, 5, 6),
                    box(2, 0, 10, 7, 4, 15),
                    box(4, 0, 4, 12, 7, 12)
                ),
                Stream.of(
                    box(9, 0, 9, 15, 8, 15),
                    box(8, 0, 1, 13, 5, 6),
                    box(2, 0, 10, 7, 4, 15),
                    box(2, 0, 2, 6, 2, 6),
                    box(3, 0, 3, 12, 9, 12)
                ),
                Stream.of(
                    box(8, 0, 8, 15, 9, 15),
                    box(8, 0, 0, 14, 7, 6),
                    box(1, 0, 10, 7, 6, 16),
                    box(1, 0, 1, 6, 5, 6),
                    box(2, 0, 2, 13, 11, 13)
                ),
                Stream.of(
                    box(8, 0, 8, 16, 10, 16),
                    box(8, 0, 0, 15, 7, 7),
                    box(0, 0, 9, 7, 8, 16),
                    box(0, 0, 0, 6, 6, 6),
                    box(2, 0, 2, 14, 12, 14)
                ),
                Stream.of(
                    box(8, 0, 8, 16, 10, 16),
                    box(8, 0, 0, 15, 8, 7),
                    box(0, 0, 9, 7, 8, 16),
                    box(0, 0, 0, 6, 6, 6),
                    box(1, 0, 1, 14, 13, 14)
                )
            ).get(age);
        }

        return List.of(
            Stream.of(
                box(2, 0, 0, 8, 6, 6),
                box(10, 0, 4, 15, 5, 9),
                box(3, 0, 5, 8, 4, 10),
                box(4, 0, 0, 12, 7, 8)
            ),
            Stream.of(
                box(1, 0, 0, 7, 8, 6),
                box(10, 0, 0, 15, 5, 5),
                box(2, 0, 6, 7, 4, 11),
                box(11, 0, 6, 15, 2, 10),
                box(4, 0, 0, 13, 9, 9)
            ),
            Stream.of(
                box(3, 0, 0, 14, 11, 11),
                box(1, 0, 0, 8, 9, 7),
                box(10, 0, 0, 16, 7, 6),
                box(0, 0, 7, 6, 6, 13),
                box(10, 0, 7, 15, 5, 12)
            ),
            Stream.of(
                box(0, 0, 0, 8, 10, 8),
                box(9, 0, 0, 16, 7, 7),
                box(0, 0, 9, 7, 8, 16),
                box(10, 0, 9, 16, 6, 15),
                box(2, 0, 0, 14, 12, 12)
            ),
            Stream.of(
                box(0, 0, 0, 8, 11, 8),
                box(9, 0, 8, 16, 9, 15),
                box(1, 0, 8, 8, 8, 15),
                box(10, 0, 0, 16, 6, 6),
                box(2, 0, 0, 15, 13, 13)
            )
        ).get(age);
    }
}
