package mod.azure.azexamples.entities.creeper;

import mod.azure.azexamples.CommonStrings;
import mod.azure.azurelib.core2.animation.dispatch.command.AzCommand;
import mod.azure.azurelib.core2.animation.primitive.AzLoopType;
import net.minecraft.world.entity.monster.Creeper;

public class CreeperAnimationDispatcher {

    private final AzCommand IDLE_COMMAND = AzCommand.create(CommonStrings.BASE_CONTROLLER,
                    CommonStrings.IDLE_ANIMATION_NAME,
                    AzLoopType.LOOP);

    private final Creeper creeper;

    public CreeperAnimationDispatcher(Creeper creeper) {
        this.creeper = creeper;
    }

    public void clientIdle() {
        IDLE_COMMAND.sendForEntity(creeper);
    }
}
