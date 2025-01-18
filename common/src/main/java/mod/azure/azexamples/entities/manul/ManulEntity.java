package mod.azure.azexamples.entities.manul;

import mod.azure.azurelib.rewrite.util.MoveAnalysis;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.goal.RandomStrollGoal;
import net.minecraft.world.level.Level;

/**
 * Credit to JayZX535 for request of this example.
 */
public class ManulEntity extends PathfinderMob {

    private final ManulAnimationDispatcher animationDispatcher;

    private final MoveAnalysis moveAnalysis;

    private int animationIdleTickCounter = 0;

    private int animationWalkTickCounter = 0;

    private boolean firstSpawn = true;

    private boolean firstWalk = true;

    public ManulEntity(EntityType<? extends PathfinderMob> entityType, Level level) {
        super(entityType, level);
        this.animationDispatcher = new ManulAnimationDispatcher(this);
        this.moveAnalysis = new MoveAnalysis(this);
    }

    @Override
    public void tick() {
        super.tick();
        moveAnalysis.update();
        var isMovingOnGround = moveAnalysis.isMovingHorizontally() && onGround();

        if (this.level().isClientSide) {
            // Increment the tick counter
            if (!isMovingOnGround) {
                animationIdleTickCounter++;
            }
            // This is needed so it plays the idle animation once when first spawned since timer would be 0
            if (this.firstSpawn && !isMovingOnGround) {
                this.firstSpawn = false;
                animationDispatcher.mainIdle();
            }

            if (isMovingOnGround) {
                // Increment the tick counter
                animationWalkTickCounter++;
                if (this.firstWalk) {
                    this.firstWalk = false;
                    animationDispatcher.mainWalk();
                }
                if (!this.firstSpawn) {
                    this.firstSpawn = true;
                }
                if (animationWalkTickCounter >= 144) {
                    var randomValue = Math.random(); // Generate a random value between 0 and 1

                    if (randomValue < 0.2) {
                        animationDispatcher.mainWalk(); // 20% chance
                    } else if (randomValue < 0.4) {
                        animationDispatcher.sniffWalk(); // 20% chance
                    } else if (randomValue < 0.6) {
                        animationDispatcher.lookRightWalk(); // 20% chance
                    } else if (randomValue < 0.8) {
                        animationDispatcher.lookLeftWalk(); // 20% chance
                    } else {
                        animationDispatcher.bounceWalk(); // 20% chance
                    }
                    animationWalkTickCounter = 0; // Reset the counter
                }
            }
            // Execute the animation logic only every 7.2 seconds (144 ticks)
            if (!isMovingOnGround && animationIdleTickCounter >= 144) {
                animationIdleTickCounter = 0; // Reset the counter

                if (!this.firstWalk) {
                    this.firstWalk = true;
                }
                if (Math.random() < 0.75) {
                    // 75% chance to play mainIdle
                    animationDispatcher.mainIdle();
                } else {
                    // 25% chance to play sniffIdle
                    animationDispatcher.sniffIdle();
                }
            }
        }
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(7, new RandomStrollGoal(this, 0.3F));
    }
}
