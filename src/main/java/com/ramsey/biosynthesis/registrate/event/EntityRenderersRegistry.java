package com.ramsey.biosynthesis.registrate.event;

import com.ramsey.biosynthesis.Main;
import com.ramsey.biosynthesis.content.vessel.VesselBlockRenderer;
import com.ramsey.biosynthesis.registrate.BlockEntityTypeRegistry;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = Main.MODID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class EntityRenderersRegistry {
    @SubscribeEvent
    public static void registerRenderers(EntityRenderersEvent.RegisterRenderers event) {
        event.registerBlockEntityRenderer(BlockEntityTypeRegistry.vesselBlockEntityType.get(), VesselBlockRenderer::new);
    }
}
