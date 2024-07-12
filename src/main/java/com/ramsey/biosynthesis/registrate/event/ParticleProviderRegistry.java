package com.ramsey.biosynthesis.registrate.event;

import com.ramsey.biosynthesis.Main;
import com.ramsey.biosynthesis.content.particles.BloodParticle;
import com.ramsey.biosynthesis.registrate.ParticleTypeRegistry;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RegisterParticleProvidersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = Main.MODID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ParticleProviderRegistry {
    @SubscribeEvent
    public static void registerRenderers(RegisterParticleProvidersEvent event) {
        event.register(ParticleTypeRegistry.bloodParticleType.get(), BloodParticle.Provider::new);
    }
}
