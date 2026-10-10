package mod.azure.azexamples.entities.manul;

import mod.azure.azurelib.animation.dispatch.command.AzCommand;
import mod.azure.azurelib.animation.play_behavior.AzWeightedPoolBehavior;

import mod.azure.azexamples.CommonStrings;

public class ManulAnimationDispatcher extends ManulAnimator {

    /**
     * Picks a new idle each time the current one finishes, using the real animation length. 75% / 25%, same odds as the
     * old timer version.
     */
    public static final AzWeightedPoolBehavior IDLE_POOL = AzWeightedPoolBehavior.builder("azexamples:manul_idle")
        .add("Idle", 3)
        .add("Idle_sniff", 1)
        .build();

    /**
     * Picks a new walk variant each time the current one finishes. Equal weights, 20% each.
     */
    public static final AzWeightedPoolBehavior WALK_POOL = AzWeightedPoolBehavior.builder("azexamples:manul_walk")
        .add("Walk", 1)
        .addNoRepeat("Walk_Sniff", 1)
        .add("Walk_Look_Right", 1)
        .add("Walk_Look_Left", 1)
        .add("Walk_Bounce", 1)
        .build();

    private final ManulEntity manulEntity;

    private AzCommand currentCommand;

    private boolean currentlyWalking;

    public ManulAnimationDispatcher(ManulEntity manulEntity) {
        this.manulEntity = manulEntity;
    }

    /**
     * Call from common mod init so both pools are registered on both sides before any animation packet arrives. The
     * client looks pools up by name; an unregistered name silently falls back to play_once.
     */
    public static void init() {}

    /**
     * Safe to call every tick. A new random start is only picked when the state changes; otherwise the same command is
     * re-sent, which the controller ignores because the sequence is equal to the one already playing.
     */
    public void play(boolean walking) {
        if (currentCommand == null || walking != currentlyWalking) {
            AzWeightedPoolBehavior pool = walking ? WALK_POOL : IDLE_POOL;
            this.currentCommand = pool.randomSequence().toCommand(CommonStrings.BASE_CONTROLLER);
            this.currentlyWalking = walking;
        }

        currentCommand.sendForEntity(manulEntity);
    }
}
