package mod.azure.azexamples.entities.juravenator;

import mod.azure.azurelib.rewrite.util.MoveAnalysis;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.goal.RandomStrollGoal;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.level.Level;

public class JuravenatorEntity extends Monster {

    private final MoveAnalysis moveAnalysis;

    protected final JuravenatorAnimationDispatcher animationDispatcher;

    public JuravenatorEntity(EntityType<? extends Monster> entityType, Level level) {
        super(entityType, level);
        this.moveAnalysis = new MoveAnalysis(this);
        this.animationDispatcher = new JuravenatorAnimationDispatcher(this);
    }

    @Override
    public void tick() {
        super.tick();
        moveAnalysis.update();
    }

    public void updateAnimations() {
        if (this.moveAnalysis.isMoving()) {
            this.animationDispatcher.clientWalk();
            return;
        }
        this.animationDispatcher.clientIdle();
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(0, new RandomStrollGoal(this, 0.7F));
    }
}
