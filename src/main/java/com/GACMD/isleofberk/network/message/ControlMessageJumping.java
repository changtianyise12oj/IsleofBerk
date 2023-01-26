package com.GACMD.isleofberk.network.message;

import com.GACMD.isleofberk.entity.base.dragon.ADragonBaseFlyingRideable;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class ControlMessageJumping {

    boolean isGoingUp;
    int dragonId;

    public ControlMessageJumping() {

    }

    public ControlMessageJumping(boolean isGoingUp, int dragonId) {
        this.isGoingUp = isGoingUp;
        this.dragonId = dragonId;
    }

    public static void encode(ControlMessageJumping message, FriendlyByteBuf buffer) {
        buffer.writeBoolean(message.isGoingUp);
        buffer.writeInt(message.dragonId);
    }

    public static ControlMessageJumping decode(FriendlyByteBuf buffer) {
        return new ControlMessageJumping(buffer.readBoolean(), buffer.readInt());
    }

    public static void handle(ControlMessageJumping message, Supplier<NetworkEvent.Context> contextSupplier) {
        NetworkEvent.Context context = contextSupplier.get();
        context.enqueueWork(() -> {
            ServerPlayer player = context.getSender();

            if (player != null) {
                Entity entity = player.level.getEntity(message.dragonId);
                if (entity instanceof ADragonBaseFlyingRideable dragon && player.getVehicle() == dragon && dragon.getOwner() == player) {
                    dragon.setIsGoingUp(message.isGoingUp);
                }
            }
        });
        context.setPacketHandled(true);

    }
}
