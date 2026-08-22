package io.github.datacircuit.horizonweapons.client.render;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import io.github.datacircuit.horizonweapons.block.entity.PlinthBlockEntity;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import org.jetbrains.annotations.NotNull;

public class PlinthBlockEntityRenderer implements BlockEntityRenderer<PlinthBlockEntity> {
    private final ItemRenderer itemRenderer;

    public PlinthBlockEntityRenderer(BlockEntityRendererProvider.Context context) {
        itemRenderer = context.getItemRenderer();
    }

    @Override
    public void render(@NotNull PlinthBlockEntity blockEntity, float partialTick, @NotNull PoseStack stack, @NotNull MultiBufferSource bufferSource, int packedLight, int packedOverlay) {
        stack.pushPose();
        stack.translate(0.5, 1.15, 0.5);
        stack.scale(.5f, .5f, .5f);

        switch (blockEntity.getBlockState().getValue(BlockStateProperties.FACING)) {
            case NORTH -> stack.mulPose(Axis.YP.rotationDegrees(0));
            case EAST -> stack.mulPose(Axis.YP.rotationDegrees(90));
            case SOUTH -> stack.mulPose(Axis.YP.rotationDegrees(180));
            case WEST -> stack.mulPose(Axis.YP.rotationDegrees(270));
            default -> {}
        }

        if (!blockEntity.getItems().isEmpty()) {
            itemRenderer.render(blockEntity.getItem(0), ItemDisplayContext.FIXED, false, stack, bufferSource, packedLight, packedOverlay, itemRenderer.getModel(blockEntity.getItem(0), blockEntity.getLevel(), null, 0));
        }

        stack.popPose();
    }
}
