package com.ramsey.biosynthesis.content.vessel;

import com.ramsey.biosynthesis.Main;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class VesselBlockModel extends AnimatedGeoModel<VesselBlockEntity> {
    @Override
    public ResourceLocation getModelResource(VesselBlockEntity vesselBlockEntity) {
        return new ResourceLocation(Main.MODID, "geo/vessel/head/stage1.geo.json");
    }

    @Override
    public ResourceLocation getAnimationResource(VesselBlockEntity vesselBlockEntity) {
        return new ResourceLocation(Main.MODID, "animations/vessel.animation.json");
    }

    @Override
    public ResourceLocation getTextureResource(VesselBlockEntity vesselBlockEntity) {
        return new ResourceLocation(Main.MODID, "textures/block/vessel.png");
    }
}
