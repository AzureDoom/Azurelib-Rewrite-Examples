package mod.azure.azexamples.items;

import net.minecraft.entity.Entity;
import net.minecraft.item.ItemStack;

import mod.azure.azurelib.animation.dispatch.command.AzCommand;
import mod.azure.azurelib.animation.play_behavior.AzPlayBehaviors;

import mod.azure.azexamples.CommonStrings;

public class PistolAnimationDispatcher {

    private static final AzCommand FIRING_COMMAND = AzCommand.create(
        CommonStrings.BASE_CONTROLLER,
        CommonStrings.FIRING_ANIMATION_NAME,
        AzPlayBehaviors.PLAY_ONCE
    );

    public void serverFire(Entity entity, ItemStack itemStack) {
        FIRING_COMMAND.sendForItem(entity, itemStack);
    }
}
