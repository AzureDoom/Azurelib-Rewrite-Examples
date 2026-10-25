package mod.azure.azexamples.entities.marauder.ai;

import java.util.List;

/**
 * Timing for one melee clip. Windows and tracking are in ANIMATION ticks (frame = seconds * 20). poseDelayTicks is how
 * many server ticks after the swing starts the clip's frame 0 shows up in the server pose (transition blend + sync).
 * Measure it with the debug log; set it to 0 if you remove the transition.
 */
public record MeleeSwingProfile(
    int animDurationTicks,
    int poseDelayTicks,
    int trackUntilAnimTick,
    float maxTurnPerTick,
    double startReach,
    int cooldownTicks,
    List<HitWindow> windows
) implements TimedAttack {

    public record HitWindow(
        int startTick,
        int endTick
    ) {

        public boolean contains(int tick) {
            return tick >= startTick && tick <= endTick;
        }
    }

    public MeleeSwingProfile {
        windows = List.copyOf(windows);
        if (windows.isEmpty()) {
            throw new IllegalArgumentException("A melee profile needs at least one hit window");
        }
    }

    @Override
    public int animTick(int serverTick) {
        return serverTick - poseDelayTicks;
    }

    @Override
    public boolean isHitTick(int serverTick) {
        var frame = animTick(serverTick);
        for (var window : windows) {
            if (window.contains(frame)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public int lastHitTick() {
        return windows.stream().mapToInt(HitWindow::endTick).max().orElseThrow() + poseDelayTicks;
    }

    @Override
    public int trackUntilTick() {
        return trackUntilAnimTick + poseDelayTicks;
    }

    @Override
    public int durationTicks() {
        return animDurationTicks + poseDelayTicks;
    }

    /** axe_cut: overhead chop, lands dead ahead on frame 12, holds to 16. */
    public static final MeleeSwingProfile AXE_CUT = new MeleeSwingProfile(
        25,
        7,
        9,
        12f,
        3.0,
        40,
        List.of(new HitWindow(12, 16))
    );

    /** axe_attack: 360 spin. Shallow pass frames 5-6, main front pass frames 12-14. */
    public static final MeleeSwingProfile AXE_SPIN = new MeleeSwingProfile(
        25,
        7,
        4,
        12f,
        2.5,
        40,
        List.of(new HitWindow(5, 6), new HitWindow(12, 14))
    );
}
