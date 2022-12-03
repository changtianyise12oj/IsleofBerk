package com.GACMD.isleofberk.common.entity.network.message;

import com.GACMD.isleofberk.common.entity.entities.base.ADragonBase;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class ClientMessageGuiDragonSit {
    private int entityId;
    private boolean sit;

    public ClientMessageGuiDragonSit(int entityId, boolean sit) {
        this.entityId = entityId;
        this.sit=sit;
    }

    public static void encode(ClientMessageGuiDragonSit message, FriendlyByteBuf buffer) {
        buffer.writeInt(message.entityId);
    }

    public static ClientMessageGuiDragonSit decode(FriendlyByteBuf buffer) {
        return new ClientMessageGuiDragonSit(buffer.readInt(), buffer.readBoolean());
    }

    public static void handle(ClientMessageGuiDragonSit message, Supplier<NetworkEvent.Context> ctx) {
        NetworkEvent.Context context = ctx.get();
        context.enqueueWork(() -> {
            ServerPlayer player = context.getSender();
            ADragonBase dragon = (ADragonBase) player.level.getEntity(message.entityId);
            if (dragon != null && !dragon.isVehicle()) {
                if(message.sit) dragon.setIsDragonSitting(!dragon.isDragonSitting());
            }
        });
        context.setPacketHandled(true);
    }
}