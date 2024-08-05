package com.ramsey.biosynthesis.data.providers.block.common.vessel;

import com.ramsey.biosynthesis.content.blocks.vessel.Alignment;
import com.ramsey.biosynthesis.content.blocks.vessel.VesselBlock;
import net.minecraft.world.level.block.state.BlockState;

public abstract class VesselBlockModelProvider {
    public static String getModelKey(BlockState pBlockState) {
        int age = pBlockState.getValue(VesselBlock.AgeProperty);

        if (age == 5) {
            return "full";
        }

        StringBuilder builder = new StringBuilder();
        Alignment alignment = pBlockState.getValue(VesselBlock.AlignmentProperty);

        if (alignment == Alignment.Middle) {
            builder.append("mid/");
        } else {
            builder.append("shifted/");
        }

        builder.append("stage").append(age);

        return builder.toString();
    }
}
