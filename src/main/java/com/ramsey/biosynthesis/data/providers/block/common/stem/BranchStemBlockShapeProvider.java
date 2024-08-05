package com.ramsey.biosynthesis.data.providers.block.common.stem;

import com.ramsey.biosynthesis.content.blocks.branch.BranchBlock;
import com.ramsey.biosynthesis.content.blocks.branch.BranchStemBlock;
import com.ramsey.biosynthesis.content.blocks.branch.Orientation;
import com.ramsey.biosynthesis.data.providers.block.Shape;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.VoxelShape;

import java.util.List;
import java.util.stream.Stream;

public abstract class BranchStemBlockShapeProvider {
    public static VoxelShape buildShape(BlockState pState) {
        Orientation orientation = pState.getValue(BranchBlock.OrientationProperty);
        int age = pState.getValue(BranchStemBlock.AgeProperty);

        Stream<Shape.Fragment> shape = shapes.get(age).stream()
            .map(Shape.Fragment::copy)
            .peek(orientation::applyToShapeFragment);

        return Shape.bake(shape);
    }

    private static final List<List<Shape.Fragment>> shapes = List.of(
        List.of(
            Shape.box(5, 0, 12, 11, 1, 16),
            Shape.box(6, 0, 11, 10, 1, 12)
        ),

        List.of(
            Shape.box(5, 0, 10, 11, 1, 16),
            Shape.box(6, 0, 9, 10, 1, 10)
        ),

        List.of(
            Shape.box(5, 0, 6, 11, 1, 16),
            Shape.box(6, 0, 5, 10, 1, 6)
        ),

        List.of(
            Shape.box(5, 0, 4, 11, 1, 16),
            Shape.box(6, 0, 3, 10, 1, 4)
        ),

        List.of(
            Shape.box(5, 0, 0, 11, 1, 16)
        )
    );
}
