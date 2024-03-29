package com.GACMD.isleofberk.network.message;

import com.GACMD.isleofberk.entity.base.dragon.ADragonBase;
import com.GACMD.isleofberk.gui.DragonContainerMenu;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraftforge.fml.LogicalSide;
import net.minecraftforge.network.NetworkEvent;
import net.minecraftforge.network.NetworkHooks;
import org.jetbrains.annotations.NotNull;

import java.util.function.Supplier;

public class ClientMessageGuiDragon {
    int entityId;

    public ClientMessageGuiDragon(int entityId) {
        this.entityId = entityId;
    }

    public static void encode(ClientMessageGuiDragon message, FriendlyByteBuf buffer) {
        buffer.writeInt(message.entityId);
    }

    public static ClientMessageGuiDragon decode(FriendlyByteBuf buffer) {
        return new ClientMessageGuiDragon(buffer.readInt());
    }

    public static void handle(ClientMessageGuiDragon message, Supplier<NetworkEvent.Context> ctx) {
        NetworkEvent.Context context = ctx.get();
        //The server Player
        ServerPlayer player = context.getSender();
        //The Dragon entity

        if (player != null) {
            ADragonBase dragon = (ADragonBase) player.level.getEntity(message.entityId);

            if (context.getDirection().getReceptionSide() == LogicalSide.SERVER) {
                context.enqueueWork(() ->
                {
                    if (dragon != null) {
                        if (!player.level.isClientSide() && player.isPassenger() && player.getVehicle() instanceof ADragonBase) {
                            Component dragonName = dragon.getName();
                            int id = dragon.getId();
                            NetworkHooks.openGui(player, new MenuProvider() {
                                @Override
                                public @NotNull Component getDisplayName() {
                                    return dragonName;
                                }

                                @Override
                                public @NotNull AbstractContainerMenu createMenu(int i, @NotNull Inventory playerInventory, @NotNull Player playerEntity) {
                                    return new DragonContainerMenu(i, playerInventory, id);
                                }
                            }, buf -> buf.writeInt(id));
                        }
                    }
                });
                context.setPacketHandled(true);
            }
        }
    }
}