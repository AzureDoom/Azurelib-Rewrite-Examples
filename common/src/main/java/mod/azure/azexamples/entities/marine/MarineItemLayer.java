package mod.azure.azexamples.entities.marine;

import com.mojang.math.Vector3f;
import mod.azure.azurelib.rewrite.model.AzBone;
import mod.azure.azurelib.rewrite.render.AzRendererPipelineContext;
import mod.azure.azurelib.rewrite.render.layer.AzBlockAndItemLayer;
import net.minecraft.client.renderer.block.model.ItemTransforms;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ItemStack;

public class MarineItemLayer extends AzBlockAndItemLayer<MarineEntity> {

    private static final String LEFT_HAND = "leftHand_Item";

    private static final String RIGHT_HAND = "rightHand_Item";

    @Override
    public ItemStack itemStackForBone(AzBone bone, MarineEntity animatable) {
        return switch (bone.getName()) {
            case RIGHT_HAND -> animatable.getItemBySlot(EquipmentSlot.MAINHAND);
            case LEFT_HAND -> animatable.getItemBySlot(EquipmentSlot.OFFHAND);
            default -> null;
        };
    }

    @Override
    protected ItemTransforms.TransformType getTransformTypeForStack(
        AzBone bone,
        ItemStack stack,
        MarineEntity animatable
    ) {
        return ItemTransforms.TransformType.THIRD_PERSON_RIGHT_HAND;
    }

    @Override
    protected void renderItemForBone(
        AzRendererPipelineContext<MarineEntity> context,
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
