package com.ramsey.biosynthesis.data.providers.block.common.vessel;

import com.ramsey.biosynthesis.content.blocks.vessel.Alignment;
import com.ramsey.biosynthesis.content.blocks.vessel.VesselBlock;
import com.ramsey.biosynthesis.data.providers.block.Shape;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.VoxelShape;

import java.util.List;
import java.util.stream.Stream;

public abstract class VesselBlockShapeProvider {
    public static VoxelShape buildShape(BlockState pState) {
        Alignment alignment = pState.getValue(VesselBlock.AlignmentProperty);
        int age = pState.getValue(VesselBlock.AgeProperty);

        Stream<Shape.Fragment> shape = getShape(alignment, age).stream()
            .map(Shape.Fragment::copy)
            .peek(alignment::applyToShapeFragment);

        return Shape.bake(shape);
    }

    private static List<Shape.Fragment> getShape(Alignment pAlignment, int pAge) {
        if (pAge == 5) {
            return List.of(Shape.box(0, 0, 0, 16, 16, 16));
        }

        if (pAlignment == Alignment.Middle) {
            return unaligned.get(pAge);
        } else  {
            return aligned.get(pAge);
        }
    }

    private static final List<List<Shape.Fragment>> unaligned = List.of(
        List.of(
            Shape.box(9, 0, 9, 15, 6, 15),
            Shape.box(8, 0, 1, 13, 5, 6),
            Shape.box(2, 0, 10, 7, 4, 15),
            Shape.box(4, 0, 4, 12, 7, 12)
        ),
        List.of(
            Shape.box(9, 0, 9, 15, 8, 15),
            Shape.box(8, 0, 1, 13, 5, 6),
            Shape.box(2, 0, 10, 7, 4, 15),
            Shape.box(2, 0, 2, 6, 2, 6),
            Shape.box(3, 0, 3, 12, 9, 12)
        ),
        List.of(
            Shape.box(8, 0, 8, 15, 9, 15),
            Shape.box(8, 0, 0, 14, 7, 6),
            Shape.box(1, 0, 10, 7, 6, 16),
            Shape.box(1, 0, 1, 6, 5, 6),
            Shape.box(2, 0, 2, 13, 11, 13)
        ),
        List.of(
            Shape.box(8, 0, 8, 16, 10, 16),
            Shape.box(8, 0, 0, 15, 7, 7),
            Shape.box(0, 0, 9, 7, 8, 16),
            Shape.box(0, 0, 0, 6, 6, 6),
            Shape.box(2, 0, 2, 14, 12, 14)
        ),
        List.of(
            Shape.box(8, 0, 8, 16, 10, 16),
            Shape.box(8, 0, 0, 15, 8, 7),
            Shape.box(0, 0, 9, 7, 8, 16),
            Shape.box(0, 0, 0, 6, 6, 6),
            Shape.box(1, 0, 1, 14, 13, 14)
        )
    );

    private static final List<List<Shape.Fragment>> aligned = List.of(
        List.of(
            Shape.box(2, 0, 0, 8, 6, 6),
            Shape.box(10, 0, 4, 15, 5, 9),
            Shape.box(3, 0, 5, 8, 4, 10),
            Shape.box(4, 0, 0, 12, 7, 8)
        ),
        List.of(
            Shape.box(1, 0, 0, 7, 8, 6),
            Shape.box(10, 0, 0, 15, 5, 5),
            Shape.box(2, 0, 6, 7, 4, 11),
            Shape.box(11, 0, 6, 15, 2, 10),
            Shape.box(4, 0, 0, 13, 9, 9)
        ),
        List.of(
            Shape.box(3, 0, 0, 14, 11, 11),
            Shape.box(1, 0, 0, 8, 9, 7),
            Shape.box(10, 0, 0, 16, 7, 6),
            Shape.box(0, 0, 7, 6, 6, 13),
            Shape.box(10, 0, 7, 15, 5, 12)
        ),
        List.of(
            Shape.box(0, 0, 0, 8, 10, 8),
            Shape.box(9, 0, 0, 16, 7, 7),
            Shape.box(0, 0, 9, 7, 8, 16),
            Shape.box(10, 0, 9, 16, 6, 15),
            Shape.box(2, 0, 0, 14, 12, 12)
        ),
        List.of(
            Shape.box(0, 0, 0, 8, 11, 8),
            Shape.box(9, 0, 8, 16, 9, 15),
            Shape.box(1, 0, 8, 8, 8, 15),
            Shape.box(10, 0, 0, 16, 6, 6),
            Shape.box(2, 0, 0, 15, 13, 13)
        )
    );
}
