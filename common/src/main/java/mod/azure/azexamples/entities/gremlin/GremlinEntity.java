package mod.azure.azexamples.entities.gremlin;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.level.Level;

public class GremlinEntity extends Mob {

    public GremlinEntity(EntityType<? extends Mob> entityType, Level level) {
        super(entityType, level);
    }
}
