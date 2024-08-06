package com.ramsey.biosynthesis.data.providers.block.common.branch;

import com.ramsey.biosynthesis.content.blocks.branch.BranchBlock;
import com.ramsey.biosynthesis.content.blocks.branch.Orientation;
import com.ramsey.biosynthesis.data.providers.block.Shape;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.VoxelShape;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

public abstract class BranchBlockShapeProvider {
    public static VoxelShape buildShape(BlockState pState) {
        String modelKey = BranchBlockModelProvider.getModelKey(pState);
        Orientation orientation = pState.getValue(BranchBlock.OrientationProperty);

        Stream<Shape.Fragment> shape = shapes.get(modelKey).stream()
            .map(Shape.Fragment::copy)
            .peek(orientation::applyToShapeFragment);

        return Shape.bake(shape);
    }

    private static final Map<String, List<Shape.Fragment>> shapes = new HashMap<>() {{
        put("both", List.of(
            Shape.box(5, 0, 5, 11, 1, 16),
            Shape.box(11, 0, 5, 16, 1, 11),
            Shape.box(0, 0, 5, 5, 1, 11)
        ));

        put("down", List.of(
            Shape.box(5, 0, 0, 11, 1, 16),
            Shape.box(5, 0, 0, 11, 1, 0),
            Shape.box(5, 0, 0, 11, 0, 0)
        ));

        put("down_both", List.of(
            Shape.box(5, 0, 0, 11, 1, 16),
            Shape.box(11, 0, 5, 16, 1, 11),
            Shape.box(0, 0, 5, 5, 1, 11),
            Shape.box(5, 0, 0, 11, 1, 0),
            Shape.box(5, 0, 0, 11, 0, 0)
        ));

        put("down_left", List.of(
            Shape.box(5, 0, 0, 11, 1, 16),
            Shape.box(0, 0, 5, 5, 1, 11),
            Shape.box(5, 0, 0, 11, 1, 0),
            Shape.box(5, 0, 0, 11, 0, 0)
        ));

        put("down_right", List.of(
            Shape.box(5, 0, 0, 11, 1, 16),
            Shape.box(11, 0, 5, 16, 1, 11),
            Shape.box(5, 0, 0, 11, 1, 0),
            Shape.box(5, 0, 0, 11, 0, 0)
        ));

        put("edge", List.of(
            Shape.box(5, 0, 0, 11, 1, 1)
        ));

        put("flat", List.of(
            Shape.box(5, 0, 0, 11, 1, 16)
        ));

        put("flat_both", List.of(
            Shape.box(5, 0, 0, 11, 1, 16),
            Shape.box(11, 0, 5, 16, 1, 11),
            Shape.box(0, 0, 5, 5, 1, 11)
        ));

        put("flat_left", List.of(
            Shape.box(5, 0, 0, 11, 1, 16),
            Shape.box(0, 0, 5, 5, 1, 11)
        ));

        put("flat_right", List.of(
            Shape.box(5, 0, 0, 11, 1, 16),
            Shape.box(11, 0, 5, 16, 1, 11)
        ));

        put("left", List.of(
            Shape.box(5, 0, 11, 11, 1, 16),
            Shape.box(0, 0, 5, 5, 1, 11),
            Shape.box(5, 0, 5, 11, 1, 11)
        ));

        put("right", List.of(
            Shape.box(5, 0, 11, 11, 1, 16),
            Shape.box(11, 0, 5, 16, 1, 11),
            Shape.box(5, 0, 5, 11, 1, 11)
        ));

        put("up", List.of(
            Shape.box(5, 0, 1, 11, 1, 16),
            Shape.box(5, 0, 0, 11, 1, 1),
            Shape.box(5, 1, 0, 11, 16, 1)
        ));

        put("up_both", List.of(
            Shape.box(5, 0, 1, 11, 1, 16),
            Shape.box(11, 0, 5, 16, 1, 11),
            Shape.box(0, 0, 5, 5, 1, 11),
            Shape.box(5, 1, 0, 11, 16, 1),
            Shape.box(0, 0, 5, 1, 1, 11),
            Shape.box(5, 0, 0, 11, 1, 1)
        ));

        put("up_left", List.of(
            Shape.box(5, 0, 1, 11, 1, 16),
            Shape.box(0, 0, 5, 5, 1, 11),
            Shape.box(5, 1, 0, 11, 16, 1),
            Shape.box(0, 0, 5, 1, 1, 11),
            Shape.box(5, 0, 0, 11, 1, 1)
        ));

        put("up_right", List.of(
            Shape.box(5, 0, 1, 11, 1, 16),
            Shape.box(11, 0, 5, 16, 1, 11),
            Shape.box(5, 1, 0, 11, 16, 1),
            Shape.box(0, 0, 5, 1, 1, 11),
            Shape.box(5, 0, 0, 11, 1, 1)
        ));
    }};
}

