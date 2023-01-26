package com.GACMD.isleofberk.network.message;

import com.GACMD.isleofberk.entity.base.dragon.ADragonBase;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class ClientMessageGuiDragonSit {
    int entityId;
    boolean sit;

    public ClientMessageGuiDragonSit(int entityId, boolean sit) {
        this.entityId = entityId;
        this.sit = sit;
    }

    public static void encode(ClientMessageGuiDragonSit message, FriendlyByteBuf buffer) {
        buffer.writeInt(message.entityId);
        buffer.writeBoolean(message.sit);
    }

    public static ClientMessageGuiDragonSit decode(FriendlyByteBuf buffer) {
        return new ClientMessageGuiDragonSit(buffer.readInt(), buffer.readBoolean());
    }

    public static void handle(ClientMessageGuiDragonSit message, Supplier<NetworkEvent.Context> ctx) {
        NetworkEvent.Context context = ctx.get();
        context.enqueueWork(() -> {
            ServerPlayer player = context.getSender();
            if (player != null) {
                Entity entity = player.level.getEntity(message.entityId);
                if (entity instanceof ADragonBase dragon && !dragon.isVehicle()) {
                    if (message.sit) dragon.setIsDragonSitting(!dragon.isDragonSitting());
                }
            }
        });
        context.setPacketHandled(true);
    }
}