package com.ramsey.biosynthesis.content.blocks.branch;

import net.minecraft.util.StringRepresentable;
import org.jetbrains.annotations.NotNull;

@SuppressWarnings("DuplicatedCode")
public enum Orientation implements StringRepresentable {
    NorthU("nu", -90, 0),
    NorthW("nw", -90, 0),
    NorthD("nd", -90, 0),
    NorthE("ne", -90, 0),
    EastU("eu", -90, 90),
    EastN("en", -90, 90),
    EastD("ed", -90, 90),
    EastS("es", -90, 90),
    SouthU("su", -90, 180),
    SouthE("se", -90, 180),
    SouthD("sd", -90, 180),
    SouthW("sw", -90, 180),
    WestU("wu", -90, 270),
    WestS("ws", -90, 270),
    WestD("wd", -90, 270),
    WestN("wn", -90, 270),
    UpN("un", 0, 0),
    UpE("ue", 0, 90),
    UpS("us", 0, 180),
    UpW("uw", 0, 270),
    DownN("dn", 180, 0),
    DownE("de", 180, 90),
    DownS("ds", 180, 180),
    DownW("dw", 180, 270);

    private final String name;
    private final int xRotation;
    private final int yRotation;

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
}
