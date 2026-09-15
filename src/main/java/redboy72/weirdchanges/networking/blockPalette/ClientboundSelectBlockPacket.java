package redboy72.weirdchanges.networking.blockPalette;

import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.ItemStack;
import redboy72.weirdchanges.WeirdChanges;

public record ClientboundSelectBlockPacket(ItemStack item) implements CustomPacketPayload {
    public static final Identifier SELECT_BLOCK_PACKET = Identifier.fromNamespaceAndPath(WeirdChanges.MOD_ID, "select_block");

    public static final CustomPacketPayload.Type<ClientboundSelectBlockPacket> TYPE = new CustomPacketPayload.Type<>(SELECT_BLOCK_PACKET);

    public static final StreamCodec<RegistryFriendlyByteBuf, ClientboundSelectBlockPacket> CODEC = StreamCodec.composite(ItemStack.STREAM_CODEC, ClientboundSelectBlockPacket::item, ClientboundSelectBlockPacket::new);

    @Override
    public CustomPacketPayload.Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}
