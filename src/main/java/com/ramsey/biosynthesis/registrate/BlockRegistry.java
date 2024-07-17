package com.ramsey.biosynthesis.registrate;

import com.ramsey.biosynthesis.Main;
import com.ramsey.biosynthesis.content.blocks.branch.BranchBlock;
import com.ramsey.biosynthesis.content.blocks.branch.BranchStemBlock;
import com.ramsey.biosynthesis.content.blocks.vessel.VesselBlock;
import com.ramsey.biosynthesis.content.blocks.vessel.VesselHeadBlock;
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

    private static final BlockBehaviour.Properties biologicalBlockProperties = BlockBehaviour.Properties.of(Material.BARRIER).noOcclusion().strength(-1.0F, 3600000.0F).noLootTable();

    public static RegistryObject<VesselHeadBlock> vesselHeadBlock = registrate.register("vessel_head", () -> new VesselHeadBlock(biologicalBlockProperties));
    public static RegistryObject<VesselBlock> vesselBlock = registrate.register("vessel", () -> new VesselBlock(biologicalBlockProperties));

    public static RegistryObject<Block> branchBlock = registrate.register("branch", () -> new BranchBlock(biologicalBlockProperties));
    public static RegistryObject<Block> stemBlock = registrate.register("stem", () -> new BranchStemBlock(biologicalBlockProperties));


    public static void init(IEventBus modEventBus) {
        registrate.register(modEventBus);
    }
}
