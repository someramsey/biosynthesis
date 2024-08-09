package com.ramsey.biosynthesis.content.blocks.branch;

import com.ramsey.biosynthesis.data.providers.block.Shape;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.StringRepresentable;
import org.jetbrains.annotations.NotNull;

@SuppressWarnings("DuplicatedCode")
public enum Orientation implements StringRepresentable, Shape.Modifier {
    NorthU("nu", -90, 0),
    NorthD("nd", 90, 180),
    EastU("eu", -90, 90),
    EastD("ed", 90, 270),
    SouthU("su", -90, 180),
    SouthD("sd", 90, 0),
    WestU("wu", -90, 270),
    WestD("wd", 90, 90),
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

    public BlockPos relative(BlockPos pPos) {
        return switch (this) {
            case UpN, DownN -> pPos.north();
            case UpE, DownE -> pPos.east();
            case UpS, DownS -> pPos.south();
            case UpW, DownW -> pPos.west();
            case NorthU, SouthU, WestU, EastU -> pPos.above();
            case NorthD, SouthD, WestD, EastD -> pPos.below();
        };
    }

    @SuppressWarnings("SuspiciousNameCombination")
    public void applyToShapeFragment(Shape.Fragment pFragment) {
        switch (this) {
            case UpW ->
                pFragment.transform(pFragment.minZ, pFragment.minY, 1 - pFragment.maxX, pFragment.maxZ, pFragment.maxY, 1 - pFragment.minX);

            case UpE ->
                pFragment.transform(1 - pFragment.minZ, pFragment.minY, pFragment.maxX, 1 - pFragment.maxZ, pFragment.maxY, pFragment.minX);

            case DownN ->
                pFragment.transform(pFragment.minX, 1 - pFragment.minY, pFragment.minZ, pFragment.maxX, 1 - pFragment.maxY, pFragment.maxZ);

            case DownS ->
                pFragment.transform(1 - pFragment.minX, 1 - pFragment.minY, pFragment.minZ, 1 - pFragment.maxX, 1 - pFragment.maxY, pFragment.maxZ);

            case DownW ->
                pFragment.transform(1 - pFragment.minZ, 1 - pFragment.minY, pFragment.maxX, 1 - pFragment.maxZ, 1 - pFragment.maxY, pFragment.minX);

            case DownE ->
                pFragment.transform(pFragment.minZ, 1 - pFragment.minY, 1 - pFragment.maxX, pFragment.maxZ, 1 - pFragment.maxY, 1 - pFragment.minX);

            case NorthU ->
                pFragment.transform(pFragment.minX, pFragment.minZ, pFragment.minY, pFragment.maxX, pFragment.maxZ, pFragment.maxY); //x

            case NorthD ->
                pFragment.transform(1 - pFragment.minX, pFragment.minZ, pFragment.minY, 1 - pFragment.maxX, pFragment.maxZ, pFragment.maxY); //x

            case SouthU ->
                pFragment.transform(1 - pFragment.maxX, pFragment.minZ, 1 - pFragment.maxY, 1 - pFragment.minX, pFragment.maxZ, 1 - pFragment.minY); //x

            case SouthD ->
                pFragment.transform(pFragment.maxX, pFragment.minZ, 1 - pFragment.maxY, pFragment.minX, pFragment.maxZ, 1 - pFragment.minY); //x

            case WestD ->
                pFragment.transform(pFragment.minY, pFragment.minZ, pFragment.maxX, pFragment.maxY, pFragment.maxZ, pFragment.minX);

            case WestU ->
                pFragment.transform(pFragment.minY, 1 - pFragment.minZ, 1 - pFragment.maxX, pFragment.maxY, 1 - pFragment.maxZ, 1 - pFragment.minX);

            case EastU ->
                pFragment.transform(1 - pFragment.maxY, 1 - pFragment.minZ, pFragment.minX, 1 - pFragment.minY, 1 - pFragment.maxZ, pFragment.maxX);

            case EastD ->
                pFragment.transform(1 - pFragment.maxY, pFragment.minZ, 1 - pFragment.minX, 1 - pFragment.minY, pFragment.maxZ, 1 - pFragment.maxX);
        }
    }
}
