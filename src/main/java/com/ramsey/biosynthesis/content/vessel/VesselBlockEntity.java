package com.ramsey.biosynthesis.content.vessel;

import com.ramsey.biosynthesis.registrate.BlockEntityTypeRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;

public class VesselBlockEntity extends BlockEntity implements Ianim  {
    public VesselBlockEntity(@NotNull BlockPos pPos, @NotNull BlockState pState) {
        super(BlockEntityTypeRegistry.vesselBlockEntityType.get(), pPos, pState);
    }
}
