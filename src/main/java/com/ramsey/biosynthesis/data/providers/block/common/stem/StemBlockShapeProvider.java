package com.ramsey.biosynthesis.data.providers.block.common.stem;

import com.ramsey.biosynthesis.content.blocks.BranchBlock;
import com.ramsey.biosynthesis.data.providers.block.BlockShapeProvider;
import com.ramsey.biosynthesis.data.providers.block.ShapeUtils;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.state.BlockState;

import java.util.stream.Stream;

public class StemBlockShapeProvider extends BlockShapeProvider {
    public StemBlockShapeProvider() {
        super(StemBlockModelKeyProvider::getModelKey);
    }

    @Override
    protected UnbakedShape transformShape(UnbakedShape pShape, BlockState pBlockState) {
        Direction direction = pBlockState.getValue(BranchBlock.FacingProperty);
        ShapeUtils.rotateHorizontally(pShape, direction);

        return pShape;
    }

    @Override
    protected Stream<UnbakedShape> buildShape(String pModel) {
        return switch (pModel) {
            case "age0" -> Stream.of(
                box(5, 0, 12, 11, 1, 15),
                box(5, 0, 15, 11, 1, 16),
                box(6, 0, 11, 10, 1, 12)
            );

            case "age1" -> Stream.of(
                box(5, 0, 10, 11, 1, 15),
                box(5, 0, 15, 11, 1, 16),
                box(6, 0, 9, 10, 1, 10)
            );

            case "age2" -> Stream.of(
                box(5, 0, 6, 11, 1, 15),
                box(5, 0, 15, 11, 1, 16),
                box(6, 0, 5, 10, 1, 6)
            );

            case "age3" -> Stream.of(
                box(5, 0, 4, 11, 1, 15),
                box(5, 0, 15, 11, 1, 16),
                box(6, 0, 3, 10, 1, 4)
            );

            case "age4" -> Stream.of(
                box(5, 0, 0, 11, 1, 15),
                box(5, 0, 15, 11, 1, 16)
            );

            default -> throw new IllegalStateException("Invalid model key: " + pModel);
        };
    }

}
