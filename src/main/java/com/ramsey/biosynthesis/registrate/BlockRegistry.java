package com.ramsey.biosynthesis.registrate;

import com.ramsey.biosynthesis.Main;
import com.ramsey.biosynthesis.content.blocks.vessel.VesselBlock;
import com.ramsey.biosynthesis.content.blocks.vessel.VesselHeadBlock;
import net.minecraft.core.Registry;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

@SuppressWarnings("unused")
public abstract class BlockRegistry {
    public static DeferredRegister<Block> registrate = DeferredRegister.create(Registry.BLOCK_REGISTRY, Main.MODID);

    public static RegistryObject<VesselHeadBlock> vesselHeadBlock = registrate.register("vessel_head", VesselHeadBlock::new);
    public static RegistryObject<VesselBlock> vesselBodyBlock = registrate.register("vessel", VesselBlock::new);


    public static void init(IEventBus modEventBus) {
        registrate.register(modEventBus);
    }
}
