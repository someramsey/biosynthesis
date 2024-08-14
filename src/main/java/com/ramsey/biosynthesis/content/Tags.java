package com.ramsey.biosynthesis.content;

import com.ramsey.biosynthesis.Main;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.Block;

public abstract class Tags {
    public static final TagKey<Block> plantBased = BlockTags.create(new ResourceLocation(Main.MODID, "plant_based"));
}
