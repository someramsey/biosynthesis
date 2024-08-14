package com.ramsey.biosynthesis;

import com.mojang.logging.LogUtils;
import com.ramsey.biosynthesis.registry.BlockEntityTypeRegistry;
import com.ramsey.biosynthesis.registry.BlockRegistry;
import com.ramsey.biosynthesis.registry.ItemRegistry;
import com.ramsey.biosynthesis.registry.ParticleTypeRegistry;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.slf4j.Logger;
import software.bernie.geckolib3.GeckoLib;

@Mod(Main.MODID)
public class Main {
    public static final String MODID = "biosynthesis";
    public static final Logger LOGGER = LogUtils.getLogger();

    public Main() {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();

        modEventBus.addListener(this::commonSetup);

        GeckoLib.initialize();

        BlockRegistry.init(modEventBus);
        ItemRegistry.init(modEventBus);
        BlockEntityTypeRegistry.init(modEventBus);
        ParticleTypeRegistry.init(modEventBus);

        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, Config.SPEC);
    }

    private void commonSetup(final FMLCommonSetupEvent event) { }
}
