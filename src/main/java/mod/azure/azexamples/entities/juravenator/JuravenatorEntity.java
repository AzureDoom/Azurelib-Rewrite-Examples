package mod.azure.azexamples.entities.juravenator;

import net.minecraft.entity.ai.EntityAIWander;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.world.World;

import mod.azure.azurelib.util.MoveAnalysis;

public class JuravenatorEntity extends EntityMob {

    private final MoveAnalysis moveAnalysis;

    protected final JuravenatorAnimationDispatcher animationDispatcher;

    public JuravenatorEntity(World world) {
        super(world);
        this.setSize(3.0F, 7.0F);
        this.moveAnalysis = new MoveAnalysis(this);
        this.animationDispatcher = new JuravenatorAnimationDispatcher(this);
    }

    @Override
    public void onUpdate() {
        super.onUpdate();
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
    protected void initEntityAI() {
        this.tasks.addTask(0, new EntityAIWander(this, 0.7D));
    }
}
