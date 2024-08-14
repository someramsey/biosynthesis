package com.ramsey.biosynthesis.registry;

import com.ramsey.biosynthesis.Main;
import net.minecraft.core.Registry;
import net.minecraft.world.item.Item;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;

public abstract class ItemRegistry {
    public static DeferredRegister<Item> registrate = DeferredRegister.create(Registry.ITEM_REGISTRY, Main.MODID);

    public static void init(IEventBus modEventBus) {
        registrate.register(modEventBus);
    }
}
