package mod.azure.azexamples.entities.manul;

import mod.azure.azurelib.util.MoveAnalysis;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.goal.RandomStrollGoal;
import net.minecraft.world.level.Level;

/**
 * Credit to JayZX535 for request of this example.
 */
public class ManulEntity extends PathfinderMob {

    protected final ManulAnimationDispatcher animationDispatcher;

    private static final int MAX_ANIMATION_TICKS = 144;

    private final MoveAnalysis moveAnalysis;

    protected int animationTickCounter = 0;

    public ManulEntity(EntityType<? extends PathfinderMob> entityType, Level level) {
        super(entityType, level);
        this.animationDispatcher = new ManulAnimationDispatcher(this);
        this.moveAnalysis = new MoveAnalysis(this);
    }

    @Override
    public void tick() {
        super.tick();
        this.moveAnalysis.update();
    }

    public void updateAnimations() {
        animationTickCounter++;

        if (animationTickCounter >= MAX_ANIMATION_TICKS) {
            handleAnimations();
            animationTickCounter = 0;
        }

        if (tickCount < 2) {
            handleIdleAnimations();
        }
    }

    protected void handleAnimations() {
        if (this.moveAnalysis.isMoving()) {
            this.handleMovementAnimations();
        } else {
            this.handleIdleAnimations();
        }
    }

    protected void handleMovementAnimations() {
        var randomValue = Math.random();

        if (randomValue < 0.2) {
            animationDispatcher.mainWalk();
        } else if (randomValue < 0.4) {
            animationDispatcher.sniffWalk();
        } else if (randomValue < 0.6) {
            animationDispatcher.lookRightWalk();
        } else if (randomValue < 0.8) {
            animationDispatcher.lookLeftWalk();
        } else {
            animationDispatcher.bounceWalk();
        }
        this.animationTickCounter = 0;
    }

    protected void handleIdleAnimations() {
        if (Math.random() < 0.75) {
            animationDispatcher.mainIdle();
        } else {
            animationDispatcher.sniffIdle();
        }
        this.animationTickCounter = 0;
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(7, new RandomStrollGoal(this, 0.3F));
    }
}
