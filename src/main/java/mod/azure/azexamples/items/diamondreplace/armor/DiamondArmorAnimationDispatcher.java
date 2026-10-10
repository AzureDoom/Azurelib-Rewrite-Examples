package mod.azure.azexamples.items.diamondreplace.armor;

import net.minecraft.entity.Entity;
import net.minecraft.item.ItemStack;

import mod.azure.azurelib.animation.dispatch.command.AzCommand;
import mod.azure.azurelib.animation.play_behavior.AzPlayBehaviors;

import mod.azure.azexamples.CommonStrings;

public class DiamondArmorAnimationDispatcher {

    private static final AzCommand EQUIP = AzCommand.create(
        CommonStrings.BASE_CONTROLLER,
        CommonStrings.IDLE_ANIMATION_NAME,
        AzPlayBehaviors.HOLD_ON_LAST_FRAME
    );

    public void serverIdleArmor(Entity entity, ItemStack itemStack) {
        EQUIP.sendForItem(entity, itemStack);
    }
}
