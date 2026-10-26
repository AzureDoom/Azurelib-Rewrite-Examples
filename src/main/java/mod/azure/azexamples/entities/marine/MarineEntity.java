package mod.azure.azexamples.entities.marine;

import mod.azure.azurelib.platform.mc.Mc;
import mod.azure.azurelib.util.AzEquipmentHolder;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.world.World;

import mod.azure.azexamples.platform.ExMc;

public class MarineEntity extends AnimalEntity implements AzEquipmentHolder {

    private static final String EQUIPMENT_KEY = "AzExamplesEquipment";

    /** 0 held item, 1 boots, 2 leggings, 3 chestplate, 4 helmet. */
    private final ItemStack[] equipment = new ItemStack[5];

    public MarineEntity(World world) {
        super(world);
        this.setBoundingBoxSpacing(0.6F, 1.8F);
    }

    @Override
    public boolean interact(PlayerEntity player) {
        ItemStack stack = Mc.heldItem(player);

        if (stack == null) {
            return super.interact(player);
        }

        // Beta armor types:
        // 0 = helmet, 1 = chestplate, 2 = leggings, 3 = boots
        //
        // AzureLib equipment:
        // 0 = held item, 1 = boots, 2 = leggings,
        // 3 = chestplate, 4 = helmet
        int armorType = Mc.armorType(Mc.item(stack));
        int slot = armorType >= 0 ? 4 - armorType : 0;

        this.equipment[slot] = new ItemStack(
            Mc.item(stack),
            1,
            Mc.damage(stack)
        );

        return true;
    }

    @Override
    public ItemStack getAzEquipment(int slot) {
        return slot >= 0 && slot < this.equipment.length ? this.equipment[slot] : null;
    }

    public boolean isLeftHanded() {
        return false;
    }

    @Override
    public boolean isCollidable() {
        return true;
    }

    /**
     * Saves each non-empty slot as its own stack compound under {@value #EQUIPMENT_KEY}. StationAPI's stack NBT (and so
     * AzureLib's item UUID) is saved with each stack.
     */
    @Override
    public void writeNbt(NbtCompound compound) {
        super.writeNbt(compound);
        NbtCompound equipmentTag = new NbtCompound();
        for (int i = 0; i < this.equipment.length; i++) {
            if (this.equipment[i] != null) {
                ExMc.putCompound(equipmentTag, "Slot" + i, ExMc.writeStack(this.equipment[i]));
            }
        }
        ExMc.putCompound(compound, EQUIPMENT_KEY, equipmentTag);
    }

    @Override
    public void readNbt(NbtCompound compound) {
        super.readNbt(compound);
        if (!Mc.nbtHas(compound, EQUIPMENT_KEY)) {
            return;
        }
        NbtCompound equipmentTag = ExMc.getCompound(compound, EQUIPMENT_KEY);
        for (int i = 0; i < this.equipment.length; i++) {
            String key = "Slot" + i;
            this.equipment[i] = Mc.nbtHas(equipmentTag, key)
                ? ExMc.readStack(ExMc.getCompound(equipmentTag, key))
                : null;
        }
    }
}
