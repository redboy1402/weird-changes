package redboy72.weirdchanges.client.entity;

import net.fabricmc.fabric.api.client.rendering.v1.ModelLayerRegistry;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.resources.Identifier;
import redboy72.weirdchanges.WeirdChanges;
import redboy72.weirdchanges.client.entity.MiniGolemEntity.CustomModel;
import redboy72.weirdchanges.client.entity.MiniGolemEntity.MiniGolemEntityModel;
import redboy72.weirdchanges.client.entity.portalEntity.HomePortalEntityModel;

public class ModEntityModelLayers {
    public static final ModelLayerLocation MINI_GOLEM = createMain("mini_golem");
    public static final ModelLayerLocation HOME_PORTAL = createMain("home_portal");

    private static ModelLayerLocation createMain(String name) {
        return new ModelLayerLocation(Identifier.fromNamespaceAndPath(WeirdChanges.MOD_ID, name), "main");
    }

    public static void registerModelLayers() {
        WeirdChanges.LOGGER.info("Registering entity model layers...");
        ModelLayerRegistry.registerModelLayer(ModEntityModelLayers.MINI_GOLEM, MiniGolemEntityModel::getTexturedModelData);
        ModelLayerRegistry.registerModelLayer(ModEntityModelLayers.HOME_PORTAL, HomePortalEntityModel::createBodyLayer);
    }
}