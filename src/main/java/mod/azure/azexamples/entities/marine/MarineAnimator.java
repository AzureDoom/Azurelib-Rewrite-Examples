package mod.azure.azexamples.entities.marine;

import net.minecraft.util.ResourceLocation;

import mod.azure.azurelib.animation.AzAnimatorConfig;
import mod.azure.azurelib.animation.controller.AzAnimationController;
import mod.azure.azurelib.animation.controller.AzAnimationControllerContainer;
import mod.azure.azurelib.animation.controller.AzBlendMode;
import mod.azure.azurelib.animation.controller.AzBoneMask;
import mod.azure.azurelib.animation.dispatch.command.AzCommand;
import mod.azure.azurelib.animation.impl.AzEntityAnimator;
import mod.azure.azurelib.animation.play_behavior.AzPlayBehaviors;
import mod.azure.azurelib.core.molang.MolangScope;
import mod.azure.azurelib.util.math.Mth;

import mod.azure.azexamples.CommonMod;
import mod.azure.azexamples.CommonStrings;

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
 * <li><b>Heat</b>: an additive layer whose keyframes are Molang programs. They keep a weapon-heat state machine per
 * Marine in {@code variable.*}: heat builds while aiming, the Marine vents (arms down, head shake) once it maxes out,
 * and holds fire until it has cooled. Code reads {@code v.venting} back to stop the recoil shots, so state flows both
 * ways between code and Molang. See {@link #applyMolangQueries} and the {@code heat} animation.</li>
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

    public static final String HEAT_CONTROLLER = "heat_controller";

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

    /**
     * Always running. All of its motion comes from Molang in the {@code heat} animation, which reads and writes this
     * Marine's own {@code variable.*} values, so two Marines heat up and vent independently:
     * <ul>
     * <li>{@code v.heat}, {@code v.venting}, {@code v.last_time}: state the animation owns. Assigned in the
     * {@code gBody} keyframe, persisted per Marine across frames, and read by the other bones.</li>
     * <li>{@code v.aiming}, {@code v.seed}: inputs set from code in {@link #applyMolangQueries}.</li>
     * <li>{@code t.*}: scratch values that start at 0 every evaluation, like the loop counter in the arm jitter.</li>
     * </ul>
     */
    private static final AzCommand HEAT = AzCommand.create(HEAT_CONTROLLER, "heat", AzPlayBehaviors.LOOP);

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
                .build(),
            // Molang-driven heat sway, venting slump and jitter, added on top of everything else.
            AzAnimationController.builder(this, HEAT_CONTROLLER)
                .setBlendMode(AzBlendMode.ADDITIVE)
                .build()
        );
    }

    @Override
    public ResourceLocation getAnimationLocation(MarineEntity animatable) {
        return ANIMATIONS;
    }

    /**
     * Feeds this Marine's inputs into its own Molang scope before any keyframe is evaluated. Values set here are only
     * visible to this Marine's animations: another Marine reading {@code v.aiming} gets its own value.
     */
    @Override
    protected void applyMolangQueries(MarineEntity animatable, double animTime, float partialTicks) {
        super.applyMolangQueries(animatable, animTime, partialTicks);

        MolangScope scope = context().molangScope();

        // Input that changes every frame.
        scope.set("v.aiming", animatable.getHeldItem() == null ? 0 : 1);

        // Input set once and then left alone: offsets the jitter so a squad doesn't shake in sync.
        if (!scope.isSet("v.seed")) {
            scope.set("v.seed", animatable.getEntityId() % 7);
        }
    }

    @Override
    public void setCustomAnimations(MarineEntity animatable, float partialTicks) {
        super.setCustomAnimations(animatable, partialTicks);

        HEAT.sendForEntity(animatable);

        // Legs: walk or idle, decided independently of aiming.
        boolean walking = Mth.lerp(
            partialTicks,
            animatable.prevLimbSwingAmount,
            animatable.limbSwingAmount
        ) > WALK_THRESHOLD;
        (walking ? WALK : IDLE).sendForEntity(animatable);

        // Arms: aim while holding something. Only the edges send a fade, since a new fade replaces a running one.
        boolean aiming = animatable.getHeldItem() != null;

        if (lastAiming == null) {
            if (aiming) {
                AIM_IN.sendForEntity(animatable);
            }
        } else if (aiming != lastAiming) {
            (aiming ? AIM_IN : AIM_OUT).sendForEntity(animatable);
        }

        lastAiming = aiming;

        // Recoil: one shot per interval while aiming, except while the heat animation has this Marine venting. The
        // venting flag is written by Molang in the heat animation and read back here from this Marine's scope.
        boolean venting = context().molangScope().get("v.venting") != 0;

        if (aiming && !venting && animatable.ticksExisted % FIRE_INTERVAL < FIRE_WINDOW) {
            FIRE.sendForEntity(animatable);
        }
    }
}
