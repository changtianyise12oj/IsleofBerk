package com.GACMD.isleofberk.network.message;

import com.GACMD.isleofberk.entity.base.dragon.ADragonBase;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.fml.LogicalSide;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class ClientMessageTameParticlesDragon {
    int entityId;
    boolean check;

    public ClientMessageTameParticlesDragon(int entityId, boolean check) {
        this.entityId = entityId;
        this.check = check;
    }

    public static void encode(ClientMessageTameParticlesDragon message, FriendlyByteBuf buffer) {
        buffer.writeInt(message.entityId);
        buffer.writeBoolean(message.check);
    }

    public static ClientMessageTameParticlesDragon decode(FriendlyByteBuf buffer) {
        return new ClientMessageTameParticlesDragon(buffer.readInt(), buffer.readBoolean());
    }

    public static void handle(ClientMessageTameParticlesDragon message, Supplier<NetworkEvent.Context> ctx) {
        NetworkEvent.Context context = ctx.get();
        Player player = context.getSender();


        if (player != null) {
            Entity entity = player.level.getEntity(message.entityId);

            if (entity instanceof ADragonBase dragon) {
                if (context.getDirection().getReceptionSide() == LogicalSide.CLIENT) {
                    context.enqueueWork(() ->
                    {
                        spawnTamingParticles(message.check, dragon);
                    });
                    context.setPacketHandled(true);
                }
            }
        }
    }

    /**
     * Spawns particles for the dragon entity. par1 tells whether to spawn hearts. If it is false, it spawns smoke."
     */
    protected static void spawnTamingParticles(boolean p_30670_, ADragonBase dragon) {
        if (dragon.level.isClientSide()) {
            ParticleOptions particleoptions = p_30670_ ? ParticleTypes.HEART : ParticleTypes.SMOKE;

            for (int i = 0; i < 7; ++i) {
                double d0 = dragon.getRandom().nextGaussian() * 0.02D;
                double d1 = dragon.getRandom().nextGaussian() * 0.02D;
                double d2 = dragon.getRandom().nextGaussian() * 0.02D;
                dragon.level.addParticle(particleoptions, dragon.getRandomX(1.0D), dragon.getRandomY() + 0.5D, dragon.getRandomZ(1.0D), d0, d1, d2);
            }
        }
    }
}