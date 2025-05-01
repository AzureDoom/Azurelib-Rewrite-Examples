package mod.azure.azexamples.entities.doomhunter;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.level.Level;

public class DoomHunterEntity extends Monster {

    protected final DoomHunterAnimationDispatcher animationDispatcher;

    public DoomHunterEntity(EntityType<? extends Monster> entityType, Level level) {
        super(entityType, level);
        this.animationDispatcher = new DoomHunterAnimationDispatcher(this);
    }
}
