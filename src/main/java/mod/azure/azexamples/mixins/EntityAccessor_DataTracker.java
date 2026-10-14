package mod.azure.azexamples.mixins;

import net.minecraft.entity.Entity;
import net.minecraft.entity.data.DataTracker;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(Entity.class)
public interface EntityAccessor_DataTracker {

    @Accessor("dataTracker")
    DataTracker azexamples$getDataTracker();
}
