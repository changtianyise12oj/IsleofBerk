package com.GACMD.isleofberk.network.message;

import com.GACMD.isleofberk.entity.dragons.stinger.Stinger;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class MessageStingerMovingForRam {

    boolean canCauseRamDamage;
    int dragonId;

    public MessageStingerMovingForRam() {

    }

    public MessageStingerMovingForRam(boolean canCauseRamDamage, int dragonId) {
        this.canCauseRamDamage = canCauseRamDamage;
        this.dragonId = dragonId;
    }

    public static void encode(MessageStingerMovingForRam message, FriendlyByteBuf buffer) {
        buffer.writeBoolean(message.canCauseRamDamage);
        buffer.writeInt(message.dragonId);
    }

    public static MessageStingerMovingForRam decode(FriendlyByteBuf buffer) {
        return new MessageStingerMovingForRam(buffer.readBoolean(), buffer.readInt());
    }

    public static void handle(MessageStingerMovingForRam message, Supplier<NetworkEvent.Context> contextSupplier) {
        NetworkEvent.Context context = contextSupplier.get();
        context.enqueueWork(() -> {
            ServerPlayer player = context.getSender();
            if (player != null) {
                if (player.getVehicle() instanceof Stinger stinger && stinger.getOwner() == player) {
                    stinger.setIsRammingDamageTrue(message.canCauseRamDamage);
                }
            }
        });
        context.setPacketHandled(true);

    }
}
