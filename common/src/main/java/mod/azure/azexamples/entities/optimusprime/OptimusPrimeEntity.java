package mod.azure.azexamples.entities.optimusprime;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.level.Level;

/**
 * Credit to <a href="https://github.com/MARCTR0N">Marctron</a> for the model/textures/animations of this example.
 */
public class OptimusPrimeEntity extends Monster {

    protected final OptimusPrimeAnimationDispatcher animationDispatcher;

    private boolean animationStarted;

    public OptimusPrimeEntity(EntityType<? extends Monster> entityType, Level level) {
        super(entityType, level);
        this.animationDispatcher = new OptimusPrimeAnimationDispatcher(this);
    }

    @Override
    public void tick() {
        super.tick();

        if (level.isClientSide()) {
            updateAnimations();
        }
    }

    @Override
    public boolean requiresCustomPersistence() {
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
