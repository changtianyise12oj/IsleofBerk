package com.GACMD.isleofberk.network.message;

import com.GACMD.isleofberk.entity.base.dragon.ADragonBase;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class ControlMessageAbility {

    boolean abilityHeld;
    int dragonId;

    public ControlMessageAbility() {

    }

    public ControlMessageAbility(boolean abilityHeld, int dragonId) {
        this.abilityHeld = abilityHeld;
        this.dragonId = dragonId;
    }

    public static void encode(ControlMessageAbility message, FriendlyByteBuf buffer) {
        buffer.writeBoolean(message.abilityHeld);
        buffer.writeInt(message.dragonId);
    }

    public static ControlMessageAbility decode(FriendlyByteBuf buffer) {
        return new ControlMessageAbility(buffer.readBoolean(), buffer.readInt());
    }

    public static void handle(ControlMessageAbility message, Supplier<NetworkEvent.Context> contextSupplier) {
        NetworkEvent.Context context = contextSupplier.get();
        context.enqueueWork(() -> {
            ServerPlayer player = context.getSender();
            if (player != null) {
                Entity entity = player.level.getEntity(message.dragonId);
                if (entity instanceof ADragonBase dragon && player.getVehicle() == dragon && dragon.getOwner() == player) {
                    dragon.setIsUsingAbility(message.abilityHeld);
                }
            }
        });
        context.setPacketHandled(true);

    }
}
