package com.ramsey.biosynthesis.registry;

import com.ramsey.biosynthesis.Main;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public abstract class ParticleTypeRegistry {
    private static final DeferredRegister<ParticleType<?>> registrate = DeferredRegister.create(ForgeRegistries.PARTICLE_TYPES, Main.MODID);

    public static final RegistryObject<SimpleParticleType> bloodParticleType = registrate.register("blood", () -> new SimpleParticleType(false));

    public static void init(IEventBus eventBus) {
        registrate.register(eventBus);
    }
}
