package com.GACMD.isleofberk.common.entity.network.message;

import com.GACMD.isleofberk.common.entity.entities.dragons.terrible_terror.TerribleTerror;
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
//            if (player.getVehicle() instanceof ADragonBase dragon && dragon.getOwner() == player && !(dragon instanceof TerribleTerror)) {
//                dragon.setIsUsingAbility(message.abilityHeld);
////                System.out.println("ability held " + message.abilityHeld);
////                System.out.println("message using ability " + dragon.isUsingAbility());
//            }

            if (player != null) {
                if (player.getPassengers().size() > 0) {
                    if (player.getPassengers().get(0) instanceof TerribleTerror terribleTerror) {
                        terribleTerror.setIsUsingAbility(message.abilityHeld);

                        if (message.abilityHeld) {
                            System.out.println("ability held " + message.abilityHeld);
                            System.out.println("message using ability " + terribleTerror.isUsingAbility());
//                System.out.println(terribleTerror);
                        }
                    }
                    if (player.getPassengers().get(1) instanceof TerribleTerror terribleTerror) {
                        terribleTerror.setIsUsingAbility(message.abilityHeld);

                        if (message.abilityHeld) {
                            System.out.println("ability held " + message.abilityHeld);
                            System.out.println("message using ability " + terribleTerror.isUsingAbility());
//                System.out.println(terribleTerror);
                        }
                    }
                    if (player.getPassengers().get(2) instanceof TerribleTerror terribleTerror) {
                        terribleTerror.setIsUsingAbility(message.abilityHeld);

                        if (message.abilityHeld) {
                            System.out.println("ability held " + message.abilityHeld);
                            System.out.println("message using ability " + terribleTerror.isUsingAbility());
//                System.out.println(terribleTerror);
                        }
                    }
                }
            }
        });
        context.setPacketHandled(true);

    }
}
