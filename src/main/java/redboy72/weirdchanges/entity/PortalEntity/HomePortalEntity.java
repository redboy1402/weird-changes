package redboy72.weirdchanges.entity.PortalEntity;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.level.Level;
import org.jspecify.annotations.Nullable;
import redboy72.weirdchanges.WeirdChanges;

import java.awt.*;
import java.util.List;

public class HomePortalEntity extends Mob {

    public HomePortalEntity(EntityType<? extends HomePortalEntity> type, Level level) {
        super(type, level);
        this.noPhysics = true;
        this.setNoAi(true);
    }

    public static AttributeSupplier.Builder createAttributes(){
        return Mob.createMobAttributes()
                .add(Attributes.GRAVITY, 0);
    }

    @Override
    public boolean canBeLeashed() {
        return false;
    }

    @Override
    public void setCustomName(final @Nullable Component name) { }

    @Override
    public void tick(){
        if (this.firstTick){
            WeirdChanges.LOGGER.info("firstTick");
            LightningBolt bolt = EntityTypes.LIGHTNING_BOLT.create(this.level(), EntitySpawnReason.TRIGGERED);
            assert bolt != null;
            this.level().addFreshEntity(bolt);
        }

        super.tick();
        List<ServerPlayer> players = this.level().getEntitiesOfClass(ServerPlayer.class, this.getBoundingBox());
        for (ServerPlayer player : players){
            if (player.getRespawnConfig() == null) {
                return;
            }
            var dest = player.getRespawnConfig().respawnData().globalPos().pos();
            player.teleportTo(dest.getX(), dest.getY(), dest.getZ());
        }
    }

}