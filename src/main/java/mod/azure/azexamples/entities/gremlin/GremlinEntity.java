package mod.azure.azexamples.entities.gremlin;

import net.minecraft.entity.LivingEntity;
import net.minecraft.world.World;

public class GremlinEntity extends LivingEntity {

    public GremlinEntity(World world) {
        super(world);
        this.setBoundingBoxSpacing(0.6F, 1.8F);
    }
}
