package mod.azure.azexamples.entities.creeper;

import mod.azure.azurelib.rewrite.animation.dispatch.command.AzCommand;
import mod.azure.azurelib.rewrite.animation.play_behavior.AzPlayBehaviors;
import net.minecraft.world.entity.monster.Creeper;

import mod.azure.azexamples.CommonStrings;

public class CreeperAnimationDispatcher {

    private final AzCommand IDLE_COMMAND = AzCommand.create(
        CommonStrings.BASE_CONTROLLER,
        CommonStrings.IDLE_ANIMATION_NAME,
        AzPlayBehaviors.LOOP
    );

    private final Creeper creeper;

    public CreeperAnimationDispatcher(Creeper creeper) {
        this.creeper = creeper;
    }

    public void clientIdle() {
        IDLE_COMMAND.sendForEntity(creeper);
    }
}
