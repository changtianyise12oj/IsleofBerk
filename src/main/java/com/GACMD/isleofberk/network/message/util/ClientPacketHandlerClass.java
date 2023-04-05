package com.GACMD.isleofberk.network.message.util;

import com.GACMD.isleofberk.network.message.MessageShockParticle;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.levelgen.RandomSource;
import net.minecraftforge.network.NetworkEvent;

import java.util.Random;
import java.util.function.Supplier;

public class ClientPacketHandlerClass {
    public static void handleSpawnShockParticles(MessageShockParticle msg, Supplier<NetworkEvent.Context> ctx) {
        Random rand = new Random();
        for (int i = 0; i < 20; i++) {
            if(Minecraft.getInstance().level != null) {
                Entity entity = Minecraft.getInstance().level.getEntity(msg.id);
                if(entity != null)
                    Minecraft.getInstance().level.addParticle(ParticleTypes.ELECTRIC_SPARK, entity.getX() + (rand.nextFloat() - 0.5F), entity.getY() + entity.getBbHeight() / 2F + (rand.nextFloat() - 0.5F), entity.getZ() + (rand.nextFloat() - 0.5F), (rand.nextFloat() - 0.5F) * 0.5F, (rand.nextFloat() - 0.5F) * 0.5F, (rand.nextFloat() - 0.5F) * 0.5F);
            }
        }
    }
}
