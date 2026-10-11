
package mod.azure.azexamples.items.gunwitharm;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

import javax.annotation.Nonnull;

import mod.azure.azurelib.util.client.ClientUtils;

public class GunWithArmItem extends Item {

    final GunWithArmDispatcher dispatcher;

    public GunWithArmItem() {
        this.setMaxStackSize(1);
        this.dispatcher = new GunWithArmDispatcher();
    }

    @Override
    public void onUsingTick(
        ItemStack stack,
        EntityLivingBase livingEntity,
        int remainingUseDuration
    ) {
        if (livingEntity instanceof EntityPlayer) {
            EntityPlayer player = (EntityPlayer) livingEntity;

            if (!player.getCooldownTracker().hasCooldown(stack.getItem())) {
                RayTraceResult hitResult = getGunHitResult(
                    player.world,
                    player,
                    35.0D
                );

                fire(player.world, player, stack, hitResult);
            }
        }

        super.onUsingTick(stack, livingEntity, remainingUseDuration);
    }

    @Override
    @Nonnull
    public ActionResult<ItemStack> onItemRightClick(
        @Nonnull World world,
        EntityPlayer user,
        @Nonnull EnumHand hand
    ) {
        ItemStack itemStack = user.getHeldItem(hand);
        user.setActiveHand(hand);

        return new ActionResult<>(EnumActionResult.SUCCESS, itemStack);
    }

    @Override
    public int getMaxItemUseDuration(@Nonnull ItemStack stack) {
        return 72000;
    }

    /**
     * Performs a 35-block hitscan, respecting block collisions.
     */
    private static RayTraceResult getGunHitResult(
        World world,
        EntityPlayer player,
        double range
    ) {
        Vec3d start = player.getPositionEyes(1.0F);
        Vec3d direction = player.getLook(1.0F);
        Vec3d end = start.add(direction.scale(range));

        // Raycast against blocks first.
        RayTraceResult blockHit = world.rayTraceBlocks(
            start,
            end,
            false,
            true,
            false
        );

        // Limit entity hits to the first blocking surface.
        double maxDistance = range * range;

        if (blockHit != null) {
            maxDistance = start.squareDistanceTo(blockHit.hitVec);
        }

        AxisAlignedBB searchBox = player.getEntityBoundingBox()
            .expand(
                direction.x * range,
                direction.y * range,
                direction.z * range
            )
            .grow(1.0D);

        Entity closestEntity = null;
        Vec3d closestHitVec = null;
        double closestDistance = maxDistance;

        for (
            Entity entity : world.getEntitiesWithinAABBExcludingEntity(
                player,
                searchBox
            )
        ) {
            if (
                !(entity instanceof EntityLivingBase)
                    || !entity.canBeCollidedWith()
                    || (entity instanceof EntityPlayer
                        && ((EntityPlayer) entity).isSpectator())
            ) {
                continue;
            }

            AxisAlignedBB entityBox = entity.getEntityBoundingBox()
                .grow(entity.getCollisionBorderSize());

            RayTraceResult entityHit = entityBox.calculateIntercept(
                start,
                end
            );

            Vec3d hitVec = null;

            if (entityBox.contains(start)) {
                hitVec = start;
            } else if (entityHit != null) {
                hitVec = entityHit.hitVec;
            }

            if (hitVec != null) {
                double distance = start.squareDistanceTo(hitVec);

                if (distance < closestDistance) {
                    closestDistance = distance;
                    closestEntity = entity;
                    closestHitVec = hitVec;
                }
            }
        }

        if (closestEntity != null) {
            return new RayTraceResult(closestEntity, closestHitVec);
        }

        return blockHit;
    }

    /**
     * Fires the weapon, applies damage, triggers animations, adds cooldown, and handles client recoil.
     */
    private void fire(
        @Nonnull World world,
        @Nonnull EntityPlayer player,
        ItemStack itemStack,
        RayTraceResult hitResult
    ) {
        if (!world.isRemote) {
            damageEntity(world, player, hitResult);
        }

        dispatcher.serverFire(player, itemStack);

        player.getCooldownTracker()
            .setCooldown(
                itemStack.getItem(),
                5
            );

        if (world.isRemote) {
            ClientUtils.getClientPlayer()
                .turn(
                    getRecoilX(player) * 5,
                    -getRecoilY() * 5
                );
        }
    }

    /**
     * Applies five damage to a living entity hit by the gun.
     */
    public static void damageEntity(
        @Nonnull World world,
        EntityPlayer player,
        RayTraceResult hitResult
    ) {
        if (world.isRemote || hitResult == null) {
            return;
        }

        if (
            hitResult.typeOfHit == RayTraceResult.Type.ENTITY
                && hitResult.entityHit instanceof EntityLivingBase
        ) {

            EntityLivingBase livingEntity =
                (EntityLivingBase) hitResult.entityHit;

            livingEntity.hurtResistantTime = 0;

            livingEntity.attackEntityFrom(
                DamageSource.causePlayerDamage(player),
                5.0F
            );
        }
    }

    public static float getRecoilX(EntityPlayer player) {
        float baseRecoilX =
            player.world.rand.nextBoolean() ? 1f : -1f;

        return baseRecoilX / 2;
    }

    public static float getRecoilY() {
        return 2.5f / 2;
    }

    /**
     * True if the entity is currently using a gun. Used by the client mixins.
     */
    public static boolean isUsingGun(EntityLivingBase entity) {
        return entity != null
            && entity.getActiveItemStack().getItem() instanceof GunWithArmItem;
    }
}
