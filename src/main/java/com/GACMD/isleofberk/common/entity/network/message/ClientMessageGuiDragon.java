package com.GACMD.isleofberk.common.entity.network.message;

import com.GACMD.isleofberk.client.gui.DragonContainerMenu;
import com.GACMD.isleofberk.common.entity.entities.base.dragon.ADragonBase;
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

import javax.annotation.Nullable;
import java.util.function.Supplier;

public class ClientMessageGuiDragon {
    private int entityId;

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
        Player player = context.getSender();
        //The Dragon entity
        ADragonBase dragon = (ADragonBase) player.level.getEntity(message.entityId);

        if (context.getDirection().getReceptionSide() == LogicalSide.SERVER) {
            context.enqueueWork(() ->
            {
                if (dragon != null) {
                    if (!player.level.isClientSide() && player.isPassenger() && player.getVehicle() instanceof ADragonBase) {
                        Component dragonName = dragon.getName();
                        int id = dragon.getId();
                        NetworkHooks.openGui((ServerPlayer) player, new MenuProvider() {
                            @Override
                            public Component getDisplayName() {
                                return dragonName;
                            }

                            @Nullable
                            @Override
                            public AbstractContainerMenu createMenu(int i, Inventory playerInventory, Player playerEntity) {
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