package com.ramsey.biosynthesis.content.blocks.vessel;

import com.ramsey.biosynthesis.Main;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class VesselBlockModel extends AnimatedGeoModel<VesselBlockEntity> {
    @Override
    public ResourceLocation getModelResource(VesselBlockEntity vesselBlockEntity) {
        BlockState state = vesselBlockEntity.getBlockState();
        String modelPath = getModelPath(state);

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

    private static @NotNull String getModelPath(BlockState state) {
        String basePath = "geo/vessel/";
        int age = state.getValue(VesselBlock.AgeProperty);

        if (age == 5) {
            return basePath + "full.geo.json";
        }

        Direction direction = state.getValue(VesselBlock.FacingProperty);

        if (direction == Direction.UP) {
            basePath += "head/";
        } else {
            basePath += "body/";
        }

        basePath += "stage" + age + ".geo.json";

        return basePath;
    }
}
