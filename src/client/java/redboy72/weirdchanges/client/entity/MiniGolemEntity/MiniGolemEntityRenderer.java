package redboy72.weirdchanges.client.entity.MiniGolemEntity;

import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.Identifier;
import org.jetbrains.annotations.NotNull;
import redboy72.weirdchanges.WeirdChanges;
import redboy72.weirdchanges.client.entity.ModEntityModelLayers;
import redboy72.weirdchanges.entity.MiniGolemEntity.MiniGolemEntity;

public class MiniGolemEntityRenderer extends MobRenderer<MiniGolemEntity, MiniGolemEntityRenderState, MiniGolemEntityModel> {
    private static final Identifier TEXTURE = Identifier.fromNamespaceAndPath(WeirdChanges.MOD_ID, "textures/entity/mini_golem.png");

    public MiniGolemEntityRenderer(EntityRendererProvider.Context context) {
        super(context, new MiniGolemEntityModel(context.bakeLayer(ModEntityModelLayers.MINI_GOLEM)), 0.375f); // 0.375 shadow radius
    }

    @Override
    public MiniGolemEntityRenderState createRenderState() {
        return new MiniGolemEntityRenderState();
    }

    @Override
    public @NotNull Identifier getTextureLocation(MiniGolemEntityRenderState state) {
        return TEXTURE;
    }
}