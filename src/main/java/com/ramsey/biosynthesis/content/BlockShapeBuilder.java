package com.ramsey.biosynthesis.content;

import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

import java.util.stream.Stream;

public abstract class BlockShapeBuilder {
    protected abstract Stream<UnbakedShape> buildShape(String pModel);

    protected abstract UnbakedShape transformShape(UnbakedShape pShape, BlockState pBlockState);

    public VoxelShape buildShape(String pModel, BlockState pBlockState) {
        return buildShape(pModel)
            .map(shape -> transformShape(shape, pBlockState))
            .map(shape -> Shapes.box(shape.minX, shape.minY, shape.minZ, shape.maxX, shape.maxY, shape.maxZ))
            .reduce(Shapes.empty(), Shapes::or);
    }

    protected static UnbakedShape box(int minX, int minY, int minZ, int maxX, int maxY, int maxZ) {
        return new UnbakedShape(minX, minY, minZ, maxX, maxY, maxZ);
    }

    public static class UnbakedShape {
        public double minX, minY, minZ, maxX, maxY, maxZ;

        public UnbakedShape(int minX, int minY, int minZ, int maxX, int maxY, int maxZ) {
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
