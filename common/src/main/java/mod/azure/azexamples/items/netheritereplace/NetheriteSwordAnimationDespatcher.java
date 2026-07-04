package mod.azure.azexamples.items.netheritereplace;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.ItemStack;

public class NetheriteSwordAnimationDespatcher {

    // private static final AzCommand OPENING_COMMAND = AzCommand.create(
    // CommonStrings.BASE_CONTROLLER,
    // "opening",
    // AzPlayBehaviors.PLAY_ONCE
    // );
    //
    // private static final AzCommand CLOSED_COMMAND = AzCommand.create(
    // CommonStrings.BASE_CONTROLLER,
    // "closed",
    // AzPlayBehaviors.HOLD_ON_LAST_FRAME
    // );

    public void serverOpening(Entity entity, ItemStack itemStack) {
        // OPENING_COMMAND.sendForItem(entity, itemStack);
    }

    public void serverClosed(Entity entity, ItemStack itemStack) {
        // CLOSED_COMMAND.sendForItem(entity, itemStack);
    }
}
