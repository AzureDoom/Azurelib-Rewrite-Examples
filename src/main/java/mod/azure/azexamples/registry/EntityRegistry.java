package mod.azure.azexamples.registry;

import net.minecraft.entity.Entity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

import mod.azure.azexamples.AzExamplesMod;
import mod.azure.azexamples.entities.doomhunter.DoomHunterEntity;
import mod.azure.azexamples.entities.gremlin.GremlinEntity;
import mod.azure.azexamples.entities.juravenator.JuravenatorEntity;
import mod.azure.azexamples.entities.manul.ManulEntity;
import mod.azure.azexamples.entities.marauder.MarauderEntity;
import mod.azure.azexamples.entities.marine.MarineEntity;
import mod.azure.azexamples.entities.meshtest.MeshTestEntity;
import mod.azure.azexamples.entities.meshtest.MeshTestVariant;
import mod.azure.azexamples.entities.optimusprime.OptimusPrimeEntity;

/**
 * Entity registration. 1.7.10 only gives spawn eggs to entities with global IDs, so the examples use their own
 * {@link mod.azure.azexamples.items.spawnegg.ExampleSpawnEggItem} (one damage value per entry below).
 */
public final class EntityRegistry {

    private static final List<Entry> ENTRIES = new ArrayList<>();

    private EntityRegistry() {}

    public static void register() {
        add(MarauderEntity.class, "marauder", 0xe9e2ed, 0x574f44);
        add(ManulEntity.class, "manul", 0xc38160, 0x3d362e);
        add(DoomHunterEntity.class, "doomhunter", 0x5a575a, 0x86472e);
        add(JuravenatorEntity.class, "juravenator", 0xc09e58, 0x574028);
        add(MarineEntity.class, "marine", 0xc09e58, 0x574028);
        add(GremlinEntity.class, "gremlin", 0x424242, 0x606060);
        add(OptimusPrimeEntity.class, "optimusprime", 0x424242, 0x606060);
        for (MeshTestVariant variant : MeshTestVariant.values()) {
            addMeshTest(variant);
        }
    }

    public static List<Entry> entries() {
        return Collections.unmodifiableList(ENTRIES);
    }

    private static void add(Class<? extends Entity> type, String name, int primaryEgg, int secondaryEgg) {
        int id = ENTRIES.size();
        cpw.mods.fml.common.registry.EntityRegistry.registerModEntity(
            type,
            name,
            id,
            AzExamplesMod.instance,
            80,
            3,
            true
        );
        ENTRIES.add(new Entry(type, name, primaryEgg, secondaryEgg));
    }

    private static void addMeshTest(MeshTestVariant variant) {
        String name = "mesh_test_" + variant.name().toLowerCase(Locale.ROOT);

        add(
            meshTestClass(variant),
            name,
            0x777777,
            0x44AAFF
        );
    }

    private static Class<? extends Entity> meshTestClass(MeshTestVariant variant) {
        switch (variant) {
            case RIGID:
                return MeshTestEntity.Rigid.class;
            case ANIMATED:
                return MeshTestEntity.Animated.class;
            case MIXED:
                return MeshTestEntity.Mixed.class;
            case SKINNED:
                return MeshTestEntity.Skinned.class;
            case SCALED:
                return MeshTestEntity.Scaled.class;
            case COPIES:
                return MeshTestEntity.Copies.class;
            case ANIMATED_OWNER:
                return MeshTestEntity.AnimatedOwner.class;
            default:
                throw new IllegalArgumentException(
                    "Unknown mesh test variant: " + variant
                );
        }
    }

    public static final class Entry {

        public final Class<? extends Entity> type;

        public final String name;

        public final int primaryColor;

        public final int secondaryColor;

        private Entry(Class<? extends Entity> type, String name, int primaryColor, int secondaryColor) {
            this.type = type;
            this.name = name;
            this.primaryColor = primaryColor;
            this.secondaryColor = secondaryColor;
        }
    }
}
