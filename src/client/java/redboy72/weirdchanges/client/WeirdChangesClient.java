package redboy72.weirdchanges.client;

import net.fabricmc.api.ClientModInitializer;
import net.minecraft.client.renderer.entity.EntityRenderers;
import redboy72.weirdchanges.WeirdChanges;
import redboy72.weirdchanges.client.entity.ModEntityModelLayers;
import redboy72.weirdchanges.entity.ModEntityTypes;
import redboy72.weirdchanges.client.entity.MiniGolemEntity.MiniGolemEntityRenderer;

public class WeirdChangesClient implements ClientModInitializer {
	@Override
	public void onInitializeClient() {
		WeirdChanges.LOGGER.warn("Client Logged");
		ModEntityModelLayers.registerModelLayers();
		EntityRenderers.register(ModEntityTypes.MINI_GOLEM, MiniGolemEntityRenderer::new);
	}
}