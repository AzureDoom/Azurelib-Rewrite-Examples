package mod.azure.azexamples.items;

import mod.azure.azurelib.core2.animation.dispatch.AzDispatcher;
import mod.azure.azurelib.core2.animation.dispatch.command.AzDispatchCommand;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.ItemStack;

public class PistolAnimationDispatcher {

    private static final String FIRING_ANIMATION_NAME = "firing";

    private static final AzDispatchCommand FIRING_COMMAND = AzDispatchCommand.builder()
        .playAnimation("base_controller", FIRING_ANIMATION_NAME)
        .build();

    public void serverFire(Entity entity, ItemStack itemStack) {
        AzDispatcher.fromServer(FIRING_COMMAND).sendForItem(entity, itemStack);
    }
}
