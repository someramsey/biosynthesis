package com.ramsey.biosynthesis.data.providers.block.common.stem;

import com.ramsey.biosynthesis.content.blocks.branch.BranchBlock;
import com.ramsey.biosynthesis.content.blocks.branch.BranchStemBlock;
import com.ramsey.biosynthesis.data.providers.block.BlockShapeProvider;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.VoxelShape;

import java.util.List;
import java.util.stream.Stream;

public abstract class BranchStemBlockShapeProvider extends BlockShapeProvider {
    public static VoxelShape getShape(BlockState pState) {
        Stream<UnbakedShapeFragment> shape = buildShape(pState)
            .map(pFragment -> transformShape(pFragment, pState));

        return bakeShape(shape);
    }

    private static UnbakedShapeFragment transformShape(UnbakedShapeFragment pFragment, BlockState pBlockState) {
        Direction direction = pBlockState.getValue(BranchBlock.FacingProperty);
        BlockShapeProvider.rotateHorizontally(pFragment, direction);

        return pFragment;
    }

    private static Stream<UnbakedShapeFragment> buildShape(BlockState pState) {
        int age = pState.getValue(BranchStemBlock.AgeProperty);

        return List.of(
            Stream.of(
                box(5, 0, 12, 11, 1, 15),
                box(5, 0, 15, 11, 1, 16),
                box(6, 0, 11, 10, 1, 12)
            ),
            Stream.of(
                box(5, 0, 10, 11, 1, 15),
                box(5, 0, 15, 11, 1, 16),
                box(6, 0, 9, 10, 1, 10)
            ),
            Stream.of(
                box(5, 0, 6, 11, 1, 15),
                box(5, 0, 15, 11, 1, 16),
                box(6, 0, 5, 10, 1, 6)
            ),
            Stream.of(
                box(5, 0, 4, 11, 1, 15),
                box(5, 0, 15, 11, 1, 16),
                box(6, 0, 3, 10, 1, 4)
            ),
            Stream.of(
                box(5, 0, 0, 11, 1, 15),
                box(5, 0, 15, 11, 1, 16)
            )
        ).get(age);
    }
}
