package com.GACMD.isleofberk.network.message;

import com.GACMD.isleofberk.entity.base.dragon.ADragonBaseFlyingRideable;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class MessageDragonFlapSounds {

    boolean shouldPlayFlapSounds;
    int dragonId;

    public MessageDragonFlapSounds() {

    }

    public MessageDragonFlapSounds(boolean shouldPlayFlapSounds, int dragonId) {
        this.shouldPlayFlapSounds = shouldPlayFlapSounds;
        this.dragonId = dragonId;
    }

    public static void encode(MessageDragonFlapSounds message, FriendlyByteBuf buffer) {
        buffer.writeBoolean(message.shouldPlayFlapSounds);
        buffer.writeInt(message.dragonId);
    }

    public static MessageDragonFlapSounds decode(FriendlyByteBuf buffer) {
        return new MessageDragonFlapSounds(buffer.readBoolean(), buffer.readInt());
    }

    public static void handle(MessageDragonFlapSounds message, Supplier<NetworkEvent.Context> contextSupplier) {
        NetworkEvent.Context context = contextSupplier.get();
        context.enqueueWork(() -> {
            ServerPlayer player = context.getSender();
            if (player != null) {
                Entity entity = player.level.getEntity(message.dragonId);
                if (entity instanceof ADragonBaseFlyingRideable dragon) {
                    dragon.setShouldPlayFlapping(message.shouldPlayFlapSounds);
                }
            }
        });
        context.setPacketHandled(true);

    }
}
