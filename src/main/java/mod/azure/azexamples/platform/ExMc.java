package mod.azure.azexamples.platform;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.world.World;

public final class ExMc {

    private ExMc() {
        throw new UnsupportedOperationException();
    }

    public static Item diamondSword() {
        return Item.DIAMOND_SWORD;
    }

    public static Item diamondHelmet() {
        return Item.DIAMOND_HELMET;
    }

    public static Item diamondChestplate() {
        return Item.DIAMOND_CHESTPLATE;
    }

    public static Item diamondLeggings() {
        return Item.DIAMOND_LEGGINGS;
    }

    public static Item diamondBoots() {
        return Item.DIAMOND_BOOTS;
    }

    public static int eggTextureId() {
        return Item.EGG.getTextureId(0);
    }

    public static java.util.Random random(World world) {
        return world.random;
    }

    public static void spawn(World world, Entity entity, double x, double y, double z, float yaw) {
        entity.setPositionAndAngles(x, y, z, yaw, 0.0F);
        world.spawnEntity(entity);
    }

    public static void playSound(Entity entity, String sound, float volume, float pitch) {
        entity.world.playSound(entity, sound, volume, pitch);
    }

    public static void damage(Entity target, Entity attacker, int amount) {
        target.damage(attacker, amount);
    }

    public static void turnView(PlayerEntity player, float yaw, float pitch) {
        player.yaw += yaw;
        player.pitch = Math.max(-90.0F, Math.min(90.0F, player.pitch + pitch));
    }

    public static boolean placeBlock(World world, int x, int y, int z, net.minecraft.block.Block block) {
        return world.setBlock(x, y, z, block.id);
    }

    public static NbtCompound writeStack(ItemStack stack) {
        return stack.writeNbt(new NbtCompound());
    }

    public static ItemStack readStack(NbtCompound tag) {
        return new ItemStack(tag);
    }

    public static void putCompound(
        NbtCompound parent,
        String key,
        NbtCompound child
    ) {
        parent.put(key, child);
    }

    public static NbtCompound getCompound(NbtCompound parent, String key) {
        return parent.getCompound(key);
    }

    public static void decrementStack(ItemStack stack) {
        stack.count--;
    }
}
