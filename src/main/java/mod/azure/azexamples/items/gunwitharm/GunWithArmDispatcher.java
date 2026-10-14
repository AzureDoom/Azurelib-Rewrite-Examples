package mod.azure.azexamples.items.gunwitharm;

import mod.azure.azurelib.animation.dispatch.command.AzCommand;
import mod.azure.azurelib.animation.play_behavior.AzPlayBehaviors;
import net.minecraft.entity.Entity;
import net.minecraft.item.ItemStack;

import mod.azure.azexamples.CommonStrings;

public class GunWithArmDispatcher {

    private static final AzCommand FIRING_COMMAND = AzCommand.create(
        CommonStrings.BASE_CONTROLLER,
        "aimfiring",
        AzPlayBehaviors.PLAY_ONCE
    );

    private static final AzCommand THIRD_PERSON_FIRING_COMMAND = AzCommand.create(
        CommonStrings.BASE_CONTROLLER,
        "firing",
        AzPlayBehaviors.PLAY_ONCE
    );

    public void serverFire(Entity entity, ItemStack itemStack) {
        FIRING_COMMAND.sendForItemFirstPerson(entity, itemStack);
        THIRD_PERSON_FIRING_COMMAND.sendForItemThirdPerson(entity, itemStack);
    }
}
