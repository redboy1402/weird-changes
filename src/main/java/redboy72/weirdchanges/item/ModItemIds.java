package redboy72.weirdchanges.item;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.Item;
import redboy72.weirdchanges.WeirdChanges;

public class ModItemIds {
    public static ResourceKey<Item> create(String id) {
        return ResourceKey.create(Registries.ITEM, Identifier.fromNamespaceAndPath(WeirdChanges.MOD_ID, id));
    }
}

