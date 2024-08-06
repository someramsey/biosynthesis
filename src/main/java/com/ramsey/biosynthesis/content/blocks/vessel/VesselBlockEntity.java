package com.ramsey.biosynthesis.content.blocks.vessel;

import com.ramsey.biosynthesis.registrate.BlockEntityTypeRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;

import java.util.ArrayList;

@SuppressWarnings("removal")
public class VesselBlockEntity extends BlockEntity implements IAnimatable {
    private final ArrayList<TreePart> treeParts = new ArrayList<>();

    public VesselBlockEntity(@NotNull BlockPos pPos, @NotNull BlockState pState) {
        super(BlockEntityTypeRegistry.vesselBlockEntityType.get(), pPos, pState);
    }
    @Override
    public AnimationFactory getFactory() {
        return new AnimationFactory(this);
    }

    @Override
    public void registerControllers(AnimationData animationData) {
        animationData.addAnimationController(new AnimationController<>(this, "controller", 0,
            VesselBlockModel::animationControllerPredicate
        ));
    }

    public static class TreePart {


    }
}
