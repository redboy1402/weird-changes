package redboy72.weirdchanges.client;

import com.mojang.blaze3d.vertex.BufferBuilder;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.rendering.v1.hud.HudElement;
import net.fabricmc.fabric.api.client.rendering.v1.hud.HudElementRegistry;
import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.EntityRenderers;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.util.Mth;
import org.joml.Matrix3x2fStack;
import redboy72.weirdchanges.WeirdChanges;
import redboy72.weirdchanges.client.control.Keys;
import redboy72.weirdchanges.client.entity.ModEntityModelLayers;
import redboy72.weirdchanges.client.entity.portalEntity.HomePortalEntityRenderer;
import redboy72.weirdchanges.client.screen.BlockPaletteMenuScreen;
import redboy72.weirdchanges.entity.ModEntityTypes;
import redboy72.weirdchanges.client.entity.MiniGolemEntity.MiniGolemEntityRenderer;
import redboy72.weirdchanges.networking.Packets;

public class WeirdChangesClient implements ClientModInitializer {
	private float totalTickProgress;
	private BufferBuilder bufferBuilder;
	private BlockPaletteMenuScreen blockPaletteMenuScreen;

	@Override
	public void onInitializeClient() {
		WeirdChanges.LOGGER.warn("Client Logged");
		ModEntityModelLayers.registerModelLayers();
		EntityRenderers.register(ModEntityTypes.MINI_GOLEM, MiniGolemEntityRenderer::new);
		EntityRenderers.register(ModEntityTypes.HOME_PORTAL, HomePortalEntityRenderer::new);
		HudElementRegistry.addLast(Identifier.fromNamespaceAndPath(WeirdChanges.MOD_ID, "last_element"), this.hudLayer());
//		var byteBufferBuilder = new ByteBufferBuilder(0);
		Keys.init();

		PayloadTypeRegistry.clientboundPlay().register(Packets.ClientboundSummonLightningPayload.TYPE, Packets.ClientboundSummonLightningPayload.CODEC);

		blockPaletteMenuScreen = new BlockPaletteMenuScreen();
		ClientTickEvents.END_CLIENT_TICK.register(client -> {
			while (Keys.BLOCK_PALETTE.getKeyMapping().consumeClick()){
				if (client.player != null ){
					Minecraft.getInstance().setScreenAndShow(blockPaletteMenuScreen);
				}
			}
		});


	}

	private HudElement hudLayer() {
		return (graphics, deltaTracker) -> {
			Matrix3x2fStack matrices = graphics.pose();

			this.totalTickProgress += deltaTracker.getGameTimeDeltaPartialTick(true);

			matrices.pushMatrix();
			float scaleAmount = Mth.sin(this.totalTickProgress / 10F) / 2F + 1.5F;

			matrices.scale(scaleAmount, scaleAmount);
			matrices.scale(1 / scaleAmount, 1/scaleAmount);
			matrices.translate(60F, 60F);
			float rotationAmount = this.totalTickProgress / 50F % 360;
			matrices.rotate(rotationAmount);
			matrices.translate(-20f, -40f);
			matrices.popMatrix();
		};
	}
}