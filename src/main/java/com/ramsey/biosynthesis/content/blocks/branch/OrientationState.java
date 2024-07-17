package com.ramsey.biosynthesis.content.blocks.branch;

import net.minecraft.util.StringRepresentable;
import org.jetbrains.annotations.NotNull;

public enum OrientationState implements StringRepresentable {
    Up("up"),
    Horizontal("horizontal"),
    Down("down");

    private final String name;

    OrientationState(String state) {
        this.name = state;
    }

    @Override
    public @NotNull String getSerializedName() {
        return this.name;
    }
}
