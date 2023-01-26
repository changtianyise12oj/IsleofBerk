package com.GACMD.isleofberk.network.message;

import com.GACMD.isleofberk.entity.base.dragon.ADragonBase;
import com.GACMD.isleofberk.entity.base.dragon.ADragonBaseFlyingRideable;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class ControlMessageGoingDown {

    boolean isGoingDown;
    int dragonId;

    public ControlMessageGoingDown() {

    }

    public ControlMessageGoingDown(boolean isGoingDown, int dragonId) {
        this.isGoingDown = isGoingDown;
        this.dragonId = dragonId;
    }

    public static void encode(ControlMessageGoingDown message, FriendlyByteBuf buffer) {
        buffer.writeBoolean(message.isGoingDown);
        buffer.writeInt(message.dragonId);
    }

    public static ControlMessageGoingDown decode(FriendlyByteBuf buffer) {
        return new ControlMessageGoingDown(buffer.readBoolean(), buffer.readInt());
    }

    public static void handle(ControlMessageGoingDown message, Supplier<NetworkEvent.Context> contextSupplier) {
        NetworkEvent.Context context = contextSupplier.get();
        context.enqueueWork(() -> {
            ServerPlayer player = context.getSender();
            if (player != null) {
                Entity entity = player.level.getEntity(message.dragonId);
                if (entity instanceof ADragonBaseFlyingRideable dragon && player.getVehicle() == dragon && dragon.getOwner() == player) {
                    dragon.setIsGoingDown(message.isGoingDown);
                }
            }
        });
        context.setPacketHandled(true);

    }
}
