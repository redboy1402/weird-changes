package redboy72.weirdchanges.world.gen;

import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectors;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.entity.variant.SpawnCondition;
import net.minecraft.world.entity.variant.SpawnConditions;
import redboy72.weirdchanges.entity.ModEntityTypes;

public class ModEntitySpawns {
    public static void addSpawns(){
        BiomeModifications.addSpawn(
                BiomeSelectors.all(),
                MobCategory.MONSTER,
                ModEntityTypes.MINI_GOLEM,
                10,
                1,
                2);

        BiomeModifications.addSpawn(
                BiomeSelectors.all(),
                MobCategory.AMBIENT,
                ModEntityTypes.HOME_PORTAL,
                200,
                1,
                1
        );
    }
}
