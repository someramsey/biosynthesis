package com.ramsey.biosynthesis.content.blocks.vessel;

import com.ramsey.biosynthesis.Main;
import com.ramsey.biosynthesis.data.providers.block.common.vessel.VesselBlockModelProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.state.BlockState;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.model.AnimatedGeoModel;

import java.util.Random;

public class VesselBlockModel extends AnimatedGeoModel<VesselBlockEntity> {
    private static final Random random = new Random();
    private final ResourceLocation animationLocation = new ResourceLocation(Main.MODID, "animations/vessel.animation.json");
    private final ResourceLocation textureLocation = new ResourceLocation(Main.MODID, "textures/block/vessel.png");

    public static final AnimationBuilder idleAnimation = new AnimationBuilder()
        .loop("idle");

    @Override
    public ResourceLocation getAnimationResource(VesselBlockEntity vesselBlockEntity) {
        return animationLocation;
    }

    @Override
    public ResourceLocation getTextureResource(VesselBlockEntity vesselBlockEntity) {
        return textureLocation;
    }

    @Override
    public ResourceLocation getModelResource(VesselBlockEntity vesselBlockEntity) {
        BlockState state = vesselBlockEntity.getBlockState();
        String modelKey = VesselBlockModelProvider.getModelKey(state);

        return new ResourceLocation(Main.MODID, "geo/vessel/" + modelKey + ".geo.json");
    }

    public static PlayState animationControllerPredicate(AnimationEvent<VesselBlockEntity> event) {
        AnimationController<VesselBlockEntity> controller = event.getController();

        if (controller.isJustStarting) {
            controller.setAnimation(VesselBlockModel.idleAnimation);
            controller.tickOffset = random.nextInt(20);
            controller.setAnimationSpeed(random.nextDouble(0.8f, 1.1f));
        }

        return PlayState.CONTINUE;
    }
}
