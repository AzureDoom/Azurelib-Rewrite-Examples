package mod.azure.azexamples.items.gunwitharm;

import mod.azure.azurelib.platform.Services;
import mod.azure.azurelib.platform.mc.Mc;
import mod.azure.azurelib.util.client.ClientUtils;
import net.minecraft.entity.Entity;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.mob.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

import java.util.Map;
import java.util.WeakHashMap;

import mod.azure.azexamples.platform.ExMc;

public class GunWithArmItem extends Item {

    private static final int FIRE_COOLDOWN_TICKS = 10;

    private static final double RANGE = 35.0D;

    final GunWithArmDispatcher dispatcher;

    private final Map<Entity, Long> nextShot = new WeakHashMap<>();

    public GunWithArmItem(int id) {
        super(id);
        this.setMaxStackSize(1);
        this.dispatcher = new GunWithArmDispatcher();
    }

    @Override
    public ItemStack startUsing(ItemStack stack, World world, PlayerEntity player) {
        long now = Mc.time(world);
        Long allowedAt = nextShot.get(player);
        if (allowedAt == null || now >= allowedAt) {
            nextShot.put(player, now + FIRE_COOLDOWN_TICKS);
            fire(world, player, stack);
        }
        return stack;
    }

    private void fire(World world, PlayerEntity player, ItemStack itemStack) {
        if (!Mc.isClientSide(world)) {
            MobEntity hit = raycastEntity(world, player, RANGE);
            if (hit != null) {
                ExMc.damage(hit, player, 5);
            }
        }
        dispatcher.serverFire(player, itemStack);
        if (
            !Services.PLATFORM.isServerEnvironment()
                && player == ClientUtils.getClientPlayer()
        ) {
            ExMc.turnView(player, getRecoilX(world) * 5, -getRecoilY() * 5);
        }
    }

    public static float getRecoilX(World world) {
        return (ExMc.random(world).nextBoolean() ? 1f : -1f) / 2;
    }

    public static float getRecoilY() {
        return 2.5f / 2;
    }

    /** The closest living entity on the player's line of sight, stopping at the first solid block. */
    private static MobEntity raycastEntity(World world, PlayerEntity player, double range) {
        double sx = Mc.x(player);
        // A Beta player's y is its eye position (the bounding box sits yOffset below it).
        double sy = Mc.y(player);
        double sz = Mc.z(player);
        float yaw = (float) Math.toRadians(Mc.yaw(player));
        float pitch = (float) Math.toRadians(Mc.pitch(player));
        double dx = -Math.sin(yaw) * Math.cos(pitch);
        double dy = -Math.sin(pitch);
        double dz = Math.cos(yaw) * Math.cos(pitch);

        double maxDistance = blockDistance(world, sx, sy, sz, dx, dy, dz, range);

        MobEntity closest = null;
        double closestDistance = maxDistance;
        for (Entity entity : Mc.entities(world)) {
            if (entity == player || !(entity instanceof MobEntity) || !Mc.isAlive(entity))
                continue;
            double half = Mc.width(entity) / 2.0D + 0.1D;
            double t = rayBox(
                sx,
                sy,
                sz,
                dx,
                dy,
                dz,
                Mc.x(entity) - half,
                Mc.y(entity) - 0.1D,
                Mc.z(entity) - half,
                Mc.x(entity) + half,
                Mc.y(entity) + Mc.height(entity) + 0.1D,
                Mc.z(entity) + half
            );
            if (t >= 0 && t <= closestDistance) {
                closestDistance = t;
                closest = (MobEntity) entity;
            }
        }
        return closest;
    }

    /** Distance along the ray to the first non-air block, sampled every 0.1 blocks. */
    private static double blockDistance(
        World world,
        double sx,
        double sy,
        double sz,
        double dx,
        double dy,
        double dz,
        double range
    ) {
        for (double t = 0; t <= range; t += 0.1D) {
            int x = (int) Math.floor(sx + dx * t);
            int y = (int) Math.floor(sy + dy * t);
            int z = (int) Math.floor(sz + dz * t);
            if (y >= 0 && y < 128 && Mc.blockId(world, x, y, z) != 0)
                return t;
        }
        return range;
    }

    /** Slab test; returns the entry distance along the (unit) ray, or -1 if it misses. */
    private static double rayBox(
        double ox,
        double oy,
        double oz,
        double dx,
        double dy,
        double dz,
        double minX,
        double minY,
        double minZ,
        double maxX,
        double maxY,
        double maxZ
    ) {
        double tMin = 0;
        double tMax = Double.MAX_VALUE;
        double[] o = { ox, oy, oz };
        double[] d = { dx, dy, dz };
        double[] lo = { minX, minY, minZ };
        double[] hi = { maxX, maxY, maxZ };
        for (int axis = 0; axis < 3; axis++) {
            if (Math.abs(d[axis]) < 1.0E-9) {
                if (o[axis] < lo[axis] || o[axis] > hi[axis])
                    return -1;
                continue;
            }
            double t1 = (lo[axis] - o[axis]) / d[axis];
            double t2 = (hi[axis] - o[axis]) / d[axis];
            tMin = Math.max(tMin, Math.min(t1, t2));
            tMax = Math.min(tMax, Math.max(t1, t2));
            if (tMax < tMin)
                return -1;
        }
        return tMin;
    }
}
