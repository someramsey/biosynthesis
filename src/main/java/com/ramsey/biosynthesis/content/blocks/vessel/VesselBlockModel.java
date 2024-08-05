package com.ramsey.biosynthesis.content.blocks.vessel;

import com.ramsey.biosynthesis.Main;
import com.ramsey.biosynthesis.data.providers.block.common.vessel.VesselBlockModelProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.state.BlockState;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class VesselBlockModel extends AnimatedGeoModel<VesselBlockEntity> {
    private final ResourceLocation animationLocation = new ResourceLocation(Main.MODID, "animations/vessel.animation.json");
    private final ResourceLocation textureLocation = new ResourceLocation(Main.MODID, "textures/block/vessel.png");

    @Override
    public ResourceLocation getModelResource(VesselBlockEntity vesselBlockEntity) {
        BlockState state = vesselBlockEntity.getBlockState();
        String modelKey = VesselBlockModelProvider.getModelKey(state);

        return new ResourceLocation(Main.MODID, "geo/vessel/" + modelKey + ".geo.json");
    }

    @Override
    public ResourceLocation getAnimationResource(VesselBlockEntity vesselBlockEntity) {
        return animationLocation;
    }

    @Override
    public ResourceLocation getTextureResource(VesselBlockEntity vesselBlockEntity) {
        return textureLocation;
    }
}
