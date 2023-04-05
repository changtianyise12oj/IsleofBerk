package com.GACMD.isleofberk.network.message;

import com.GACMD.isleofberk.network.message.util.ClientPacketHandlerClass;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.LogicalSide;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class MessageShockParticle {
    public int id;

    public MessageShockParticle(int id) {
        this.id = id;
    }

    public void encode(FriendlyByteBuf buf) {
        buf.writeInt(this.id);
    }

    public static MessageShockParticle decode(FriendlyByteBuf buf) {
        int id = buf.readInt();
        return new MessageShockParticle(id);
    }

    public static void handle(MessageShockParticle message, Supplier<NetworkEvent.Context> ctx) {
        NetworkEvent.Context context = ctx.get();
        if(context.getDirection().getReceptionSide() == LogicalSide.CLIENT) {
            context.enqueueWork(() -> {
                DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> () -> ClientPacketHandlerClass.handleSpawnShockParticles(message, ctx));
            });
            context.setPacketHandled(true);
        }
    }
}
