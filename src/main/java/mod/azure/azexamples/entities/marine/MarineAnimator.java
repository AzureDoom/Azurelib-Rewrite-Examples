package mod.azure.azexamples.entities.marine;

import mod.azure.azexamples.CommonMod;
import mod.azure.azexamples.CommonStrings;
import mod.azure.azurelib.animation.AzAnimatorConfig;
import mod.azure.azurelib.animation.controller.AzAnimationController;
import mod.azure.azurelib.animation.controller.AzAnimationControllerContainer;
import mod.azure.azurelib.animation.controller.AzBlendMode;
import mod.azure.azurelib.animation.controller.AzBoneMask;
import mod.azure.azurelib.animation.dispatch.command.AzCommand;
import mod.azure.azurelib.animation.impl.AzEntityAnimator;
import mod.azure.azurelib.animation.play_behavior.AzPlayBehaviors;
import mod.azure.azurelib.util.math.Mth;
import net.minecraft.util.ResourceLocation;

/**
 * Animator for the Marine, showing a controller that only affects a chosen bone hierarchy.
 * <p>
 * Three controllers run independently:
 * <ul>
 * <li><b>Base</b>: whole body, plays {@code walk} or {@code idle}. The walk swings the arms as well as the legs.</li>
 * <li><b>Aim</b>: masked with {@link AzBoneMask#only(String...)} to the two arm hierarchies. When faded in it takes
 * over the arms (and everything parented to them: the held-item bones and arm armor) while the legs keep walking,
 * because bones outside the mask are never evaluated or written by this controller.</li>
 * <li><b>Fire</b>: an additive recoil kick played once per shot on top of whatever pose the arms are in.</li>
 * </ul>
 * All of the state selection lives in {@link #setCustomAnimations}. It runs on the client every frame the Marine is
 * rendered, after the controllers have been applied, so commands sent here take effect from the next frame. Re-sending
 * an animation that is already playing does nothing, so only the aim fades need edge tracking.
 */
public class MarineAnimator extends AzEntityAnimator<MarineEntity> {

    private static final ResourceLocation ANIMATIONS = CommonMod.modResource(
        "animations/entity/marine.animation.json"
    );

    public static final String AIM_CONTROLLER = "aim_controller";

    public static final String FIRE_CONTROLLER = "fire_controller";

    /** The bone hierarchies the aim controller is allowed to touch. Children (hands, arm armor) are included. */
    private static final AzBoneMask ARMS = AzBoneMask.only("gRightArm", "gLeftArm");

    /** Horizontal walk speed above which the base controller switches from idle to walk. */
    private static final float WALK_THRESHOLD = 0.1F;

    private static final float AIM_FADE_IN_TICKS = 6F;

    private static final float AIM_FADE_OUT_TICKS = 8F;

    /** Ticks between shots while aiming. */
    private static final int FIRE_INTERVAL = 20;

    /**
     * Ticks at the start of each interval during which the shot is sent. Shorter than the fire animation, so repeated
     * sends inside the window are no-ops and each interval fires exactly once.
     */
    private static final int FIRE_WINDOW = 2;

    private static final AzCommand IDLE = AzCommand.create(
        CommonStrings.BASE_CONTROLLER,
        CommonStrings.IDLE_ANIMATION_NAME,
        AzPlayBehaviors.LOOP
    );

    private static final AzCommand WALK = AzCommand.create(CommonStrings.BASE_CONTROLLER, "walk", AzPlayBehaviors.LOOP);

    /** Starts the aim loop (a no-op once running) and eases the masked layer in over the arms. */
    private static final AzCommand AIM_IN = AzCommand.compose(
        AzCommand.create(AIM_CONTROLLER, "aim", AzPlayBehaviors.LOOP),
        AzCommand.controllerBuilder().fadeWeight(AIM_CONTROLLER, 1F, AIM_FADE_IN_TICKS).build()
    );

    /** Hands the arms back to the base controller, so the walk's arm swing returns. */
    private static final AzCommand AIM_OUT = AzCommand.controllerBuilder()
        .fadeWeight(AIM_CONTROLLER, 0F, AIM_FADE_OUT_TICKS)
        .build();

    private static final AzCommand FIRE = AzCommand.create(FIRE_CONTROLLER, "fire", AzPlayBehaviors.PLAY_ONCE);

    /** Aim state seen on the previous frame, or {@code null} before the first frame. */
    private Boolean lastAiming;

    public MarineAnimator() {
        super(AzAnimatorConfig.defaultConfig());
    }

    @Override
    public void registerControllers(AzAnimationControllerContainer<MarineEntity> animationControllerContainer) {
        animationControllerContainer.add(
            // Whole body: walk or idle.
            AzAnimationController.builder(this, CommonStrings.BASE_CONTROLLER).setTransitionLength(5).build(),
            // Arms only: OVERRIDE so the aim pose replaces the walk's arm swing, legs untouched.
            AzAnimationController.builder(this, AIM_CONTROLLER)
                .setBoneMask(ARMS)
                .setWeight(0)
                .build(),
            // Recoil added on top of whichever arm pose is active.
            AzAnimationController.builder(this, FIRE_CONTROLLER)
                .setBlendMode(AzBlendMode.ADDITIVE)
                .build()
        );
    }

    @Override
    public ResourceLocation getAnimationLocation(MarineEntity animatable) {
        return ANIMATIONS;
    }

    @Override
    public void setCustomAnimations(MarineEntity animatable, float partialTicks) {
        super.setCustomAnimations(animatable, partialTicks);

        // Legs: walk or idle, decided independently of aiming.
        boolean walking = Mth.lerp(
                partialTicks,
                animatable.prevLimbSwingAmount,
                animatable.limbSwingAmount
        ) > WALK_THRESHOLD;
        (walking ? WALK : IDLE).sendForEntity(animatable);

        // Arms: aim while holding something. Only the edges send a fade, since a new fade replaces a running one.
        boolean aiming = !animatable.getHeldItemMainhand().isEmpty();

        if (lastAiming == null) {
            if (aiming) {
                AIM_IN.sendForEntity(animatable);
            }
        } else if (aiming != lastAiming) {
            (aiming ? AIM_IN : AIM_OUT).sendForEntity(animatable);
        }

        lastAiming = aiming;

        // Recoil: one shot per interval while aiming.
        if (aiming && animatable.ticksExisted % FIRE_INTERVAL < FIRE_WINDOW) {
            FIRE.sendForEntity(animatable);
        }
    }
}
