package redboy72.weirdchanges.item;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.Item;
import redboy72.weirdchanges.WeirdChanges;

public class ModItemIds {

    public static final ResourceKey<Item> SUSPICIOUS_SUBSTANCE = create("suspicious_substance");

    public static ResourceKey<Item> create(String name) {
        return ResourceKey.create(Registries.ITEM, Identifier.fromNamespaceAndPath(WeirdChanges.MOD_ID, name));
    }
}
