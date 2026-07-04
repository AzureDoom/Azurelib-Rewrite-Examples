package mod.azure.azexamples.entities.marauder;

import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.Identifier;

import mod.azure.azexamples.CommonMod;

public class MarauderRenderer { // extends AzEntityRenderer<MarauderEntity> {

    private static final Identifier MODEL = CommonMod.modResource("geo/entity/marauder.geo.json");

    private static final Identifier TEXTURE = CommonMod.modResource("textures/entity/marauder.png");

    public MarauderRenderer(EntityRendererProvider.Context context) {
        // super(
        // AzEntityRendererConfig.<MarauderEntity>builder(MODEL, TEXTURE)
        // .addRenderLayer(new AzAutoGlowingLayer<>())
        // .addRenderLayer(new AzBlockAndItemLayer<>() {
        //
        // private static final String LEFT_HAND = "item_bone";
        //
        // @Override
        // public ItemStack itemStackForBone(AzBone bone, MarauderEntity animatable) {
        // if (bone.getName().equals(LEFT_HAND)) {
        // return animatable.getItemBySlot(EquipmentSlot.MAINHAND);
        // }
        // return null;
        // }
        //
        // @Override
        // protected ItemDisplayContext getTransformTypeForStack(
        // AzBone bone,
        // ItemStack stack,
        // MarauderEntity animatable
        // ) {
        // return ItemDisplayContext.THIRD_PERSON_RIGHT_HAND;
        // }
        //
        // @Override
        // protected void renderItemForBone(
        // AzRendererPipelineContext<MarauderEntity> context,
        // AzBone bone,
        // ItemStack itemStack,
        // MarauderEntity animatable
        // ) {
        // context.poseStack().mulPose(Axis.XP.rotationDegrees(270));
        // context.poseStack().mulPose(Axis.YP.rotationDegrees(0));
        // context.poseStack().mulPose(Axis.ZP.rotationDegrees(0f));
        // context.poseStack().translate(0.0D, 0.1D, -0.5D);
        // super.renderItemForBone(context, bone, itemStack, animatable);
        // }
        // })
        // .setRenderEntry(contextPipeline -> {
        // contextPipeline.animatable().updateAnimations();
        //
        // return contextPipeline;
        // })
        // .setAnimatorProvider(MarauderAnimator::new)
        // .setDeathMaxRotation(0F)
        // .setShadowRadius(0.5F)
        // .build(),
        // context
        // );
    }
}
