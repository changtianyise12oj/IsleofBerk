package com.GACMD.isleofberk.common.entity.network.message;

import com.GACMD.isleofberk.common.entity.entities.base.ADragonBase;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class ControlMessageAbility {

    boolean abilityHeld;

    public ControlMessageAbility() {

    }

    public ControlMessageAbility(boolean abilityHeld) {
        this.abilityHeld = abilityHeld;
    }

    public static void encode(ControlMessageAbility message, FriendlyByteBuf buffer) {
        buffer.writeBoolean(message.abilityHeld);
    }

    public static ControlMessageAbility decode(FriendlyByteBuf buffer) {
        return new ControlMessageAbility(buffer.readBoolean());
    }

    public static void handle(ControlMessageAbility message, Supplier<NetworkEvent.Context> contextSupplier) {
        NetworkEvent.Context context = contextSupplier.get();
        context.enqueueWork(() -> {
            ServerPlayer player = context.getSender();
            if (player.getVehicle() instanceof ADragonBase dragon && dragon.getOwner() == player) {
                dragon.setIsUsingAbility(message.abilityHeld);
            }
        });
        context.setPacketHandled(true);

    }
}
