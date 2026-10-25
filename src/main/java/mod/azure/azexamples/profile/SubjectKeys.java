package mod.azure.azexamples.profile;

import mod.azure.azurelib.animation.controller.AzAbstractAnimationController;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.Entity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.modificationstation.stationapi.api.registry.ItemRegistry;
import net.modificationstation.stationapi.api.util.Identifier;

/**
 * Maps hook subjects to stable aggregation keys without allocating: an entity aggregates under its class, a block
 * entity under its class, an item stack under its item, a controller under its name. Labels are resolved only when a
 * report is written.
 * <p>
 * Beta has no entity or block entity types: both are registered by class, so the class is the type. Items have
 * StationAPI registry identifiers.
 */
final class SubjectKeys {

    static final Object UNKNOWN = new Object();

    /** Distinguishes entity and block entity class keys from other class keys in {@link #label}. */
    private record EntityKey(Class<?> type) {}

    private record BlockEntityKey(Class<?> type) {}

    private static final ClassValue<EntityKey> ENTITY_KEYS = new ClassValue<>() {

        @Override
        protected EntityKey computeValue(Class<?> type) {
            return new EntityKey(type);
        }
    };

    private static final ClassValue<BlockEntityKey> BLOCK_ENTITY_KEYS = new ClassValue<>() {

        @Override
        protected BlockEntityKey computeValue(Class<?> type) {
            return new BlockEntityKey(type);
        }
    };

    private SubjectKeys() {}

    static Object key(Object subject) {
        if (subject == null) {
            return UNKNOWN;
        }
        if (subject instanceof Entity) {
            return ENTITY_KEYS.get(subject.getClass());
        }
        if (subject instanceof BlockEntity) {
            return BLOCK_ENTITY_KEYS.get(subject.getClass());
        }
        if (subject instanceof ItemStack stack) {
            Item item = stack.getItem();
            return item == null ? UNKNOWN : item;
        }
        if (subject instanceof AzAbstractAnimationController controller) {
            return controller.name();
        }
        return subject.getClass();
    }

    static String label(Object key) {
        if (key == UNKNOWN) {
            return "unknown";
        }

        if (key instanceof EntityKey entity) {
            return "entity:" + entity.type().getSimpleName();
        }

        if (key instanceof BlockEntityKey blockEntity) {
            return "block_entity:" + blockEntity.type().getSimpleName();
        }

        if (key instanceof Item item) {
            return itemLabel(item);
        }

        if (key instanceof String controllerName) {
            return "controller:" + controllerName;
        }

        if (key instanceof Class<?> type) {
            return type.getName();
        }

        return key.toString();
    }

    private static String itemLabel(Item item) {
        try {
            Identifier id = ItemRegistry.INSTANCE.getId(item);
            if (id != null) {
                return id.toString();
            }
        } catch (RuntimeException ignored) {}
        return "item#" + item.id;
    }
}
