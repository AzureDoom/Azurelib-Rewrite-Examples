package mod.azure.azexamples.profiler;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityList;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;

import mod.azure.azurelib.animation.controller.AzAbstractAnimationController;

final class SubjectKeys {

    static final Object UNKNOWN = new Object();

    private SubjectKeys() {}

    static Object key(Object subject) {
        if (subject == null) {
            return UNKNOWN;
        }

        if (subject instanceof Entity || subject instanceof TileEntity) {
            return subject.getClass();
        }

        if (subject instanceof ItemStack) {
            return ((ItemStack) subject).getItem();
        }

        if (subject instanceof AzAbstractAnimationController) {
            return ((AzAbstractAnimationController) subject).name();
        }

        return subject.getClass();
    }

    @SuppressWarnings("unchecked")
    static String label(Object key) {
        if (key == UNKNOWN) {
            return "unknown";
        }

        if (key instanceof Class) {
            Class<?> type = (Class<?>) key;

            if (Entity.class.isAssignableFrom(type)) {
                ResourceLocation id = EntityList.getKey((Class<? extends Entity>) type);
                return id != null ? id.toString() : type.getName();
            }

            if (TileEntity.class.isAssignableFrom(type)) {
                ResourceLocation id = TileEntity.getKey((Class<? extends TileEntity>) type);
                return id != null ? id.toString() : type.getName();
            }

            return type.getName();
        }

        if (key instanceof Item) {
            ResourceLocation id = ((Item) key).getRegistryName();
            return id != null ? id.toString() : key.toString();
        }

        if (key instanceof String) {
            return "controller:" + key;
        }

        return key.toString();
    }
}
