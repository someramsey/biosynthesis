package com.ramsey.biosynthesis.content.blocks.vessel;

import com.ramsey.biosynthesis.content.blocks.branch.Orientation;
import com.ramsey.biosynthesis.data.providers.block.Shape;
import net.minecraft.util.StringRepresentable;
import org.jetbrains.annotations.NotNull;

public enum Alignment implements StringRepresentable, Shape.Modifier {
    Middle("middle"),
    North("north"),
    East("east"),
    South("south"),
    West("west");

    private final String name;

    Alignment(String state) {
        this.name = state;
    }

    @Override
    public @NotNull String getSerializedName() {
        return this.name;
    }

    public void applyToShapeFragment(Shape.Fragment pFragment) {
        switch (this) {
            case West ->
                pFragment.transform(pFragment.minZ, pFragment.minY, 1 - pFragment.minX, pFragment.maxZ, pFragment.maxY, 1 - pFragment.maxX);
            case East ->
                pFragment.transform(1 - pFragment.minZ, pFragment.minY, pFragment.minX, 1 - pFragment.maxZ, pFragment.maxY, pFragment.maxX);
            case South ->
                pFragment.transform(1 - pFragment.minX, pFragment.minY, 1 - pFragment.minZ, 1 - pFragment.maxX, pFragment.maxY, 1 - pFragment.maxZ);
        }
    }

    public static Alignment fromOrientation(Orientation pOrientation) {
        return switch (pOrientation) {
            case UpN, DownN, NorthU, NorthD -> South;
            case UpE, DownE, EastU, EastD -> West;
            case UpS, DownS, SouthU, SouthD -> North;
            case UpW, DownW, WestU, WestD -> East;
        };
    }
}
