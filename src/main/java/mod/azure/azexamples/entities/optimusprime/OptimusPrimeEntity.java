package mod.azure.azexamples.entities.optimusprime;

import net.minecraft.entity.MobEntity;
import net.minecraft.world.World;

/**
 * Credit to <a href="https://github.com/MARCTR0N">Marctron</a> for the model/textures/animations of this example.
 */
public class OptimusPrimeEntity extends MobEntity {

    protected final OptimusPrimeAnimationDispatcher animationDispatcher;

    private boolean animationStarted;

    public OptimusPrimeEntity(World world) {
        super(world);
        this.setBoundingBoxSpacing(3.0F, 7.0F);
        this.animationDispatcher = new OptimusPrimeAnimationDispatcher(this);
        this.ignoreFrustumCull = true;
    }

    @Override
    public void tick() {
        super.tick();

        if (!world.isRemote) {
            updateAnimations();
        }
    }

    @Override
    protected boolean canDespawn() {
        return false;
    }

    public void updateAnimations() {
        if (animationStarted) {
            return;
        }
        animationStarted = true;

        animationDispatcher.clientIdle();
    }
}
