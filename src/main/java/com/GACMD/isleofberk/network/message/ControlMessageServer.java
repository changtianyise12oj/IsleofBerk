package com.GACMD.isleofberk.network.message;

import com.GACMD.isleofberk.entity.base.dragon.ADragonRideableUtility;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class ControlMessageServer {

    double x, y, z;
    int dragonId;

    public ControlMessageServer() {

    }

    public ControlMessageServer(double x, double y, double z, int dragonId) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.dragonId = dragonId;
    }

    public static void encode(ControlMessageServer message, FriendlyByteBuf buffer) {
        buffer.writeDouble(message.x);
        buffer.writeDouble(message.y);
        buffer.writeDouble(message.z);
        buffer.writeInt(message.dragonId);
    }

    public static ControlMessageServer decode(FriendlyByteBuf buffer) {
        return new ControlMessageServer(buffer.readDouble(), buffer.readDouble(), buffer.readDouble(), buffer.readInt());
    }

    public static void handle(ControlMessageServer message, Supplier<NetworkEvent.Context> contextSupplier) {
        NetworkEvent.Context context = contextSupplier.get();
        context.enqueueWork(() -> {
            ServerPlayer player = context.getSender();

            if (player != null) {
                Entity entity = player.level.getEntity(message.dragonId);
                if (entity instanceof ADragonRideableUtility dragon && player.getVehicle() == dragon && dragon.getOwner() == player) {
                    dragon.setPos(new Vec3(message.x, message.y, message.z));
                }
            }
        });
        context.setPacketHandled(true);
    }
}

