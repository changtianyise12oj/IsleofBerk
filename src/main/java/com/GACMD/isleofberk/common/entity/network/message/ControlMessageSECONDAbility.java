package com.GACMD.isleofberk.common.entity.network.message;

import com.GACMD.isleofberk.common.entity.entities.base.dragon.ADragonBase;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class ControlMessageSECONDAbility {

    boolean abilityHeld;

    public ControlMessageSECONDAbility() {

    }

    public ControlMessageSECONDAbility(boolean abilityHeld) {
        this.abilityHeld = abilityHeld;
    }

    public static void encode(ControlMessageSECONDAbility message, FriendlyByteBuf buffer) {
        buffer.writeBoolean(message.abilityHeld);
    }

    public static ControlMessageSECONDAbility decode(FriendlyByteBuf buffer) {
        return new ControlMessageSECONDAbility(buffer.readBoolean());
    }

    public static void handle(ControlMessageSECONDAbility message, Supplier<NetworkEvent.Context> contextSupplier) {
        NetworkEvent.Context context = contextSupplier.get();
        context.enqueueWork(() -> {
            ServerPlayer player = context.getSender();
            if (player.getVehicle() instanceof ADragonBase dragon && dragon.getOwner() == player) {
                dragon.setIsUsingSECONDAbility(message.abilityHeld);
            }
        });
        context.setPacketHandled(true);

    }
}
