package redboy72.weirdchanges;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import redboy72.weirdchanges.block.ModBlocks;
import redboy72.weirdchanges.entity.ModEntityTypes;
import redboy72.weirdchanges.item.ModItems;
import redboy72.weirdchanges.networking.blockPalette.ClientboundSelectBlockPacket;
import redboy72.weirdchanges.networking.blockPalette.ServerBoundPickBlockPayload;
import redboy72.weirdchanges.world.gen.ModEntitySpawns;

public class WeirdChanges implements ModInitializer {
	public static final String MOD_ID = "weird-changes";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	public enum PaletteItems {
		STONES;

		private final TagKey<Item> tag;
		public TagKey<Item> getTag() {return this.tag; }

		PaletteItems() {
			this.tag = TagKey.create(
					Registries.ITEM,
					Identifier.fromNamespaceAndPath(MOD_ID, "palette-items/" + name().toLowerCase())
			);
		}
	}

	@Override
	public void onInitialize() {
		LOGGER.info("Initializing Weird Changes");
		ModItems.initialize();
		ModBlocks.initialize();
		ModEntityTypes.initialize();
		ModEntitySpawns.addSpawns();
		PayloadTypeRegistry.serverboundPlay().register(ClientboundSelectBlockPacket.TYPE, ClientboundSelectBlockPacket.CODEC);
		PayloadTypeRegistry.serverboundPlay().register(ServerBoundPickBlockPayload.TYPE, ServerBoundPickBlockPayload.CODEC);
		ServerPlayNetworking.registerGlobalReceiver(ServerBoundPickBlockPayload.TYPE, (payload, ctx) -> {
			ItemStack itemStack = payload.itemStack();
			var player = ctx.player();
			player.getInventory().setSelectedItem(itemStack);
		});
	}

	public static Identifier id(String path) {
		return Identifier.fromNamespaceAndPath(MOD_ID, path);
	}
}
