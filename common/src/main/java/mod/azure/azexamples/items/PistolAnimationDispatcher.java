package mod.azure.azexamples.items;

import mod.azure.azexamples.CommonStrings;
import mod.azure.azurelib.core2.animation.dispatch.command.AzCommand;
import mod.azure.azurelib.core2.animation.primitive.AzLoopType;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.ItemStack;

public class PistolAnimationDispatcher {

    private static final AzCommand FIRING_COMMAND = AzCommand.create(
            CommonStrings.BASE_CONTROLLER,
            CommonStrings.FIRING_ANIMATION_NAME,
            AzLoopType.PLAY_ONCE
    );

    public void serverFire(Entity entity, ItemStack itemStack) {
        FIRING_COMMAND.sendForItem(entity, itemStack);
    }
}
