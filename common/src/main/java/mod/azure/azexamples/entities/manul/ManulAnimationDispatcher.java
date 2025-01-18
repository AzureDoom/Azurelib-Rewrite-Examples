package mod.azure.azexamples.entities.manul;

import mod.azure.azexamples.CommonStrings;
import mod.azure.azurelib.rewrite.animation.dispatch.command.AzCommand;
import mod.azure.azurelib.rewrite.animation.play_behavior.AzPlayBehaviors;

public class ManulAnimationDispatcher {

    private final AzCommand IDLE_COMMAND = AzCommand.builder().cancelAll().playSequence(
                    CommonStrings.BASE_CONTROLLER,
                    sequenceBuilder ->
                            sequenceBuilder.queue("Idle", props -> props.withPlayBehavior(AzPlayBehaviors.LOOP))
            )
            .build();

    private final AzCommand IDLE_SNIFF_COMMAND = AzCommand.builder().cancelAll().playSequence(
                    CommonStrings.BASE_CONTROLLER,
                    sequenceBuilder ->
                            sequenceBuilder.queue("Idle_sniff", props -> props.withPlayBehavior(AzPlayBehaviors.LOOP))
            )
            .build();

    private final AzCommand WALK_COMMAND = AzCommand.builder().cancelAll().playSequence(
                    CommonStrings.BASE_CONTROLLER,
                    sequenceBuilder ->
                            sequenceBuilder.queue("Walk", props -> props.withPlayBehavior(AzPlayBehaviors.LOOP))
            )
            .build();

    private final AzCommand WALK_SNIFF_COMMAND = AzCommand.builder().cancelAll().playSequence(
                    CommonStrings.BASE_CONTROLLER,
                    sequenceBuilder ->
                            sequenceBuilder.queue("Walk_Sniff", props -> props.withPlayBehavior(AzPlayBehaviors.LOOP))
            )
            .build();

    private final AzCommand WALK_LOOK_RIGHT_COMMAND = AzCommand.builder().cancelAll().playSequence(
                    CommonStrings.BASE_CONTROLLER,
                    sequenceBuilder ->
                            sequenceBuilder.queue("Walk_Look_Right", props -> props.withPlayBehavior(AzPlayBehaviors.LOOP))
            )
            .build();

    private final AzCommand WALK_LOOK_LEFT_COMMAND = AzCommand.builder().cancelAll().playSequence(
                    CommonStrings.BASE_CONTROLLER,
                    sequenceBuilder ->
                            sequenceBuilder.queue("Walk_Look_Left", props -> props.withPlayBehavior(AzPlayBehaviors.LOOP))
            )
            .build();

    private final AzCommand WALK_BOUNCE_COMMAND = AzCommand.builder().cancelAll().playSequence(
                    CommonStrings.BASE_CONTROLLER,
                    sequenceBuilder ->
                            sequenceBuilder.queue("Walk_Bounce", props -> props.withPlayBehavior(AzPlayBehaviors.LOOP))
            )
            .build();

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
