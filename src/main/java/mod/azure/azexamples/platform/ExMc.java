package mod.azure.azexamples.platform;

import mod.azure.azurelib.platform.mc.Mc;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.mob.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.world.World;

import java.util.Random;

import mod.azure.azexamples.CommonStrings;

/**
 * The examples' own bridge to Minecraft members that AzureLib's {@link Mc} doesn't cover. Every access is annotated
 * with its MCP (b1.7.3) name; names already confirmed by the AzureLib port are marked {@code (Feather)}.
 */
public final class ExMc {

    /** Sound key for {@code assets/azexamples/sounds/vigilance_fire.ogg}, see the client's ExamplesSounds. */
    public static final String VIGILANCE_FIRE = CommonStrings.MOD_ID + ".vigilance_fire";

    private ExMc() {
        throw new UnsupportedOperationException();
    }

    public static Item diamondSword() {
        return Item.DIAMOND_SWORD; // MCP: Item.swordDiamond
    }

    public static Item diamondHelmet() {
        return Item.DIAMOND_HELMET; // MCP: Item.helmetDiamond
    }

    public static Item diamondChestplate() {
        return Item.DIAMOND_CHESTPLATE; // MCP: Item.plateDiamond
    }

    public static Item diamondLeggings() {
        return Item.DIAMOND_LEGGINGS; // MCP: Item.legsDiamond
    }

    public static Item diamondBoots() {
        return Item.DIAMOND_BOOTS; // MCP: Item.bootsDiamond
    }

    public static int eggTextureId() {
        return Item.EGG.getSprite(0); // MCP: Item.egg, Item.getIconFromDamage (Feather)
    }

    /** Translation key {@code item.azexamples.<name>}, as in {@code assets/azexamples/lang/en_US.lang}. */
    public static void name(Item item, String name) {
        item.setKey(CommonStrings.MOD_ID + "." + name);
    }

    /** Translation key {@code tile.azexamples.<name>}. */
    public static void name(Block block, String name) {
        block.setKey(CommonStrings.MOD_ID + "." + name);
    }

    public static Random random(World world) {
        return world.random; // MCP: World.rand
    }

    public static void spawn(World world, Entity entity, double x, double y, double z, float yaw) {
        entity.setPositionAndAngles(x, y, z, yaw, 0.0F); // MCP: Entity.setLocationAndAngles
        world.addEntity(entity); // MCP: World.entityJoinedWorld
    }

    public static void playSound(Entity entity, String sound, float volume, float pitch) {
        Mc.world(entity).playSound(entity, sound, volume, pitch); // MCP: World.playSoundAtEntity
    }

    public static void damage(Entity target, Entity attacker, int amount) {
        target.takeDamage(attacker, amount);
    }

    public static void turnView(PlayerEntity player, float yaw, float pitch) {
        player.yaw += yaw; // MCP: rotationYaw (Feather)
        player.pitch = Math.max(-90.0F, Math.min(90.0F, player.pitch + pitch)); // MCP: rotationPitch (Feather)
    }

    public static boolean placeBlock(World world, int x, int y, int z, Block block) {
        return world.setBlock(x, y, z, Mc.blockId(block)); // MCP: World.setBlockWithNotify
    }

    public static NbtCompound writeStack(ItemStack stack) {
        return stack.writeNbt(new NbtCompound()); // MCP: ItemStack.writeToNBT (Feather)
    }

    public static ItemStack readStack(NbtCompound tag) {
        return new ItemStack(tag); // MCP: ItemStack(NBTTagCompound)
    }

    public static void putCompound(NbtCompound parent, String key, NbtCompound child) {
        Mc.nbtPutCompound(parent, key, child);
    }

    public static NbtCompound getCompound(NbtCompound parent, String key) {
        return Mc.nbtGetCompound(parent, key);
    }

    public static void decrementStack(ItemStack stack) {
        stack.size--; // MCP: ItemStack.stackSize (Feather)
    }
}
