package mod.azure.azexamples.entities.marine;

import mod.azure.azurelib.model.AzBone;
import mod.azure.azurelib.render.AzRendererPipelineContext;
import mod.azure.azurelib.render.layer.AzArmorLayer;
import mod.azure.azurelib.util.AzEquipmentSlot;
import net.minecraft.client.model.ModelPart;
import net.minecraft.client.render.entity.model.BipedEntityModel;
import net.minecraft.item.ItemStack;

import java.util.UUID;
import javax.annotation.Nonnull;

public class MarineArmorLayer extends AzArmorLayer<MarineEntity> {

    private static final String LEFT_BOOT = "armorBipedLeftFoot";

    private static final String RIGHT_BOOT = "armorBipedRightFoot";

    private static final String LEFT_ARMOR_LEG = "armorBipedLeftLeg";

    private static final String RIGHT_ARMOR_LEG = "armorBipedRightLeg";

    private static final String CHESTPLATE = "armorBipedBody";

    private static final String RIGHT_SLEEVE = "armorBipedRightArm";

    private static final String LEFT_SLEEVE = "armorBipedLeftArm";

    private static final String HELMET = "armorBipedHead";

    @Override
    protected ItemStack getArmorItemForBone(AzRendererPipelineContext<UUID, MarineEntity> context, AzBone bone) {
        switch (bone.getName()) {
            case LEFT_BOOT:
            case RIGHT_BOOT:
                return this.bootsStack;
            case LEFT_ARMOR_LEG:
            case RIGHT_ARMOR_LEG:
                return this.leggingsStack;
            case CHESTPLATE:
            case RIGHT_SLEEVE:
            case LEFT_SLEEVE:
                return this.chestplateStack;
            case HELMET:
                return this.helmetStack;
            default:
                return null;
        }
    }

    @Override
    protected @Nonnull AzEquipmentSlot getEquipmentSlotForBone(
        AzRendererPipelineContext<UUID, MarineEntity> context,
        AzBone bone,
        ItemStack stack
    ) {
        MarineEntity animatable = context.animatable();

        switch (bone.getName()) {
            case LEFT_BOOT:
            case RIGHT_BOOT:
                return AzEquipmentSlot.FEET;
            case LEFT_ARMOR_LEG:
            case RIGHT_ARMOR_LEG:
                return AzEquipmentSlot.LEGS;
            case RIGHT_SLEEVE:
                return !animatable.isLeftHanded() ? AzEquipmentSlot.MAINHAND : AzEquipmentSlot.OFFHAND;
            case LEFT_SLEEVE:
                return animatable.isLeftHanded() ? AzEquipmentSlot.OFFHAND : AzEquipmentSlot.MAINHAND;
            case CHESTPLATE:
                return AzEquipmentSlot.CHEST;
            case HELMET:
                return AzEquipmentSlot.HEAD;
            default:
                return super.getEquipmentSlotForBone(context, bone, stack);
        }
    }

    @Override
    protected @Nonnull ModelPart getModelPartForBone(
        AzRendererPipelineContext<UUID, MarineEntity> context,
        AzBone bone,
        BipedEntityModel baseModel
    ) {
        switch (bone.getName()) {
            case HELMET:
                return baseModel.head;
            case CHESTPLATE:
                return baseModel.body;
            case LEFT_BOOT:
            case LEFT_ARMOR_LEG:
                return baseModel.leftLeg;
            case RIGHT_BOOT:
            case RIGHT_ARMOR_LEG:
                return baseModel.rightLeg;
            case RIGHT_SLEEVE:
                return baseModel.rightArm;
            case LEFT_SLEEVE:
                return baseModel.leftArm;
            default:
                return super.getModelPartForBone(context, bone, baseModel);
        }
    }
}
