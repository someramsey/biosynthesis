package com.ramsey.biosynthesis.data.providers.block;

import net.minecraft.world.phys.shapes.VoxelShape;

import java.util.HashMap;
import java.util.stream.Stream;

public class BranchBlockShapeMapper extends BlockShapeMapper {
    private static final HashMap<String, Stream<VoxelShape>> Shapes = new HashMap<>() {{

    }};

    @Override
    protected HashMap<String, Stream<VoxelShape>> getShapes() {
        return Shapes;
    }
}
