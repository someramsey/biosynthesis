package com.ramsey.biosynthesis.content.vessel;

import com.ramsey.biosynthesis.Main;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.state.BlockState;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class VesselBlockModel extends AnimatedGeoModel<VesselBlockEntity> {
    private static final String baseModelDir = "geo/vessel/";

    @Override
    public ResourceLocation getModelResource(VesselBlockEntity vesselBlockEntity) {
        BlockState state = vesselBlockEntity.getBlockState();

        String modelPath = baseModelDir;

        if (state.getValue(VesselBlock.FacingProperty) == Direction.UP) {
            modelPath += "head/";
        } else {
            modelPath += "body/";
        }

        modelPath += "stage" + state.getValue(VesselBlock.AgeProperty) + ".geo.json";

        return new ResourceLocation(Main.MODID, modelPath);
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
