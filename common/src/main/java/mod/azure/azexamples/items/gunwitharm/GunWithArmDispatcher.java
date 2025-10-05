package mod.azure.azexamples.items.gunwitharm;

import mod.azure.azurelib.rewrite.animation.dispatch.command.AzCommand;
import mod.azure.azurelib.rewrite.animation.play_behavior.AzPlayBehaviors;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.ItemStack;

import mod.azure.azexamples.CommonStrings;

public class GunWithArmDispatcher {

    private static final AzCommand FIRING_COMMAND = AzCommand.create(
        CommonStrings.BASE_CONTROLLER,
        "aimfiring",
        AzPlayBehaviors.PLAY_ONCE
    );

    private static final AzCommand IDLE_COMMAND = AzCommand.create(
        CommonStrings.BASE_CONTROLLER,
        "aim",
        AzPlayBehaviors.LOOP
    );

    public void serverFire(Entity entity, ItemStack itemStack) {
        FIRING_COMMAND.sendForItem(entity, itemStack);
    }

    public void sendIdle(Entity entity, ItemStack itemStack) {
        IDLE_COMMAND.sendForItem(entity, itemStack);
    }
}
