package com.ramsey.biosynthesis.content.blocks.branch;

import net.minecraft.core.BlockPos;
import net.minecraft.util.StringRepresentable;
import org.jetbrains.annotations.NotNull;

public enum Orientation implements StringRepresentable {
    NorthUp("north_up", -90, 0),
    North("north", 0, 0),
    NorthDown("north_down", 90, 180),
    EastUp("east_up", -90, 90),
    East("east", 0, 90),
    EastDown("east_down", 90, 270),
    SouthUp("south_up", -90, 180),
    South("south", 0, 180),
    SouthDown("south_down", 90, 0),
    WestUp("west_up", -90, 270),
    West("west", 0, 270),
    WestDown("west_down", 90, 90);

    private final String name;
    private final int xRotation;
    private final int yRotation;

    public static final Orientation[] Horizontal = {North, East, South, West};

    Orientation(String state, int xRotation, int yRotation) {
        this.name = state;
        this.xRotation = xRotation;
        this.yRotation = yRotation;
    }

    @Override
    public @NotNull String getSerializedName() {
        return this.name;
    }

    public int getXRotation() {
        return this.xRotation;
    }

    public int getYRotation() {
        return this.yRotation;
    }

    public BlockPos step(BlockPos pos) {
        return this.step(pos, 1);
    }

    public BlockPos step(BlockPos pPos, int pDistance) {
        return switch (this) {
            case North -> pPos.north(pDistance);
            case NorthUp -> pPos.north(pDistance).above(pDistance);
            case NorthDown -> pPos.north(pDistance).below(pDistance);
            case EastUp -> pPos.east(pDistance).above(pDistance);
            case East -> pPos.east(pDistance);
            case EastDown -> pPos.east(pDistance).below(pDistance);
            case South -> pPos.south(pDistance);
            case SouthUp -> pPos.south(pDistance).above(pDistance);
            case SouthDown -> pPos.south(pDistance).below(pDistance);
            case WestUp -> pPos.west(pDistance).above(pDistance);
            case West -> pPos.west(pDistance);
            case WestDown -> pPos.west(pDistance).below(pDistance);
        };
    }


}
