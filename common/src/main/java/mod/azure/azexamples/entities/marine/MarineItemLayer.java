package mod.azure.azexamples.entities.marine;

public class MarineItemLayer { // extends AzBlockAndItemLayer<MarineEntity> {

    private static final String LEFT_HAND = "leftHand_Item";

    private static final String RIGHT_HAND = "rightHand_Item";

    // @Override
    // public ItemStack itemStackForBone(AzBone bone, MarineEntity animatable) {
    // return switch (bone.getName()) {
    // case RIGHT_HAND -> animatable.getItemBySlot(EquipmentSlot.MAINHAND);
    // case LEFT_HAND -> animatable.getItemBySlot(EquipmentSlot.OFFHAND);
    // default -> null;
    // };
    // }
    //
    // @Override
    // protected ItemDisplayContext getTransformTypeForStack(
    // AzBone bone,
    // ItemStack stack,
    // MarineEntity animatable
    // ) {
    // return ItemDisplayContext.THIRD_PERSON_RIGHT_HAND;
    // }
    //
    // @Override
    // protected void renderItemForBone(
    // AzRendererPipelineContext<MarineEntity> context,
    // AzBone bone,
    // ItemStack itemStack,
    // MarineEntity animatable
    // ) {
    // context.poseStack().mulPose(Axis.XP.rotationDegrees(270));
    // context.poseStack().mulPose(Axis.YP.rotationDegrees(0));
    // context.poseStack().mulPose(Axis.ZP.rotationDegrees(0f));
    // context.poseStack().translate(0.0D, 0.1D, -0.1D);
    // super.renderItemForBone(context, bone, itemStack, animatable);
    // }
}
