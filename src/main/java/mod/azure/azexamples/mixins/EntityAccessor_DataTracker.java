package mod.azure.azexamples.mixins;

import net.minecraft.entity.Entity;
import net.minecraft.entity.data.SyncedData; // MCP: DataWatcher
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(Entity.class)
public interface EntityAccessor_DataTracker {

    // MCP: Entity.dataWatcher
    @Accessor("syncedData")
    SyncedData azexamples$getDataTracker();
}
