package com.GACMD.isleofberk.client.gui.event;

import com.GACMD.isleofberk.common.entity.entities.base.dragon.ADragonRideableUtility;
import com.GACMD.isleofberk.common.entity.network.ControlNetwork;
import com.GACMD.isleofberk.common.entity.network.message.ClientMessageGuiDragon;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.inventory.InventoryScreen;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.client.event.ScreenOpenEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class RiderInventoryHandler
{
    @SubscribeEvent
    public static void onOpenInventory(ScreenOpenEvent event)
    {
        if(event.getScreen() != null && event.getScreen() instanceof InventoryScreen)
        {
            Player localPlayer = Minecraft.getInstance().player;
            if(localPlayer.isPassenger() && localPlayer.getVehicle() instanceof ADragonRideableUtility)
            {
                if(((ADragonRideableUtility) localPlayer.getVehicle()).getOwner() == localPlayer)
                {
                    event.setCanceled(true);
                    // Sends a packet to the server, and notifies it of the player opening the inventory
                    ADragonRideableUtility dragonBase = (ADragonRideableUtility) localPlayer.getVehicle();
                    ControlNetwork.INSTANCE.sendToServer(new ClientMessageGuiDragon(dragonBase.getId()));
                }
            }
        }
    }
}
