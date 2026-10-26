package mod.azure.azexamples.registry;

import net.minecraft.entity.Entity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import mod.azure.azexamples.entities.doomhunter.DoomHunterEntity;
import mod.azure.azexamples.entities.gremlin.GremlinEntity;
import mod.azure.azexamples.entities.juravenator.JuravenatorEntity;
import mod.azure.azexamples.entities.manul.ManulEntity;
import mod.azure.azexamples.entities.marauder.MarauderEntity;
import mod.azure.azexamples.entities.marine.MarineEntity;
import mod.azure.azexamples.entities.optimusprime.OptimusPrimeEntity;

/**
 * The examples' entities. Beta has no spawn eggs, so {@link ItemRegistry} creates an
 * {@link mod.azure.azexamples.items.spawnegg.ExampleSpawnEggItem} for each entry below.
 */
public final class EntityRegistry {

    private static final List<Entry> ENTRIES = new ArrayList<>();

    static {
        add(MarauderEntity.class, "marauder");
        add(ManulEntity.class, "manul");
        add(DoomHunterEntity.class, "doomhunter");
        add(JuravenatorEntity.class, "juravenator");
        add(MarineEntity.class, "marine");
        add(GremlinEntity.class, "gremlin");
        add(OptimusPrimeEntity.class, "optimusprime");
    }

    private EntityRegistry() {}

    public static List<Entry> entries() {
        return Collections.unmodifiableList(ENTRIES);
    }

    private static void add(Class<? extends Entity> type, String name) {
        ENTRIES.add(new Entry(type, name));
    }

    public static final class Entry {

        public final Class<? extends Entity> type;

        public final String name;

        private Entry(Class<? extends Entity> type, String name) {
            this.type = type;
            this.name = name;
        }
    }
}
