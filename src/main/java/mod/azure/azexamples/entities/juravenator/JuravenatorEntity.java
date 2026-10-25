package mod.azure.azexamples.entities.juravenator;

import mod.azure.azurelib.util.MoveAnalysis;
import net.minecraft.entity.mob.MonsterEntity;
import net.minecraft.world.World;

public class JuravenatorEntity extends MonsterEntity {

    private final MoveAnalysis moveAnalysis;

    protected final JuravenatorAnimationDispatcher animationDispatcher;

    public JuravenatorEntity(World world) {
        super(world);
        this.setBoundingBoxSpacing(3.0F, 7.0F);
        this.moveAnalysis = new MoveAnalysis(this);
        this.animationDispatcher = new JuravenatorAnimationDispatcher(this);
    }

    @Override
    public void tick() {
        super.tick();
        moveAnalysis.update();

        if (!world.isRemote) {
            updateAnimations();
        }
    }

    public void updateAnimations() {
        if (this.moveAnalysis.isMoving()) {
            this.animationDispatcher.clientWalk();
            return;
        }

        this.animationDispatcher.clientIdle();
    }
}
