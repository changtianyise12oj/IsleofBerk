package com.GACMD.isleofberk.common.entity.network.message;

import com.GACMD.isleofberk.common.entity.entities.base.ADragonRideableUtility;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class ControlMessageServer {

    double x, y, z;

    public ControlMessageServer() {

    }

    public ControlMessageServer(double x, double y, double z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public static void encode(ControlMessageServer message, FriendlyByteBuf buffer) {
        buffer.writeDouble(message.x);
        buffer.writeDouble(message.y);
        buffer.writeDouble(message.z);
    }

    public static ControlMessageServer decode(FriendlyByteBuf buffer) {
        return new ControlMessageServer(buffer.readDouble(), buffer.readDouble(), buffer.readDouble());
    }

    public static void handle(ControlMessageServer message, Supplier<NetworkEvent.Context> contextSupplier) {
        NetworkEvent.Context context = contextSupplier.get();
        context.enqueueWork(() -> {
            ServerPlayer player = context.getSender();
            if (player.getVehicle() instanceof ADragonRideableUtility) {
                ADragonRideableUtility dragon = (ADragonRideableUtility) player.getVehicle();
                dragon.setPos(new Vec3(message.x, message.y, message.z));
            }
        });
        context.setPacketHandled(true);

    }
}
