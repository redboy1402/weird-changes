package redboy72.weirdchanges.networking.blockPalette;

import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.ItemStack;
import redboy72.weirdchanges.WeirdChanges;

public record ServerBoundPickBlockPayload(ItemStack itemStack) implements CustomPacketPayload {
    public static final Identifier PICK_BLOCK_PAYLOAD_ID = Identifier.fromNamespaceAndPath(WeirdChanges.MOD_ID, "pick_block");
    public static final CustomPacketPayload.Type<ServerBoundPickBlockPayload> TYPE = new CustomPacketPayload.Type<>(PICK_BLOCK_PAYLOAD_ID);

    public static final StreamCodec<RegistryFriendlyByteBuf, ServerBoundPickBlockPayload> CODEC = StreamCodec.composite(
            ItemStack.STREAM_CODEC,
            ServerBoundPickBlockPayload::itemStack,
            ServerBoundPickBlockPayload::new
    );

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}