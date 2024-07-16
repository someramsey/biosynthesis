package com.ramsey.biosynthesis.data.providers.block.common.stem;

import com.ramsey.biosynthesis.content.blocks.StemBlock;
import net.minecraft.world.level.block.state.BlockState;

public class StemBlockModelKeyProvider {
    public static String getModelKey(BlockState pBlockState) {
        return "age" + pBlockState.getValue(StemBlock.AgeProperty);
    }
}
