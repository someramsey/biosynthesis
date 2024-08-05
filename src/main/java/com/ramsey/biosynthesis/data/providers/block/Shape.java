package com.ramsey.biosynthesis.data.providers.block;

import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

import java.util.stream.Stream;

public abstract class Shape {
    public static Fragment box(int minX, int minY, int minZ, int maxX, int maxY, int maxZ) {
        Fragment fragment = new Fragment();

        fragment.minX = minX / 16.0d;
        fragment.minY = minY / 16.0d;
        fragment.minZ = minZ / 16.0d;
        fragment.maxX = maxX / 16.0d;
        fragment.maxY = maxY / 16.0d;
        fragment.maxZ = maxZ / 16.0d;

        return fragment;
    }

    public static VoxelShape bake(Stream<Fragment> pShape) {
        return pShape
            .map(frag -> Shapes.box(frag.minX, frag.minY, frag.minZ, frag.maxX, frag.maxY, frag.maxZ))
            .reduce(Shapes.empty(), Shapes::or);
    }

    public static class Fragment {
        public double minX, minY, minZ, maxX, maxY, maxZ;

        public Fragment copy() {
            Fragment fragment = new Fragment();

            fragment.minX = minX;
            fragment.minY = minY;
            fragment.minZ = minZ;
            fragment.maxX = maxX;
            fragment.maxY = maxY;
            fragment.maxZ = maxZ;

            return fragment;
        }

        public void transform(double minX, double minY, double minZ, double maxX, double maxY, double maxZ) {
            this.minX = minX;
            this.minY = minY;
            this.minZ = minZ;
            this.maxX = maxX;
            this.maxY = maxY;
            this.maxZ = maxZ;

            this.updateBounds();
        }

        private void updateBounds() {
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
        }
    }

    public interface Modifier {
        void applyToShapeFragment(Fragment pFragment);
    }
}
