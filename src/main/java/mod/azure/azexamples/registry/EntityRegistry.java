package mod.azure.azexamples.registry;

import net.minecraft.entity.Entity;
import net.minecraftforge.fml.common.registry.EntityEntry;
import net.minecraftforge.fml.common.registry.EntityEntryBuilder;

import java.util.ArrayList;
import java.util.List;

import mod.azure.azexamples.CommonMod;
import mod.azure.azexamples.CommonStrings;
import mod.azure.azexamples.entities.doomhunter.DoomHunterEntity;
import mod.azure.azexamples.entities.gremlin.GremlinEntity;
import mod.azure.azexamples.entities.juravenator.JuravenatorEntity;
import mod.azure.azexamples.entities.manul.ManulEntity;
import mod.azure.azexamples.entities.marauder.MarauderEntity;
import mod.azure.azexamples.entities.marine.MarineEntity;

/**
 * Entity entries. On 1.12.2 entity size is set in each entity's constructor, and spawn eggs come from
 * {@link EntityEntryBuilder#egg} (the vanilla spawn egg with the entity's id in NBT) instead of custom egg items.
 */
public final class EntityRegistry {

    static final List<EntityEntry> ENTRIES = new ArrayList<>();

    private static int nextId = 0;

    static {
        add(MarauderEntity.class, "marauder", 0xe9e2ed, 0x574f44);
        add(ManulEntity.class, "manul", 0xc38160, 0x3d362e);
        add(DoomHunterEntity.class, "doomhunter", 0x5a575a, 0x86472e);
        add(JuravenatorEntity.class, "juravenator", 0xc09e58, 0x574028);
        add(MarineEntity.class, "marine", 0xc09e58, 0x574028);
        add(GremlinEntity.class, "gremlin", 0x424242, 0x606060);
    }

    private EntityRegistry() {}

    private static <E extends Entity> void add(Class<E> type, String name, int primaryEgg, int secondaryEgg) {
        ENTRIES.add(
            EntityEntryBuilder.<E>create()
                .entity(type)
                .id(CommonMod.modResource(name), nextId++)
                .name(CommonStrings.MOD_ID + "." + name)
                .tracker(80, 3, true)
                .egg(primaryEgg, secondaryEgg)
                .build()
        );
    }
}
