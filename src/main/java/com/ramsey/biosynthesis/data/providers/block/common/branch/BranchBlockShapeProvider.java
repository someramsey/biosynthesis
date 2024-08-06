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
        put("flat_down_down", List.of(
            Shape.box(5, 0, 0, 11, 1, 16),
            Shape.box(11, 0, 5, 16, 1, 11),
            Shape.box(0, 0, 5, 5, 1, 11),
            Shape.box(0, 0, 5, 0, 0, 11),
            Shape.box(16, 0, 5, 16, 0, 11),
            Shape.box(0, 0, 5, 0, 1, 11),
            Shape.box(16, 0, 5, 16, 1, 11)
        ));

        put("flat_down_flat", List.of(
            Shape.box(5, 0, 0, 11, 1, 16),
            Shape.box(11, 0, 5, 16, 1, 11),
            Shape.box(0, 0, 5, 5, 1, 11),
            Shape.box(0, 0, 5, 0, 0, 11),
            Shape.box(0, 0, 5, 0, 1, 11)
        ));

        put("flat_down_up", List.of(
            Shape.box(5, 0, 0, 11, 1, 16),
            Shape.box(11, 0, 5, 15, 1, 11),
            Shape.box(1, 0, 5, 5, 1, 11),
            Shape.box(0, 0, 5, 1, 0, 11),
            Shape.box(15, 1, 5, 16, 16, 11),
            Shape.box(0, 0, 5, 1, 1, 11),
            Shape.box(15, 0, 5, 16, 1, 11)
        ));

        put("flat_flat_down", List.of(
            Shape.box(5, 0, 0, 11, 1, 16),
            Shape.box(11, 0, 5, 15, 1, 11),
            Shape.box(0, 0, 5, 5, 1, 11),
            Shape.box(15, 0, 5, 16, 0, 11),
            Shape.box(15, 0, 5, 16, 1, 11)
        ));

        put("flat_flat_flat", List.of(
            Shape.box(5, 0, 0, 11, 1, 16),
            Shape.box(11, 0, 5, 16, 1, 11),
            Shape.box(0, 0, 5, 5, 1, 11)
        ));

        put("flat_flat_up", List.of(
            Shape.box(5, 0, 0, 11, 1, 16),
            Shape.box(11, 0, 5, 15, 1, 11),
            Shape.box(0, 0, 5, 5, 1, 11),
            Shape.box(15, 1, 5, 16, 16, 11),
            Shape.box(15, 0, 5, 16, 1, 11)
        ));

        put("flat_up_down", List.of(
            Shape.box(5, 0, 0, 11, 1, 16),
            Shape.box(11, 0, 5, 15, 1, 11),
            Shape.box(1, 0, 5, 5, 1, 11),
            Shape.box(0, 1, 5, 1, 16, 11),
            Shape.box(15, 0, 5, 16, 0, 11),
            Shape.box(0, 0, 5, 1, 1, 11),
            Shape.box(15, 0, 5, 16, 1, 11)
        ));

        put("flat_up_flat", List.of(
            Shape.box(5, 0, 0, 11, 1, 16),
            Shape.box(11, 0, 5, 16, 1, 11),
            Shape.box(1, 0, 5, 5, 1, 11),
            Shape.box(0, 1, 5, 1, 16, 11),
            Shape.box(0, 0, 5, 1, 1, 11)
        ));

        put("flat_up_up", List.of(
            Shape.box(5, 0, 0, 11, 1, 16),
            Shape.box(11, 0, 5, 15, 1, 11),
            Shape.box(1, 0, 5, 5, 1, 11),
            Shape.box(0, 1, 5, 1, 16, 11),
            Shape.box(15, 1, 5, 16, 16, 11),
            Shape.box(0, 0, 5, 1, 1, 11),
            Shape.box(15, 0, 5, 16, 1, 11)
        ));

        put("edge", List.of(
            Shape.box(5, 0, 0, 11, 1, 1)
        ));

        put("none_down_none", List.of(
            Shape.box(5, 0, 0, 11, 1, 16),
            Shape.box(0, 0, 5, 0, 0, 11),
            Shape.box(0, 0, 5, 5, 1, 11),
            Shape.box(0, 0, 5, 0, 1, 11)
        ));

        put("none_flat_none", List.of(
            Shape.box(5, 0, 0, 11, 1, 16),
            Shape.box(0, 0, 5, 5, 1, 11)
        ));

        put("none_up_none", List.of(
            Shape.box(5, 0, 0, 11, 1, 16),
            Shape.box(0, 1, 5, 1, 16, 11),
            Shape.box(1, 0, 5, 5, 1, 11),
            Shape.box(0, 0, 5, 1, 1, 11)
        ));

        put("none_none_down", List.of(
            Shape.box(5, 0, 0, 11, 1, 16),
            Shape.box(11, 0, 5, 16, 1, 11),
            Shape.box(16, 0, 5, 16, 1, 11),
            Shape.box(16, 0, 5, 16, 0, 11)
        ));

        put("none_none_flat", List.of(
            Shape.box(5, 0, 0, 11, 1, 16),
            Shape.box(11, 0, 5, 16, 1, 11)
        ));

        put("none_none_up", List.of(
            Shape.box(5, 0, 0, 11, 1, 16),
            Shape.box(11, 0, 5, 15, 1, 11),
            Shape.box(15, 0, 5, 16, 1, 11),
            Shape.box(15, 1, 5, 16, 16, 11)
        ));

        put("down_none_none", List.of(
            Shape.box(5, 0, 0, 11, 1, 16),
            Shape.box(5, 0, 0, 11, 1, 0),
            Shape.box(5, 0, 0, 11, 0, 0)
        ));

        put("flat_none_none", List.of(
            Shape.box(5, 0, 0, 11, 1, 16)
        ));

        put("up_none_none", List.of(
            Shape.box(5, 0, 1, 11, 1, 16),
            Shape.box(5, 0, 0, 11, 1, 1),
            Shape.box(5, 1, 0, 11, 16, 1)
        ));
    }};
}

