package mod.azure.azexamples.entities.optimusprime.ai;

import net.minecraft.world.entity.EntitySelector;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.player.Player;
import org.jetbrains.annotations.Nullable;

import java.util.EnumSet;

import mod.azure.azexamples.entities.optimusprime.OptimusPrimeEntity;

/**
 * Server side: when a player comes within reach, Optimus Prime turns to face them and plays his sword strike. The goal
 * only starts and stops the attack; the damage itself is dealt by the strike's {@code impact} sequence event (see
 * {@link OptimusPrimeEntity#onStrikeEvent}), so it always lands on the animation's impact frame.
 */
public class OptimusStrikeGoal extends Goal {

    private final OptimusPrimeEntity optimusPrime;

    private @Nullable Player target;

    private int nextStrikeTick;

    public OptimusStrikeGoal(OptimusPrimeEntity optimusPrime) {
        this.optimusPrime = optimusPrime;
        this.setFlags(EnumSet.of(Flag.MOVE, Flag.LOOK));
    }

    @Override
    public boolean canUse() {
        if (optimusPrime.tickCount < nextStrikeTick) {
            return false;
        }

        this.target = optimusPrime.level()
            .getNearestPlayer(
                optimusPrime.getX(),
                optimusPrime.getY(),
                optimusPrime.getZ(),
                8.0D,
                EntitySelector.NO_CREATIVE_OR_SPECTATOR
            );

        return target != null;
    }

    @Override
    public boolean canContinueToUse() {
        return optimusPrime.isStriking();
    }

    @Override
    public boolean requiresUpdateEveryTick() {
        return true;
    }

    @Override
    public void start() {
        optimusPrime.startStrike();
    }

    @Override
    public void tick() {
        if (target != null && target.isAlive()) {
            optimusPrime.getLookControl().setLookAt(target, 30.0F, 30.0F);
        }
    }

    @Override
    public void stop() {
        if (optimusPrime.isStriking()) {
            optimusPrime.interruptStrike();
        }

        this.target = null;
        this.nextStrikeTick = optimusPrime.tickCount + 40;
    }
}
