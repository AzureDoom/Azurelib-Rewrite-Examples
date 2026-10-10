package mod.azure.azexamples.entities.marine;

import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;

import java.util.UUID;
import javax.annotation.Nonnull;

import mod.azure.azurelib.model.AzBone;
import mod.azure.azurelib.render.AzRendererPipelineContext;
import mod.azure.azurelib.render.layer.AzArmorLayer;

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
    protected @Nonnull EntityEquipmentSlot getEquipmentSlotForBone(
        AzRendererPipelineContext<UUID, MarineEntity> context,
        AzBone bone,
        ItemStack stack
    ) {
        MarineEntity animatable = context.animatable();

        switch (bone.getName()) {
            case LEFT_BOOT:
            case RIGHT_BOOT:
                return EntityEquipmentSlot.FEET;
            case LEFT_ARMOR_LEG:
            case RIGHT_ARMOR_LEG:
                return EntityEquipmentSlot.LEGS;
            case RIGHT_SLEEVE:
                return !animatable.isLeftHanded() ? EntityEquipmentSlot.MAINHAND : EntityEquipmentSlot.OFFHAND;
            case LEFT_SLEEVE:
                return animatable.isLeftHanded() ? EntityEquipmentSlot.OFFHAND : EntityEquipmentSlot.MAINHAND;
            case CHESTPLATE:
                return EntityEquipmentSlot.CHEST;
            case HELMET:
                return EntityEquipmentSlot.HEAD;
            default:
                return super.getEquipmentSlotForBone(context, bone, stack);
        }
    }

    @Override
    protected @Nonnull ModelRenderer getModelPartForBone(
        AzRendererPipelineContext<UUID, MarineEntity> context,
        AzBone bone,
        ModelBiped baseModel
    ) {
        switch (bone.getName()) {
            case HELMET:
                return baseModel.bipedHead;
            case CHESTPLATE:
                return baseModel.bipedBody;
            case LEFT_BOOT:
            case LEFT_ARMOR_LEG:
                return baseModel.bipedLeftLeg;
            case RIGHT_BOOT:
            case RIGHT_ARMOR_LEG:
                return baseModel.bipedRightLeg;
            case RIGHT_SLEEVE:
                return baseModel.bipedRightArm;
            case LEFT_SLEEVE:
                return baseModel.bipedLeftArm;
            default:
                return super.getModelPartForBone(context, bone, baseModel);
        }
    }
}
