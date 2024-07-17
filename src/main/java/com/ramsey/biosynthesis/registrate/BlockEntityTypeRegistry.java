package com.ramsey.biosynthesis.registrate;

import com.ramsey.biosynthesis.Main;
import com.ramsey.biosynthesis.content.blocks.vessel.VesselBlockEntity;
import net.minecraft.core.Registry;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

import java.util.Arrays;
import java.util.function.Supplier;

@SuppressWarnings("unused")
public abstract class BlockEntityTypeRegistry {
    public static DeferredRegister<BlockEntityType<?>> registrate = DeferredRegister.create(Registry.BLOCK_ENTITY_TYPE_REGISTRY, Main.MODID);

    public static RegistryObject<BlockEntityType<VesselBlockEntity>> vesselBlockEntityType = registrate.register("vessel_entity",
        buildType(VesselBlockEntity::new, BlockRegistry.vesselHeadBlock, BlockRegistry.vesselBlock)
    );

    public static void init(IEventBus modEventBus) {
        registrate.register(modEventBus);
    }

    @SafeVarargs
    @SuppressWarnings("DataFlowIssue")
    private static <T extends BlockEntity> Supplier<BlockEntityType<T>> buildType(BlockEntityType.BlockEntitySupplier<T> pFactory, RegistryObject<? extends Block>... pBlocks) {
        return () -> BlockEntityType.Builder.of(pFactory, Arrays.stream(pBlocks).map(RegistryObject::get).toArray(Block[]::new)).build(null);
    }
}
