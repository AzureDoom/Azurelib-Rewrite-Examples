package mod.azure.azexamples.entities.marauder;

import net.minecraft.client.renderer.block.model.ItemCameraTransforms;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

import java.util.UUID;

import mod.azure.azurelib.model.AzBone;
import mod.azure.azurelib.render.AzRendererPipelineContext;
import mod.azure.azurelib.render.entity.AzEntityRenderer;
import mod.azure.azurelib.render.entity.AzEntityRendererConfig;
import mod.azure.azurelib.render.layer.AzAutoGlowingLayer;
import mod.azure.azurelib.render.layer.AzBlockAndItemLayer;
import mod.azure.azurelib.util.math.Vector3f;

import mod.azure.azexamples.CommonMod;

public class MarauderRenderer extends AzEntityRenderer<MarauderEntity> {

    private static final ResourceLocation MODEL = CommonMod.modResource("geo/entity/marauder.geo.json");

    private static final ResourceLocation TEXTURE = CommonMod.modResource("textures/entity/marauder.png");

    private static final String LEFT_HAND = "item_bone";

    public MarauderRenderer(RenderManager renderManager) {
        super(
            AzEntityRendererConfig.<MarauderEntity>builder(MODEL, TEXTURE)
                .addRenderLayer(new AzAutoGlowingLayer<>())
                .addRenderLayer(new AzBlockAndItemLayer<UUID, MarauderEntity>() {

                    @Override
                    public ItemStack itemStackForBone(AzBone bone, MarauderEntity animatable) {
                        if (bone.getName().equals(LEFT_HAND)) {
                            return animatable.getItemStackFromSlot(EntityEquipmentSlot.MAINHAND);
                        }

                        return null;
                    }

                    @Override
                    protected ItemCameraTransforms.TransformType getTransformTypeForStack(
                        AzBone bone,
                        ItemStack stack,
                        MarauderEntity animatable
                    ) {
                        return ItemCameraTransforms.TransformType.THIRD_PERSON_RIGHT_HAND;
                    }

                    @Override
                    protected void renderItemForBone(
                        AzRendererPipelineContext<UUID, MarauderEntity> context,
                        AzBone bone,
                        ItemStack itemStack,
                        MarauderEntity animatable
                    ) {
                        context.poseStack().mulPose(Vector3f.XP.rotationDegrees(270));
                        context.poseStack().mulPose(Vector3f.YP.rotationDegrees(0));
                        context.poseStack().mulPose(Vector3f.ZP.rotationDegrees(0f));
                        context.poseStack().translate(0.0D, 0.1D, -0.5D);
                        super.renderItemForBone(context, bone, itemStack, animatable);
                    }
                })
                .setRenderEntry(contextPipeline -> {
                    contextPipeline.animatable().updateAnimations();
                    return contextPipeline;
                })
                .setAnimatorProvider(MarauderAnimator::new)
                .setDeathMaxRotation(0F)
                .setShadowRadius(0.5F)
                .build(),
            renderManager
        );
    }
}
