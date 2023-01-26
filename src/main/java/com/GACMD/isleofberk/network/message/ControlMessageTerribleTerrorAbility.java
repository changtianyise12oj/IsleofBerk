package com.GACMD.isleofberk.network.message;

import com.GACMD.isleofberk.entity.base.dragon.ADragonBase;
import com.GACMD.isleofberk.entity.dragons.terrible_terror.TerribleTerror;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class ControlMessageTerribleTerrorAbility {

    boolean abilityHeld;
    int dragonId;

    public ControlMessageTerribleTerrorAbility() {

    }

    public ControlMessageTerribleTerrorAbility(boolean abilityHeld, int dragonId) {
        this.abilityHeld = abilityHeld;
        this.dragonId = dragonId;
    }

    public static void encode(ControlMessageTerribleTerrorAbility message, FriendlyByteBuf buffer) {
        buffer.writeBoolean(message.abilityHeld);
        buffer.writeInt(message.dragonId);
    }

    public static ControlMessageTerribleTerrorAbility decode(FriendlyByteBuf buffer) {
        return new ControlMessageTerribleTerrorAbility(buffer.readBoolean(), buffer.readInt());
    }

    public static void handle(ControlMessageTerribleTerrorAbility message, Supplier<NetworkEvent.Context> contextSupplier) {
        NetworkEvent.Context context = contextSupplier.get();
        context.enqueueWork(() -> {
            ServerPlayer player = context.getSender();

            if (player != null) {
                Entity entity = player.level.getEntity(message.dragonId);
                if (entity instanceof ADragonBase dragon && dragon.getOwner() == player) {
                    if (player.getPassengers().size() > 0 && player.getPassengers().get(0) == dragon && dragon instanceof TerribleTerror terribleTerror) {
                        terribleTerror.setIsUsingAbility(message.abilityHeld);
                    }
                    if (player.getPassengers().size() > 1 && player.getPassengers().get(1) == dragon && dragon instanceof TerribleTerror terribleTerror) {
                        terribleTerror.setIsUsingAbility(message.abilityHeld);
                    }
                    if (player.getPassengers().size() > 2 && player.getPassengers().get(2) == dragon && dragon instanceof TerribleTerror terribleTerror) {
                        terribleTerror.setIsUsingAbility(message.abilityHeld);
                    }
                }
            }
        });
        context.setPacketHandled(true);

    }
}
