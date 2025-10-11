package mod.azure.azexamples.items.gunwitharm;

import mod.azure.azurelib.common.animation.dispatch.command.AzCommand;
import mod.azure.azurelib.common.animation.play_behavior.AzPlayBehaviors;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.ItemStack;

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
        FIRING_COMMAND.sendForItem(entity, itemStack);
    }

    public void clientThirdPersonFire(Entity entity, ItemStack itemStack) {
        THIRD_PERSON_FIRING_COMMAND.sendForItem(entity, itemStack);
    }
}
