package mod.azure.azexamples.entities.doomhunter;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.level.Level;

public class DoomHunterEntity extends Monster {

    private final DoomHunterAnimationDispatcher animationDispatcher;

    public DoomHunterEntity(EntityType<? extends Monster> entityType, Level level) {
        super(entityType, level);
        this.animationDispatcher = new DoomHunterAnimationDispatcher(this);
    }

    /**
     * Overrides the default tick behavior for the entity to include additional animation logic.
     * </br>
     * </br>
     * Invokes the super method to perform core tick operations and checks if the current
     * level is client-side and the entity is not in an aggressive state. If these conditions
     * are satisfied, this method triggers the client-side idle animation for the entity
     * using the {@code DoomHunterAnimationDispatcher}.
     * </br>
     * </br>
     * The animation dispatcher handles animation commands by utilizing a client-side
     * dispatcher setup for controlling the entity's animation state.
     */
    @Override
    public void tick() {
        super.tick();

        if (this.level().isClientSide && !this.isAggressive()) {
            animationDispatcher.clientIdle();
        }
    }
}
