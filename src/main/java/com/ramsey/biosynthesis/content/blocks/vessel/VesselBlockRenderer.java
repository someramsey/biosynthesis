package com.ramsey.biosynthesis.content.blocks.vessel;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Vector3f;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.state.BlockState;
import software.bernie.geckolib3.geo.render.built.GeoModel;
import software.bernie.geckolib3.renderers.geo.GeoBlockRenderer;
import software.bernie.geckolib3.util.EModelRenderCycle;

public class VesselBlockRenderer extends GeoBlockRenderer<VesselBlockEntity> {
    private final RenderType renderType = RenderType.entityTranslucent(getTextureLocation(animatable));

    public VesselBlockRenderer(BlockEntityRendererProvider.Context rendererProvider) {
        super(rendererProvider, new VesselBlockModel());
    }

    private void rotateBlock(BlockState state, PoseStack poseStack) {
        Alignment alignment = state.getValue(VesselBlock.AlignmentProperty);

        switch (alignment) {
            case South -> poseStack.mulPose(Vector3f.YP.rotationDegrees(180.0F));
            case West -> poseStack.mulPose(Vector3f.YP.rotationDegrees(90.0F));
            case North -> poseStack.mulPose(Vector3f.YP.rotationDegrees(0.0F));
            case East -> poseStack.mulPose(Vector3f.YP.rotationDegrees(270.0F));
        }
    }

    @Override
    public void render(VesselBlockEntity tile, float partialTick, PoseStack poseStack, MultiBufferSource bufferSource, int packedLight) {
        ResourceLocation modelLocation = this.modelProvider.getModelResource(tile);
        GeoModel model = this.modelProvider.getModel(modelLocation);

        this.modelProvider.setCustomAnimations(tile, this.getInstanceId(tile));
        this.dispatchedMat = poseStack.last().pose().copy();

        this.setCurrentModelRenderCycle(EModelRenderCycle.INITIAL);

        poseStack.pushPose();
        poseStack.translate(0.5, 0.0, 0.5);

        this.rotateBlock(tile.getBlockState(), poseStack);

        ResourceLocation textureLocation = this.getTextureLocation(tile);
        RenderSystem.setShaderTexture(0, textureLocation);

        this.render(model, tile, partialTick, this.renderType, poseStack, bufferSource, null, packedLight, OverlayTexture.NO_OVERLAY, 1, 1, 1, 1);

        poseStack.popPose();
    }
}
