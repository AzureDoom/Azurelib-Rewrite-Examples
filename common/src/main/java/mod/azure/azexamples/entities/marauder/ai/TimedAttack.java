package mod.azure.azexamples.entities.marauder.ai;

/**
 * Shared timing contract for the Marauder's animation-driven attacks. All "serverTick" values count from the tick the
 * attack started (0); animation frames are serverTick - poseDelayTicks.
 */
public interface TimedAttack {

    int durationTicks();

    int trackUntilTick();

    float maxTurnPerTick();

    int cooldownTicks();

    int animTick(int serverTick);

    boolean isHitTick(int serverTick);

    int lastHitTick();
}
