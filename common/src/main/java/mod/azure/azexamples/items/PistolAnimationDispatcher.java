package mod.azure.azexamples.items;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.ItemStack;

public class PistolAnimationDispatcher {

    // private static final AzCommand FIRING_COMMAND = AzCommand.create(
    // CommonStrings.BASE_CONTROLLER,
    // CommonStrings.FIRING_ANIMATION_NAME,
    // AzPlayBehaviors.PLAY_ONCE
    // );

    public void serverFire(Entity entity, ItemStack itemStack) {
        // FIRING_COMMAND.sendForItem(entity, itemStack);
    }
}
