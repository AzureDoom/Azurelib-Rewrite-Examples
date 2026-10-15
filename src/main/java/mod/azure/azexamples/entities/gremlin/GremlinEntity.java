package mod.azure.azexamples.entities.gremlin;

import net.minecraft.entity.mob.MobEntity;
import net.minecraft.world.World;

public class GremlinEntity extends MobEntity {

    public GremlinEntity(World world) {
        super(world);
        this.setSize(0.6F, 1.8F);
    }
}
