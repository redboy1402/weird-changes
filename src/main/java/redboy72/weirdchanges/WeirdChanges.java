package redboy72.weirdchanges;

import net.fabricmc.api.ModInitializer;
import net.minecraft.resources.Identifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import redboy72.weirdchanges.block.ModBlocks;
import redboy72.weirdchanges.entity.ModEntityTypes;
import redboy72.weirdchanges.item.ModItems;
import redboy72.weirdchanges.world.gen.ModEntitySpawns;

public class WeirdChanges implements ModInitializer {
	public static final String MOD_ID = "weird-changes";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitialize() {
		LOGGER.info("Initializing Weird Changes");
		ModItems.initialize();
		ModBlocks.initialize();
		ModEntityTypes.initialize();
		ModEntitySpawns.addSpawns();
	}

	public static Identifier id(String path) {
		return Identifier.fromNamespaceAndPath(MOD_ID, path);
	}
}
