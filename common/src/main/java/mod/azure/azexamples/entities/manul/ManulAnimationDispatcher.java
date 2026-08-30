package mod.azure.azexamples.entities.manul;

import mod.azure.azurelib.animation.dispatch.command.AzCommand;
import mod.azure.azurelib.animation.play_behavior.AzPlayBehaviors;

import mod.azure.azexamples.CommonStrings;

public class ManulAnimationDispatcher extends ManulAnimator {

    private final AzCommand IDLE_COMMAND = AzCommand.create(
        CommonStrings.BASE_CONTROLLER,
        "Idle",
        AzPlayBehaviors.LOOP
    );

    //
    private final AzCommand IDLE_SNIFF_COMMAND = AzCommand.create(
        CommonStrings.BASE_CONTROLLER,
        "Idle_sniff",
        AzPlayBehaviors.LOOP
    );

    //
    private final AzCommand WALK_COMMAND = AzCommand.create(
        CommonStrings.BASE_CONTROLLER,
        "Walk",
        AzPlayBehaviors.LOOP
    );

    //
    private final AzCommand WALK_SNIFF_COMMAND = AzCommand.create(
        CommonStrings.BASE_CONTROLLER,
        "Walk_Sniff",
        AzPlayBehaviors.LOOP
    );

    //
    private final AzCommand WALK_LOOK_RIGHT_COMMAND = AzCommand.create(
        CommonStrings.BASE_CONTROLLER,
        "Walk_Look_Right",
        AzPlayBehaviors.LOOP
    );

    //
    private final AzCommand WALK_LOOK_LEFT_COMMAND = AzCommand.create(
        CommonStrings.BASE_CONTROLLER,
        "Walk_Look_Left",
        AzPlayBehaviors.LOOP
    );

    //
    private final AzCommand WALK_BOUNCE_COMMAND = AzCommand.create(
        CommonStrings.BASE_CONTROLLER,
        "Walk_Bounce",
        AzPlayBehaviors.LOOP
    );

    private final ManulEntity manulEntity;

    public ManulAnimationDispatcher(ManulEntity manulEntity) {
        this.manulEntity = manulEntity;
    }

    public void mainIdle() {
        IDLE_COMMAND.sendForEntity(manulEntity);
    }

    public void sniffIdle() {
        IDLE_SNIFF_COMMAND.sendForEntity(manulEntity);
    }

    public void mainWalk() {
        WALK_COMMAND.sendForEntity(manulEntity);
    }

    public void sniffWalk() {
        WALK_SNIFF_COMMAND.sendForEntity(manulEntity);
    }

    public void lookRightWalk() {
        WALK_LOOK_RIGHT_COMMAND.sendForEntity(manulEntity);
    }

    public void lookLeftWalk() {
        WALK_LOOK_LEFT_COMMAND.sendForEntity(manulEntity);
    }

    public void bounceWalk() {
        WALK_BOUNCE_COMMAND.sendForEntity(manulEntity);
    }
}
