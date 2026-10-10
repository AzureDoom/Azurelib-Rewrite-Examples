package mod.azure.azexamples.items.diamondreplace;

import net.minecraft.entity.Entity;
import net.minecraft.item.ItemStack;

import mod.azure.azurelib.animation.dispatch.command.AzCommand;
import mod.azure.azurelib.animation.play_behavior.AzPlayBehaviors;

import mod.azure.azexamples.CommonStrings;

public class DiamondSwordAnimationDispatcher {

    private static final AzCommand OPENING_COMMAND = AzCommand.create(
        CommonStrings.BASE_CONTROLLER,
        "opening",
        AzPlayBehaviors.PLAY_ONCE
    );

    private static final AzCommand CLOSED_COMMAND = AzCommand.create(
        CommonStrings.BASE_CONTROLLER,
        "closed",
        AzPlayBehaviors.HOLD_ON_LAST_FRAME
    );

    public void serverOpening(Entity entity, ItemStack itemStack) {
        OPENING_COMMAND.sendForItem(entity, itemStack);
    }

    public void serverClosed(Entity entity, ItemStack itemStack) {
        CLOSED_COMMAND.sendForItem(entity, itemStack);
    }
}
