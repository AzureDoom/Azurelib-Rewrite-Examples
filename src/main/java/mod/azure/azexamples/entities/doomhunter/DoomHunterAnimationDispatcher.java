package mod.azure.azexamples.entities.doomhunter;

import mod.azure.azurelib.animation.dispatch.command.AzCommand;
import mod.azure.azurelib.animation.play_behavior.AzPlayBehaviors;

import mod.azure.azexamples.CommonStrings;

/**
 * The flamethrower runs through four phases:
 * <ol>
 * <li><b>Idle</b>: the base controller loops the idle; the attack layer sits at weight {@code 0}.</li>
 * <li><b>Fade-in</b>: {@link #clientFlamethrowerFadeIn()} starts the flamethrower loop (a no-op if it is already
 * playing) and fades the attack layer up to {@link DoomHunterAnimator#FLAMETHROWER_WEIGHT}.</li>
 * <li><b>Active firing</b>: the weight holds at its peak, since a weight stays put until it is changed again.</li>
 * <li><b>Fade-out</b>: {@link #clientFlamethrowerFadeOut()} fades the attack layer back to {@code 0}.</li>
 * </ol>
 * None of these commands touch the base controller, so the idle never restarts. Usage of this class is tied to the
 * entity lifecycle, as demonstrated in the {@code DoomHunterEntity} class.
 */
public class DoomHunterAnimationDispatcher {

    /** Ticks for the attack layer to reach full strength once firing starts. */
    public static final float FADE_IN_TICKS = 10F;

    /** Ticks for the attack layer to die away once firing stops. Longer than the fade-in so the flames trail off. */
    public static final float FADE_OUT_TICKS = 20F;

    private final AzCommand idleCommand = AzCommand
        .create(CommonStrings.BASE_CONTROLLER, CommonStrings.IDLE_ANIMATION_NAME, AzPlayBehaviors.LOOP);

    private final AzCommand flamethrowerCommand = AzCommand
        .create(CommonStrings.ATTACK_CONTROLLER, "flamethrower", AzPlayBehaviors.LOOP);

    /** Starts the flamethrower loop and eases the additive layer in. */
    private final AzCommand flamethrowerFadeInCommand = AzCommand.compose(
        flamethrowerCommand,
        AzCommand.controllerBuilder()
            .fadeWeight(CommonStrings.ATTACK_CONTROLLER, DoomHunterAnimator.FLAMETHROWER_WEIGHT, FADE_IN_TICKS)
            .build()
    );

    /** Eases the additive layer out. The loop keeps running underneath at weight 0, so it never restarts. */
    private final AzCommand flamethrowerFadeOutCommand = AzCommand.controllerBuilder()
        .fadeWeight(CommonStrings.ATTACK_CONTROLLER, 0F, FADE_OUT_TICKS)
        .build();

    /** Snaps straight to the firing state, for when the entity is first seen mid-fire. */
    private final AzCommand flamethrowerActiveCommand = AzCommand.compose(
        flamethrowerCommand,
        AzCommand.controllerBuilder()
            .setWeight(CommonStrings.ATTACK_CONTROLLER, DoomHunterAnimator.FLAMETHROWER_WEIGHT)
            .build()
    );

    /** Snaps straight to the idle state, for when the entity is first seen while not firing. */
    private final AzCommand flamethrowerOffCommand = AzCommand.controllerBuilder()
        .setWeight(CommonStrings.ATTACK_CONTROLLER, 0F)
        .build();

    private final DoomHunterEntity doomHunter;

    public DoomHunterAnimationDispatcher(DoomHunterEntity doomHunter) {
        this.doomHunter = doomHunter;
    }

    public void clientIdle() {
        idleCommand.sendForEntity(doomHunter);
    }

    public void clientFlamethrowerFadeIn() {
        flamethrowerFadeInCommand.sendForEntity(doomHunter);
    }

    public void clientFlamethrowerFadeOut() {
        flamethrowerFadeOutCommand.sendForEntity(doomHunter);
    }

    public void clientFlamethrowerActive() {
        flamethrowerActiveCommand.sendForEntity(doomHunter);
    }

    public void clientFlamethrowerOff() {
        flamethrowerOffCommand.sendForEntity(doomHunter);
    }
}
