package mod.azure.azexamples.entities.gremlin;

import net.minecraft.entity.EntityLiving;
import net.minecraft.world.World;

public class GremlinEntity extends EntityLiving {

    public GremlinEntity(World world) {
        super(world);
        this.setSize(0.6F, 1.8F);
    }
}
