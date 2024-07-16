package com.ramsey.biosynthesis.data.providers.block;

import net.minecraft.world.phys.shapes.VoxelShape;

import java.util.HashMap;
import java.util.stream.Stream;

public abstract class BlockShapeMapper {
    protected abstract HashMap<String, Stream<VoxelShape>> getShapes();


}
