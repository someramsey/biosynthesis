package com.ramsey.biosynthesis.content.blocks.vessel;

import com.ramsey.biosynthesis.registrate.BlockEntityTypeRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;
import software.bernie.geckolib3.util.GeckoLibUtil;

import java.util.Random;

@SuppressWarnings("ALL")
public class VesselBlockEntity extends BlockEntity implements IAnimatable {
    private static final AnimationBuilder animationBuilder = new AnimationBuilder().addAnimation("idle", true);
    private static final Random random = new Random();

    private final AnimationFactory factory = GeckoLibUtil.createFactory(this);
    private final AnimationController<VesselBlockEntity> controller = new AnimationController<>(this, "controller", 0, this::controllerPredicate);

    public VesselBlockEntity(@NotNull BlockPos pPos, @NotNull BlockState pState) {
        super(BlockEntityTypeRegistry.vesselBlockEntityType.get(), pPos, pState);
    }

    @Override
    public AnimationFactory getFactory() {
        return factory;
    }

    @Override
    public void registerControllers(AnimationData animationData) {
        animationData.addAnimationController(controller);
    }

    private PlayState controllerPredicate(AnimationEvent<VesselBlockEntity> event) {
        AnimationController<VesselBlockEntity> controller = event.getController();
        
        if(controller.isJustStarting) {
            controller.setAnimation(animationBuilder);
            controller.tickOffset = random.nextInt(20);
            controller.setAnimationSpeed(random.nextDouble(0.8f, 1.1f));
        }

        return PlayState.CONTINUE;
    }
}
