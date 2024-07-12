package com.ramsey.biosynthesis.content.blocks.vessel;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Vector3f;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib3.renderers.geo.GeoBlockRenderer;

public class VesselBlockRenderer extends GeoBlockRenderer<VesselBlockEntity> {
    public VesselBlockRenderer(BlockEntityRendererProvider.Context rendererProvider) {
        super(rendererProvider, new VesselBlockModel());
    }

    @Override
    protected void rotateBlock(Direction facing, PoseStack poseStack) {
        switch (facing) {
            case SOUTH -> poseStack.mulPose(Vector3f.YP.rotationDegrees(180.0F));
            case WEST -> poseStack.mulPose(Vector3f.YP.rotationDegrees(90.0F));
            case NORTH -> poseStack.mulPose(Vector3f.YP.rotationDegrees(0.0F));
            case EAST -> poseStack.mulPose(Vector3f.YP.rotationDegrees(270.0F));
            case DOWN -> poseStack.mulPose(Vector3f.XN.rotationDegrees(180.0F));
        }
    }

    @Override
    public RenderType getRenderType(VesselBlockEntity animatable, float partialTick, PoseStack poseStack, @Nullable MultiBufferSource bufferSource, @Nullable VertexConsumer buffer, int packedLight, ResourceLocation texture) {
        return RenderType.entityTranslucent(getTextureLocation(animatable));
    }
}
