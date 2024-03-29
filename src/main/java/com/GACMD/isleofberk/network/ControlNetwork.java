package com.GACMD.isleofberk.network;

import com.GACMD.isleofberk.IsleofBerk;
import com.GACMD.isleofberk.network.message.*;
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
        INSTANCE.registerMessage(3, ControlMessageTerribleTerrorAbility.class, ControlMessageTerribleTerrorAbility::encode, ControlMessageTerribleTerrorAbility::decode, ControlMessageTerribleTerrorAbility::handle);
        INSTANCE.registerMessage(4, ControlMessageSECONDAbility.class, ControlMessageSECONDAbility::encode, ControlMessageSECONDAbility::decode, ControlMessageSECONDAbility::handle);
        INSTANCE.registerMessage(5, ClientMessageTameParticlesDragon.class, ClientMessageTameParticlesDragon::encode, ClientMessageTameParticlesDragon::decode, ClientMessageTameParticlesDragon::handle);
        INSTANCE.registerMessage(6, ClientMessageGuiDragon.class, ClientMessageGuiDragon::encode, ClientMessageGuiDragon::decode, ClientMessageGuiDragon::handle);
        INSTANCE.registerMessage(7, ControlMessageServer.class, ControlMessageServer::encode, ControlMessageServer::decode, ControlMessageServer::handle);
        INSTANCE.registerMessage(8, ClientMessageGuiDragonSit.class, ClientMessageGuiDragonSit::encode, ClientMessageGuiDragonSit::decode, ClientMessageGuiDragonSit::handle);
        INSTANCE.registerMessage(9, DragonRideMessage.class, DragonRideMessage::encode, DragonRideMessage::decode, DragonRideMessage::handle);
        INSTANCE.registerMessage(10, MessageDragonFlapSounds.class, MessageDragonFlapSounds::encode, MessageDragonFlapSounds::decode, MessageDragonFlapSounds::handle);
        INSTANCE.registerMessage(11, MessageStingerMovingForRam.class, MessageStingerMovingForRam::encode, MessageStingerMovingForRam::decode, MessageStingerMovingForRam::handle);
        INSTANCE.registerMessage(12, MessageShockParticle.class, MessageShockParticle::encode, MessageShockParticle::decode, MessageShockParticle::handle);
    }
}
