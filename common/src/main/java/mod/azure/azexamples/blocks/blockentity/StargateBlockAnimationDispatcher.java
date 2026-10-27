package mod.azure.azexamples.blocks.blockentity;

import mod.azure.azurelib.common.animation.dispatch.command.AzCommand;
import mod.azure.azurelib.common.animation.dispatch.command.sequence.AzSequence;
import mod.azure.azurelib.common.animation.play_behavior.AzPlayBehaviors;

import mod.azure.azexamples.CommonStrings;

/**
 * The StargateBlockAnimationDispatcher class is responsible for managing and triggering animation commands for block
 * entities, specifically for the Stargate block entity.
 * <p>
 * Each {@link StargateBlockEntity.Mode} maps to one command, and together they cover AzureLib's playback features:
 * <ul>
 * <li><b>Sequences</b>: {@link #DIAL} chains ring spins, chevron locks and the kawoosh into one value.</li>
 * <li><b>Per-stage reverse</b>: {@link #DIAL} plays {@code ring_spin} backwards on alternate chevrons, so the inner
 * ring changes direction like the real gate.</li>
 * <li><b>Ping-pong</b>: {@link #DIAL} ends on {@code portal_ripple} played forward then backward forever.</li>
 * <li><b>Controller reverse</b>: {@link #SHUTDOWN} flips the whole controller, so the kawoosh plays backwards and the
 * event horizon collapses.</li>
 * <li><b>Freezing</b>: {@link #JAM} plays {@code ring_spin} and freezes it partway through, at a tick set in code.</li>
 * <li><b>Stage settings</b>: {@link #DIAL} plays its last chevron lock at double speed with a longer blend into the
 * kawoosh. Those values belong to their stages only, so nothing has to reset them afterward.</li>
 * </ul>
 * The commands are sent from the client every tick. Sending a command or sequence that is already playing does nothing,
 * so this never restarts anything, and a client that loads the gate mid-mode still catches up.
 */
public class StargateBlockAnimationDispatcher {

    private static final String CONTROLLER = CommonStrings.BASE_CONTROLLER;

    /** Matches the base controller's transition length, since the full {@code create} overload sets it too. */
    private static final float TRANSITION_TICKS = StargateBlockEntityAnimator.TRANSITION_TICKS;

    /** Tick at which {@code ring_spin} freezes in {@link #JAM}, a little under halfway through its 40 ticks. */
    private static final float JAM_FREEZE_TICK = 18F;

    /**
     * The dial: three chevrons, alternating ring direction, then the kawoosh, then a rippling event horizon. Every
     * stage but the last plays once and moves on; the ping-pong never finishes, so it has to be last.
     */
    private static final AzSequence DIAL_SEQUENCE = AzSequence.builder()
        .play("ring_spin")
        .play("chevron_lock")
        .then("ring_spin", AzPlayBehaviors.PLAY_ONCE, props -> props.withShouldReverse(true))
        .play("chevron_lock")
        .play("ring_spin")
        .then("chevron_lock", AzPlayBehaviors.PLAY_ONCE, props -> props.withAnimationSpeed(2))
        .then("kawoosh", AzPlayBehaviors.PLAY_ONCE, props -> props.withTransitionLength(10))
        .then("portal_ripple", AzPlayBehaviors.PING_PONG)
        .build();

    /** The kawoosh again, ending on the closed idle pose once it has finished collapsing. */
    private static final AzSequence SHUTDOWN_SEQUENCE = AzSequence.builder()
        .play("kawoosh")
        .loop(CommonStrings.IDLE_ANIMATION_NAME)
        .build();

    /** Closed gate. Also clears the controller-level reverse that {@link #SHUTDOWN} leaves behind. */
    private static final AzCommand IDLE = AzCommand.controllerBuilder()
        .setReverseAnimation(CONTROLLER, false)
        .play(CONTROLLER, CommonStrings.IDLE_ANIMATION_NAME)
        .build();

    /** Clears any leftover controller reverse first, so the per-stage reverse is the only one in play. */
    private static final AzCommand DIAL = AzCommand.compose(
        AzCommand.controllerBuilder().setReverseAnimation(CONTROLLER, false).build(),
        DIAL_SEQUENCE.toCommand(CONTROLLER)
    );

    /**
     * Reverses the whole controller. Unlike the per-stage reverse in {@link #DIAL}, this direction sticks to the
     * controller until something sets it back, which {@link #JAM} and {@link #IDLE} both do.
     */
    private static final AzCommand SHUTDOWN = AzCommand.compose(
        AzCommand.controllerBuilder().setReverseAnimation(CONTROLLER, true).build(),
        SHUTDOWN_SEQUENCE.toCommand(CONTROLLER)
    );

    /**
     * The full {@code create} overload sets every playback property on the controller at once: here the freeze point,
     * plus forward direction to undo {@link #SHUTDOWN}'s reverse. These stay on the controller afterward, but a freeze
     * point only ever applies to freeze_on_frame animations, so it doesn't stall the loops, ping-pong and sequence
     * stages the other modes play. (To keep the freeze point off the controller entirely, use a sequence stage instead:
     * {@code AzSequence.builder().freeze("ring_spin", JAM_FREEZE_TICK).build().toCommand(CONTROLLER)}.)
     */
    private static final AzCommand JAM = AzCommand.create(
        CONTROLLER,
        "ring_spin",
        AzPlayBehaviors.FREEZE_ON_FRAME,
        0F, // start tick offset
        1F, // animation speed
        TRANSITION_TICKS, // transition length
        JAM_FREEZE_TICK, // freeze tick offset
        1F, // repeat count (unused here)
        false // forward
    );

    private final StargateBlockEntity stargateBlockEntity;

    public StargateBlockAnimationDispatcher(StargateBlockEntity stargateBlockEntity) {
        this.stargateBlockEntity = stargateBlockEntity;
    }

    public void clientPlay(StargateBlockEntity.Mode mode) {
        var command = switch (mode) {
            case IDLE -> IDLE;
            case DIAL -> DIAL;
            case SHUTDOWN -> SHUTDOWN;
            case JAM -> JAM;
        };

        command.sendForBlockEntity(stargateBlockEntity);
    }
}
