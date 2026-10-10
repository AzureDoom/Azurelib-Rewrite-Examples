package mod.azure.azexamples.items.armors;

import net.minecraft.entity.Entity;
import net.minecraft.item.ItemStack;

import mod.azure.azurelib.animation.dispatch.command.AzCommand;

import mod.azure.azexamples.CommonStrings;

public class DoomicornArmorAnimationDispatcher {

    private static final AzCommand IDLE = AzCommand.create(
        CommonStrings.BASE_CONTROLLER,
        CommonStrings.IDLE_ANIMATION_NAME
    );

    public void serverIdle(Entity entity, ItemStack itemStack) {
        IDLE.sendForItem(entity, itemStack);
    }
}
