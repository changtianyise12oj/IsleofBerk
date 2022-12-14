package com.GACMD.isleofberk.common.entity.network.message;

import com.GACMD.isleofberk.common.entity.entities.dragons.terrible_terror.TerribleTerror;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class ControlMessageTerribleTerrorAbility {

    boolean abilityHeld;

    public ControlMessageTerribleTerrorAbility() {

    }

    public ControlMessageTerribleTerrorAbility(boolean abilityHeld) {
        this.abilityHeld = abilityHeld;
    }

    public static void encode(ControlMessageTerribleTerrorAbility message, FriendlyByteBuf buffer) {
        buffer.writeBoolean(message.abilityHeld);
    }

    public static ControlMessageTerribleTerrorAbility decode(FriendlyByteBuf buffer) {
        return new ControlMessageTerribleTerrorAbility(buffer.readBoolean());
    }

    public static void handle(ControlMessageTerribleTerrorAbility message, Supplier<NetworkEvent.Context> contextSupplier) {
        NetworkEvent.Context context = contextSupplier.get();
        context.enqueueWork(() -> {
            ServerPlayer player = context.getSender();

            if (player != null) {
                if (player.getPassengers().size() > 0 && player.getPassengers().get(0) instanceof TerribleTerror terribleTerror) {
                    terribleTerror.setIsUsingAbility(message.abilityHeld);
                }
                if (player.getPassengers().size() > 1 && player.getPassengers().get(1) instanceof TerribleTerror terribleTerror) {
                    terribleTerror.setIsUsingAbility(message.abilityHeld);
                }
                if (player.getPassengers().size() > 2 && player.getPassengers().get(2) instanceof TerribleTerror terribleTerror) {
                    terribleTerror.setIsUsingAbility(message.abilityHeld);
                }

            }
        });
        context.setPacketHandled(true);

    }
}
