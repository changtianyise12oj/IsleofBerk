package com.GACMD.isleofberk.common.entity.network;

import com.GACMD.isleofberk.IsleofBerk;
import com.GACMD.isleofberk.common.entity.network.message.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkDirection;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.simple.SimpleChannel;
import net.minecraftforge.server.ServerLifecycleHooks;

public class ControlNetwork {

    public static final String NETWORK_VERSION = "0.1.0";
    public static final SimpleChannel INSTANCE = NetworkRegistry.newSimpleChannel(new ResourceLocation(IsleofBerk.MOD_ID, "control"), () -> NETWORK_VERSION, NETWORK_VERSION::equals, NETWORK_VERSION::equals);

    public static void init() {
        INSTANCE.registerMessage(0, ControlMessageJumping.class, ControlMessageJumping::encode, ControlMessageJumping::decode, ControlMessageJumping::handle);
        INSTANCE.registerMessage(1, ControlMessageGoingDown.class, ControlMessageGoingDown::encode, ControlMessageGoingDown::decode, ControlMessageGoingDown::handle);
        INSTANCE.registerMessage(2, ControlMessageAbility.class, ControlMessageAbility::encode, ControlMessageAbility::decode, ControlMessageAbility::handle);
        INSTANCE.registerMessage(3, ControlMessageSECONDAbility.class, ControlMessageSECONDAbility::encode, ControlMessageSECONDAbility::decode, ControlMessageSECONDAbility::handle);
        INSTANCE.registerMessage(4, ClientMessageTameParticlesDragon.class, ClientMessageTameParticlesDragon::encode, ClientMessageTameParticlesDragon::decode, ClientMessageTameParticlesDragon::handle);
        INSTANCE.registerMessage(5, ClientMessageGuiDragon.class, ClientMessageGuiDragon::encode, ClientMessageGuiDragon::decode, ClientMessageGuiDragon::handle);
        INSTANCE.registerMessage(6, ControlMessageServer.class, ControlMessageServer::encode, ControlMessageServer::decode, ControlMessageServer::handle);
        INSTANCE.registerMessage(7, ClientMessageGuiDragonSit.class, ClientMessageGuiDragonSit::encode, ClientMessageGuiDragonSit::decode, ClientMessageGuiDragonSit::handle);
        INSTANCE.registerMessage(8, DragonRideMessage.class, DragonRideMessage::encode, DragonRideMessage::decode, DragonRideMessage::handle);
    }

    public static <MSG> void sendMSGToAll(MSG message) {
        for (ServerPlayer player : ServerLifecycleHooks.getCurrentServer().getPlayerList().getPlayers()) {
            INSTANCE.sendTo(message, player.connection.getConnection(), NetworkDirection.PLAY_TO_CLIENT);
        }
    }
}
