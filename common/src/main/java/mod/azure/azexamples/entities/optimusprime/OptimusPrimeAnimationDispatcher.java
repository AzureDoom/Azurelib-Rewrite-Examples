package mod.azure.azexamples.entities.optimusprime;

import mod.azure.azurelib.common.animation.dispatch.command.AzCommand;
import mod.azure.azurelib.common.animation.dispatch.command.sequence.AzSequence;
import mod.azure.azurelib.common.animation.dispatch.command.sequence.AzSequenceTiming;
import mod.azure.azurelib.common.animation.play_behavior.AzPlayBehaviors;

import mod.azure.azexamples.CommonStrings;

/**
 * Credit to <a href="https://github.com/MARCTR0N">Marctron</a> for the model/textures/animations of this example.
 * <p>
 * Besides the idle loop, this holds Optimus Prime's sword strike: a server-timed {@link AzSequence} whose events are
 * anchored to the animation itself, so they stay on the right frame when the attack is sped up.
 */
public class OptimusPrimeAnimationDispatcher {

    /** Fired when the swing itself starts, once the windup has finished and blended into it. */
    public static final String SWING_EVENT = "swing";

    /** Fired the moment the blade crosses his center line: where the damage lands. */
    public static final String IMPACT_EVENT = "impact";

    /** Fired when the swing animation finishes; the attack is over from here. */
    public static final String RECOVERED_EVENT = "recovered";

    /** Speed multiplier used once he drops below half health. */
    public static final double ENRAGED_SPEED = 1.5;

    /**
     * The strike at normal speed. {@code melee_op_1} raises the sword and {@code melee_op_2} swings it: the second
     * starts from exactly the pose the first ends on, so they're played back to back.
     */
    public static final AzSequence STRIKE = strike(1);

    /** The same strike, everything played {@link #ENRAGED_SPEED} times faster. */
    public static final AzSequence STRIKE_ENRAGED = strike(ENRAGED_SPEED);

    /**
     * What the server needs to know to place the anchored events: the base controller's transition length (from
     * {@link OptimusPrimeAnimator}) and the lengths of the animations the events depend on, in ticks (seconds x 20, as
     * in Blockbench). The server can't read these from the animation file, which only exists on the client.
     */
    public static final AzSequenceTiming STRIKE_TIMING = AzSequenceTiming.builder()
        .transitionLength(OptimusPrimeAnimator.BASE_TRANSITION_TICKS)
        .length("melee_op_1", 180) // 9 s
        .length("melee_op_2", 140) // 7 s
        .build();

    private final AzCommand idleCommand = AzCommand
        .create(CommonStrings.BASE_CONTROLLER, "walkcycle", AzPlayBehaviors.LOOP);

    private final OptimusPrimeEntity optimusPrimeEntity;

    public OptimusPrimeAnimationDispatcher(OptimusPrimeEntity optimusPrimeEntity) {
        this.optimusPrimeEntity = optimusPrimeEntity;
    }

    /**
     * The strike at {@code speed} times its base pace. Both stages set their own speed, so the authored 9 s windup and
     * 7 s swing play as roughly 3 s and 3.5 s here, and only for this attack: the controller's speed is untouched.
     * <p>
     * None of the event times below are game ticks. They're anchored to the stages, so the player works out when each
     * one happens from the speeds, the transitions and {@link #STRIKE_TIMING}:
     * <ul>
     * <li>normal: swing on tick 65, impact on tick 95, recovered on tick 135</li>
     * <li>enraged: swing on tick 43, impact on tick 63, recovered on tick 90</li>
     * </ul>
     */
    private static AzSequence strike(double speed) {
        return AzSequence.builder()
            .then("melee_op_1", AzPlayBehaviors.PLAY_ONCE, props -> props.withAnimationSpeed(3 * speed))
            // The swing starts on the windup's last pose, so a short blend is enough; this stage only.
            .then(
                "melee_op_2",
                AzPlayBehaviors.PLAY_ONCE,
                props -> props.withAnimationSpeed(2 * speed).withTransitionLength(2)
            )
            .eventOnStart(SWING_EVENT)
            // Keyframe tick 60 (3 s) of melee_op_2: the chest swings from -30 to +30 degrees and crosses 0 here.
            .eventAt(IMPACT_EVENT, 60)
            .eventOnEnd(RECOVERED_EVENT)
            .loop("walkcycle")
            .build();
    }

    public void clientIdle() {
        idleCommand.sendForEntity(optimusPrimeEntity);
    }

    /**
     * Puts the idle loop back from the server, e.g. after an interrupted strike.
     */
    public void serverIdle() {
        idleCommand.sendForEntity(optimusPrimeEntity);
    }
}
