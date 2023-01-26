package com.GACMD.isleofberk.network.message;

import com.GACMD.isleofberk.entity.base.dragon.ADragonBase;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class ControlMessageSECONDAbility {

    boolean abilityHeld;
    int dragonId;

    public ControlMessageSECONDAbility() {

    }

    public ControlMessageSECONDAbility(boolean abilityHeld, int dragonId) {
        this.abilityHeld = abilityHeld;
        this.dragonId = dragonId;
    }

    public static void encode(ControlMessageSECONDAbility message, FriendlyByteBuf buffer) {
        buffer.writeBoolean(message.abilityHeld);
        buffer.writeInt(message.dragonId);
    }

    public static ControlMessageSECONDAbility decode(FriendlyByteBuf buffer) {
        return new ControlMessageSECONDAbility(buffer.readBoolean(), buffer.readInt());
    }

    public static void handle(ControlMessageSECONDAbility message, Supplier<NetworkEvent.Context> contextSupplier) {
        NetworkEvent.Context context = contextSupplier.get();
        context.enqueueWork(() -> {
            ServerPlayer player = context.getSender();
            if (player != null) {
                Entity entity = player.level.getEntity(message.dragonId);
                if (entity instanceof ADragonBase dragon && player.getVehicle() == dragon && dragon.getOwner() == player) {
                    dragon.setIsUsingSECONDAbility(message.abilityHeld);
                }
            }
        });
        context.setPacketHandled(true);

    }
}
