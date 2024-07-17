package com.ramsey.biosynthesis.data.providers.block;

import com.ramsey.biosynthesis.content.blocks.branch.Orientation;
import net.minecraft.core.Direction;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

import java.util.stream.Stream;

public abstract class BlockShapeProvider {
    protected static UnbakedShapeFragment box(int minX, int minY, int minZ, int maxX, int maxY, int maxZ) {
        return new UnbakedShapeFragment(minX, minY, minZ, maxX, maxY, maxZ);
    }

    protected static VoxelShape bakeShape(Stream<UnbakedShapeFragment> pShape) {
        return pShape.map(shape -> Shapes.box(shape.minX, shape.minY, shape.minZ, shape.maxX, shape.maxY, shape.maxZ))
            .reduce(Shapes.empty(), Shapes::or);
    }


    protected static void transformByOrientation(UnbakedShapeFragment pShape, Orientation pOrientation) {
        switch (pOrientation) {
            case NorthUp ->
                pShape.transform(pShape.minX, 1 - pShape.minZ, pShape.minY, pShape.maxX, 1 - pShape.maxZ, pShape.maxY);

            case NorthDown ->
                pShape.transform(pShape.minX, pShape.minZ, pShape.minY, pShape.maxX, pShape.maxZ, pShape.maxY);

            case South ->
                pShape.transform(1 - pShape.maxX, pShape.minY, 1 - pShape.maxZ, 1 - pShape.minX, pShape.maxY, 1 - pShape.minZ);

            case SouthUp -> {
                pShape.transform(pShape.minX, 1 - pShape.minZ, pShape.minY, pShape.maxX, 1 - pShape.maxZ, pShape.maxY);
                pShape.transform(1 - pShape.maxX, pShape.minY, 1 - pShape.maxZ, 1 - pShape.minX, pShape.maxY, 1 - pShape.minZ);
            }

            case SouthDown -> {
                pShape.transform(pShape.minX, pShape.minZ, pShape.minY, pShape.maxX, pShape.maxZ, pShape.maxY);
                pShape.transform(1 - pShape.maxX, pShape.minY, 1 - pShape.maxZ, 1 - pShape.minX, pShape.maxY, 1 - pShape.minZ);
            }

            case West ->
                pShape.transform(pShape.minZ, pShape.minY, 1 - pShape.maxX, pShape.maxZ, pShape.maxY, 1 - pShape.minX);

            //a, b, c, d, e, f
            //c, b, 1 - d, f, e, 1 - a
            //1 - d, b, 1 - f, 1 - a, e, 1 - c
            case WestUp -> {
                pShape.transform(pShape.minX, 1 - pShape.minZ, pShape.minY, pShape.maxX, 1 - pShape.maxZ, pShape.maxY);
                pShape.transform(pShape.minZ, pShape.minY, 1 - pShape.maxX, pShape.maxZ, pShape.maxY, 1 - pShape.minX);
            }

            case WestDown -> {
                pShape.transform(pShape.minX, pShape.minZ, pShape.minY, pShape.maxX, pShape.maxZ, pShape.maxY);
                pShape.transform(pShape.minZ, pShape.minY, 1 - pShape.maxX, pShape.maxZ, pShape.maxY, 1 - pShape.minX);
            }

            //a, b, c, d, e, f
            //a, 1-c, b, d, 1-f, e
            //1-e, 1-c, a, 1 - b, 1-f, d
            case East ->
                pShape.transform(1 - pShape.maxZ, pShape.minY, pShape.minX, 1 - pShape.minZ, pShape.maxY, pShape.maxX);

            case EastUp -> {
                pShape.transform(pShape.minX, 1 - pShape.minZ, pShape.minY, pShape.maxX, 1 - pShape.maxZ, pShape.maxY);
                pShape.transform(1 - pShape.maxZ, pShape.minY, pShape.minX, 1 - pShape.minZ, pShape.maxY, pShape.maxX);
            }
            case EastDown -> {
                pShape.transform(pShape.minX, pShape.minZ, pShape.minY, pShape.maxX, pShape.maxZ, pShape.maxY);
                pShape.transform(1 - pShape.maxZ, pShape.minY, 1 - pShape.maxX, 1 - pShape.minZ, pShape.maxY, 1 - pShape.minX);
            }
        }
    }


    protected static void transformByDirection(UnbakedShapeFragment pShape, Direction pDirection) {
        switch (pDirection) {
            case SOUTH ->
                pShape.transform(1 - pShape.maxX, pShape.minY, 1 - pShape.maxZ, 1 - pShape.minX, pShape.maxY, 1 - pShape.minZ);
            case WEST ->
                pShape.transform(pShape.minZ, pShape.minY, 1 - pShape.maxX, pShape.maxZ, pShape.maxY, 1 - pShape.minX);
            case EAST ->
                pShape.transform(1 - pShape.maxZ, pShape.minY, pShape.minX, 1 - pShape.minZ, pShape.maxY, pShape.maxX);
        }
    }

    protected static class UnbakedShapeFragment {
        public double minX, minY, minZ, maxX, maxY, maxZ;

        public UnbakedShapeFragment(int minX, int minY, int minZ, int maxX, int maxY, int maxZ) {
            this.minX = minX / 16.0d;
            this.minY = minY / 16.0d;
            this.minZ = minZ / 16.0d;
            this.maxX = maxX / 16.0d;
            this.maxY = maxY / 16.0d;
            this.maxZ = maxZ / 16.0d;
        }

        public void transform(double minX, double minY, double minZ, double maxX, double maxY, double maxZ) {
            if (minX > maxX) {
                double temp = minX;
                minX = maxX;
                maxX = temp;
            }

            if (minY > maxY) {
                double temp = minY;
                minY = maxY;
                maxY = temp;
            }

            if (minZ > maxZ) {
                double temp = minZ;
                minZ = maxZ;
                maxZ = temp;
            }

            this.minX = minX;
            this.minY = minY;
            this.minZ = minZ;
            this.maxX = maxX;
            this.maxY = maxY;
            this.maxZ = maxZ;
        }
    }
}
