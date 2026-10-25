package mod.azure.azexamples.entities.marauder.ai;

/**
 * Timing for the energy_slash clip (1.0 s / 20 frames). The wave is the h_slash10 cube, carried forward by the
 * h_slash.h_slash10 chain (each link adds -47 px), so it is only a bone, never an entity.
 * <p>
 * Measured from the animation: frames 0-6 hidden (scale 0), torso/axe wind-up frame 7 wave appears ~2.2 blocks ahead,
 * 0.6-2.6 blocks up, 2 blocks long frames 8-20 wave accelerates (easeInOutQuad) to ~4.3 blocks/tick around frames
 * 14-15, ends ~31 blocks out The wave can travel further in one tick than it is long, so hits are swept between ticks.
 *
 * @param minRange      don't slash closer than this (melee handles it), blocks center to center
 * @param maxRange      don't start a slash beyond this
 * @param maxHeightDiff the wave flies flat along the body yaw, so skip targets far above/below
 */
public record EnergySlashProfile(
    int animDurationTicks,
    int poseDelayTicks,
    int trackUntilAnimTick,
    float maxTurnPerTick,
    double minRange,
    double maxRange,
    double maxHeightDiff,
    int cooldownTicks,
    float damage,
    int firstHitFrame,
    int lastHitFrame
) implements TimedAttack {

    @Override
    public int animTick(int serverTick) {
        return serverTick - poseDelayTicks;
    }

    @Override
    public boolean isHitTick(int serverTick) {
        var frame = animTick(serverTick);
        return frame >= firstHitFrame && frame <= lastHitFrame;
    }

    @Override
    public int lastHitTick() {
        return lastHitFrame + poseDelayTicks;
    }

    @Override
    public int trackUntilTick() {
        return trackUntilAnimTick + poseDelayTicks;
    }

    @Override
    public int durationTicks() {
        return animDurationTicks + poseDelayTicks;
    }

    /** Aim locks 2 frames before the wave appears, so a sidestep during the wind-up dodges it. */
    public static final EnergySlashProfile ENERGY_SLASH = new EnergySlashProfile(
        20,
        7,
        5,
        15f,
        4.0,
        26.0,
        1.5,
        100,
        8.0f,
        7,
        20
    );
}
