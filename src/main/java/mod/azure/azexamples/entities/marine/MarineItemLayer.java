package mod.azure.azexamples.entities.marine;

import net.minecraft.item.ItemStack;

import java.util.UUID;

import mod.azure.azurelib.model.AzBone;
import mod.azure.azurelib.render.AzRendererPipelineContext;
import mod.azure.azurelib.render.item.AzItemDisplayContext;
import mod.azure.azurelib.render.layer.AzBlockAndItemLayer;
import mod.azure.azurelib.util.AzEquipmentSlot;
import mod.azure.azurelib.util.math.Vector3f;

public class MarineItemLayer extends AzBlockAndItemLayer<UUID, MarineEntity> {

    private static final String LEFT_HAND = "leftHand_Item";

    private static final String RIGHT_HAND = "rightHand_Item";

    @Override
    public ItemStack itemStackForBone(AzBone bone, MarineEntity animatable) {
        switch (bone.getName()) {
            case RIGHT_HAND:
                return AzEquipmentSlot.MAINHAND.getStack(animatable);
            case LEFT_HAND:
                return AzEquipmentSlot.OFFHAND.getStack(animatable);
            default:
                return null;
        }
    }

    @Override
    protected AzItemDisplayContext getTransformTypeForStack(
        AzBone bone,
        ItemStack stack,
        MarineEntity animatable
    ) {
        return AzItemDisplayContext.THIRD_PERSON_RIGHT_HAND;
    }

    @Override
    protected void renderItemForBone(
        AzRendererPipelineContext<UUID, MarineEntity> context,
        AzBone bone,
        ItemStack itemStack,
        MarineEntity animatable
    ) {
        context.poseStack().mulPose(Vector3f.XP.rotationDegrees(270));
        context.poseStack().mulPose(Vector3f.YP.rotationDegrees(0));
        context.poseStack().mulPose(Vector3f.ZP.rotationDegrees(0f));
        context.poseStack().translate(0.0D, 0.1D, -0.1D);
        super.renderItemForBone(context, bone, itemStack, animatable);
    }
}
