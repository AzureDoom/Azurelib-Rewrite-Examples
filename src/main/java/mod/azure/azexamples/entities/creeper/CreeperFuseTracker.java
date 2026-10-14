package mod.azure.azexamples.entities.creeper;

import mod.azure.azurelib.platform.mc.Mc;
import net.minecraft.entity.mob.CreeperEntity;

import java.util.Map;
import java.util.WeakHashMap;

import mod.azure.azexamples.mixins.EntityAccessor_DataTracker;

final class CreeperFuseTracker {

    private static final int FUSE_STATE = 16;

    private static final int MAX_FUSE = 30;

    private static final Map<CreeperEntity, Fuse> FUSES = new WeakHashMap<>();

    private CreeperFuseTracker() {}

    static float swelling(CreeperEntity creeper, float partialTick) {
        Fuse fuse = FUSES.computeIfAbsent(creeper, $ -> new Fuse());
        long now = Mc.time(Mc.world(creeper));
        byte state = ((EntityAccessor_DataTracker) creeper).azexamples$getDataTracker().getByte(FUSE_STATE);

        if (fuse.lastTick < 0 || now < fuse.lastTick || now - fuse.lastTick > 40) {
            // First sight, time went backwards, or the creeper was out of view for a while.
            fuse.lastTick = now;
        }
        while (fuse.lastTick < now) {
            fuse.previous = fuse.current;
            fuse.current = Math.max(0, Math.min(MAX_FUSE, fuse.current + state));
            fuse.lastTick++;
        }

        return (fuse.previous + (fuse.current - fuse.previous) * partialTick) / (MAX_FUSE - 2);
    }

    private static final class Fuse {

        long lastTick = -1;

        int previous;

        int current;
    }
}
