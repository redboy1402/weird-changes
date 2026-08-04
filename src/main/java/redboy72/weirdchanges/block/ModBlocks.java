package redboy72.weirdchanges.block;

import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import redboy72.weirdchanges.WeirdChanges;

import java.util.function.Function;

public class ModBlocks {

    public static Block register(ResourceKey<Block> blockKey, Function<Block.Properties, Block> itemFactory, Block.Properties settings) {
        Block block = itemFactory.apply(settings.setId(blockKey));

        Registry.register(BuiltInRegistries.BLOCK, blockKey, block);
        return block;
    }

    public static void initialize(){
        WeirdChanges.LOGGER.info("Registering Mod Blocks");
    }
}
