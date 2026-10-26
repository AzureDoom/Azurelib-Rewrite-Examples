package mod.azure.azexamples.entities.optimusprime;

import net.minecraft.entity.EntityLiving;
import net.minecraft.world.World;

/**
 * Credit to <a href="https://github.com/MARCTR0N">Marctron</a> for the model/textures/animations of this example.
 */
public class OptimusPrimeEntity extends EntityLiving {

    protected final OptimusPrimeAnimationDispatcher animationDispatcher;

    private boolean animationStarted;

    public OptimusPrimeEntity(World world) {
        super(world);
        this.setSize(3.0F, 7.0F);
        this.animationDispatcher = new OptimusPrimeAnimationDispatcher(this);
        this.ignoreFrustumCheck = true;
    }

    @Override
    public void onUpdate() {
        super.onUpdate();

        if (worldObj.isRemote) {
            updateAnimations();
        }
    }

    @Override
    public boolean isNoDespawnRequired() {
        return true;
    }

    public void updateAnimations() {
        if (animationStarted) {
            return;
        }
        animationStarted = true;

        animationDispatcher.clientIdle();
    }
}
