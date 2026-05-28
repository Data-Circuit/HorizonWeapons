package io.github.datacircuit.horizonweapons.client.render;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import io.github.datacircuit.horizonweapons.block.entity.PlinthBlockEntity;
import net.minecraft.client.gui.Font;
import net.minecraft.client.renderer.Lightmap;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.feature.ModelFeatureRenderer;
import net.minecraft.client.renderer.item.ItemModelResolver;
import net.minecraft.client.renderer.state.LightmapRenderState;
import net.minecraft.client.renderer.state.level.CameraRenderState;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.network.chat.Style;
import net.minecraft.util.FormattedCharSequence;
import net.minecraft.util.LightCoordsUtil;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.LightLayer;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.phys.Vec3;
import org.jspecify.annotations.Nullable;

import javax.swing.*;

public class PlinthBlockEntityRenderer implements BlockEntityRenderer<PlinthBlockEntity, PlinthBlockEntityRenderState> {
    private final ItemModelResolver itemModelResolver;

    public PlinthBlockEntityRenderer(BlockEntityRendererProvider.Context context) {
        itemModelResolver = context.itemModelResolver();
    }

    @Override
    public PlinthBlockEntityRenderState createRenderState() {
        return new PlinthBlockEntityRenderState();
    }

    @Override
    public void extractRenderState(PlinthBlockEntity blockEntity, PlinthBlockEntityRenderState state, float partialTicks, Vec3 cameraPosition, ModelFeatureRenderer.@Nullable CrumblingOverlay breakProgress) {
        BlockEntityRenderer.super.extractRenderState(blockEntity, state, partialTicks, cameraPosition, breakProgress);
        state.setStack(blockEntity.getItem(0));

        itemModelResolver.appendItemLayers(state.itemStackRenderState,
                state.getStack(), ItemDisplayContext.FIXED, blockEntity.getLevel(), null, 0);
        state.setDirection(blockEntity.getBlockState().getValue(BlockStateProperties.FACING));
    }

    @Override
    public void submit(PlinthBlockEntityRenderState state, PoseStack poseStack, SubmitNodeCollector submitNodeCollector, CameraRenderState camera) {
        poseStack.pushPose();
        poseStack.translate(0.5, 1.15, 0.5);
        poseStack.scale(.5f, .5f, .5f);

        switch (state.getDirection()) {
            case NORTH -> poseStack.mulPose(Axis.YP.rotationDegrees(0));
            case EAST -> poseStack.mulPose(Axis.YP.rotationDegrees(90));
            case SOUTH -> poseStack.mulPose(Axis.YP.rotationDegrees(180));
            case WEST -> poseStack.mulPose(Axis.YP.rotationDegrees(270));
            default -> {
            }
        }

        if (state.getStack() != ItemStack.EMPTY) {
            state.itemStackRenderState.submit(poseStack, submitNodeCollector, state.lightCoords, OverlayTexture.NO_OVERLAY, 0);
        }

        poseStack.popPose();
    }
}
