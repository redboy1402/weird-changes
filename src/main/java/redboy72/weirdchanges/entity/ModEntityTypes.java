package redboy72.weirdchanges.entity;

import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import redboy72.weirdchanges.WeirdChanges;
import redboy72.weirdchanges.entity.MiniGolemEntity.MiniGolemEntity;
import redboy72.weirdchanges.entity.PortalEntity.HomePortalEntity;

public class ModEntityTypes {
    public static final EntityType<MiniGolemEntity> MINI_GOLEM = register(
            "mini_golem",
            EntityType.Builder.<MiniGolemEntity>of(MiniGolemEntity::new, MobCategory.MONSTER)
                    .sized(0.75f, 1.75f)
    );
    public static final EntityType<HomePortalEntity> HOME_PORTAL = register(
            "home_portal",
            EntityType.Builder.<HomePortalEntity>of(HomePortalEntity::new, MobCategory.AMBIENT)
                    .sized(2f, 5f)
    );

    private static <T extends Entity> EntityType<T> register(String name, EntityType.Builder<T> builder) {
        ResourceKey<EntityType<?>> key = ResourceKey.create(Registries.ENTITY_TYPE, Identifier.fromNamespaceAndPath(WeirdChanges.MOD_ID, name));
        return Registry.register(BuiltInRegistries.ENTITY_TYPE, key, builder.build(key));
    }

    public static void initialize() {
        registerModEntityTypes();
        registerAttributes();
    }

    public static void registerModEntityTypes() {
        WeirdChanges.LOGGER.info("Registering EntityTypes for " + WeirdChanges.MOD_ID);

    }

    public static void registerAttributes() {
        FabricDefaultAttributeRegistry.register(MINI_GOLEM, MiniGolemEntity.createCubeAttributes());
        FabricDefaultAttributeRegistry.register(HOME_PORTAL, HomePortalEntity.createAttributes());
    }
}
