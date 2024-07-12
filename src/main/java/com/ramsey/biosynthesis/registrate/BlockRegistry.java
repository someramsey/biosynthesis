package com.ramsey.biosynthesis.registrate;

import com.ramsey.biosynthesis.Main;
import com.ramsey.biosynthesis.content.vessel.VesselBlock;
import net.minecraft.core.Registry;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.Material;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

@SuppressWarnings("unused")
public abstract class BlockRegistry {
    public static DeferredRegister<Block> registrate = DeferredRegister.create(Registry.BLOCK_REGISTRY, Main.MODID);

    public static RegistryObject<VesselBlock> vesselBlock = registrate.register("vessel", () -> new VesselBlock(BlockBehaviour.Properties.of(Material.STONE).noOcclusion().strength(-1.0F, 3600000.0F).noLootTable()));

    public static void init(IEventBus modEventBus) {
        registrate.register(modEventBus);
    }
}
