package redboy72.weirdchanges.client.entity.portalEntity;

import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.Identifier;
import org.jetbrains.annotations.NotNull;
import redboy72.weirdchanges.WeirdChanges;
import redboy72.weirdchanges.client.entity.ModEntityModelLayers;
import redboy72.weirdchanges.entity.PortalEntity.HomePortalEntity;

public class HomePortalEntityRenderer extends MobRenderer<HomePortalEntity, HomePortalEntityRenderState, HomePortalEntityModel> {
    private static final Identifier TEXTURE = Identifier.fromNamespaceAndPath(WeirdChanges.MOD_ID, "textures/entity/home_portal.png");

    public HomePortalEntityRenderer(EntityRendererProvider.Context context) {
        super(context, new HomePortalEntityModel(context.bakeLayer(ModEntityModelLayers.HOME_PORTAL)), 0.375f); // 0.375 shadow radius
    }

    @Override
    public HomePortalEntityRenderState createRenderState() {
        return new HomePortalEntityRenderState();
    }

    @Override
    public @NotNull Identifier getTextureLocation(HomePortalEntityRenderState state) {
        return TEXTURE;
    }
}