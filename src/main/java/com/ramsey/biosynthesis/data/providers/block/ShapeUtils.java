package com.ramsey.biosynthesis.data.providers.block;

import net.minecraft.core.Direction;

public abstract class ShapeUtils {
    public static void rotateHorizontally(BlockShapeProvider.UnbakedShape pShape, Direction pDirection) {
        switch (pDirection) {
            case SOUTH -> pShape.transform(1 - pShape.maxX, pShape.minY, 1 - pShape.maxZ, 1 - pShape.minX, pShape.maxY, 1 - pShape.minZ);
            case WEST -> pShape.transform(pShape.minZ, pShape.minY, 1 - pShape.maxX, pShape.maxZ, pShape.maxY, 1 - pShape.minX);
            case EAST -> pShape.transform(1 - pShape.maxZ, pShape.minY, pShape.minX, 1 - pShape.minZ, pShape.maxY, pShape.maxX);
        }
    }
}
