package com.GACMD.isleofberk.common.entity.network.message;

import com.GACMD.isleofberk.common.entity.entities.base.dragon.ADragonBaseFlyingRideable;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class ControlMessageJumping {

    boolean isGoingUp;

    public ControlMessageJumping() {

    }

    public ControlMessageJumping(boolean isGoingUp) {
        this.isGoingUp = isGoingUp;
    }

    public static void encode(ControlMessageJumping message, FriendlyByteBuf buffer) {
        buffer.writeBoolean(message.isGoingUp);
    }

    public static ControlMessageJumping decode(FriendlyByteBuf buffer) {
        return new ControlMessageJumping(buffer.readBoolean());
    }

    public static void handle(ControlMessageJumping message, Supplier<NetworkEvent.Context> contextSupplier) {
        NetworkEvent.Context context = contextSupplier.get();
        context.enqueueWork(() -> {
            ServerPlayer player = context.getSender();
            if (player != null) {
                if (player.getVehicle() instanceof ADragonBaseFlyingRideable dragon) {
                    dragon.setIsGoingUp(message.isGoingUp);
                }
            }
        });
        context.setPacketHandled(true);

    }
}
