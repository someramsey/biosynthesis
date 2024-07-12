package com.ramsey.biosynthesis.registrate;

import com.ramsey.biosynthesis.Main;
import net.minecraft.core.Registry;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;

public abstract class ItemRegistry {
    public static DeferredRegister<Item> registrate = DeferredRegister.create(Registry.ITEM_REGISTRY, Main.MODID);

    public static void init(IEventBus modEventBus) {
        registrate.register(modEventBus);
    }
}
