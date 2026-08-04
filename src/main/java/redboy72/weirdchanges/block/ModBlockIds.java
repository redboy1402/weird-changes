package redboy72.weirdchanges.block;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import redboy72.weirdchanges.WeirdChanges;

public class ModBlockIds {

    public static final ResourceKey<Block> SUSPICIOUS_SUBSTANCE = create("suspicious_substance");

    public static ResourceKey<Block> create(String name) {
        return ResourceKey.create(Registries.BLOCK, Identifier.fromNamespaceAndPath(WeirdChanges.MOD_ID, name));
    }
}
