package mod.azure.azexamples.entities.juravenator;

import mod.azure.azurelib.animation.dispatch.command.AzCommand;
import mod.azure.azurelib.animation.play_behavior.AzPlayBehaviors;

import mod.azure.azexamples.CommonStrings;

public class JuravenatorAnimationDispatcher {

    private final AzCommand IDLE_COMMAND = AzCommand
        .create(CommonStrings.BASE_CONTROLLER, CommonStrings.IDLE_ANIMATION_NAME, AzPlayBehaviors.LOOP);

    //
    public final AzCommand WALK_COMMAND = AzCommand.create(
        CommonStrings.BASE_CONTROLLER,
        CommonStrings.WALK_ANIMATION_NAME,
        AzPlayBehaviors.LOOP
    );

    private final JuravenatorEntity juravenator;

    public JuravenatorAnimationDispatcher(JuravenatorEntity juravenator) {
        this.juravenator = juravenator;
    }

    public void clientIdle() {
        IDLE_COMMAND.sendForEntity(juravenator);
    }

    public void clientWalk() {
        WALK_COMMAND.sendForEntity(juravenator);
    }
}
