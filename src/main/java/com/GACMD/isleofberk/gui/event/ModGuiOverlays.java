package com.GACMD.isleofberk.gui.event;

import com.GACMD.isleofberk.entity.base.dragon.ADragonBaseFlyingRideableBreathUser;
import com.GACMD.isleofberk.entity.base.dragon.ADragonBaseFlyingRideableProjUser;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.client.gui.IIngameOverlay;

public class ModGuiOverlays {

    public static final ResourceLocation GUI_ICONS_LOCATION = new ResourceLocation("textures/gui/icons.png");

    public static final IIngameOverlay PROJ_BAR_CHARGE = (gui, mStack, partialTicks, screenWidth, screenHeight) -> {
        if (gui.minecraft.player.getVehicle() instanceof ADragonBaseFlyingRideableProjUser projUser && !gui.minecraft.options.hideGui) {
            RenderSystem.setShaderTexture(0, GUI_ICONS_LOCATION);
            RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
            RenderSystem.disableBlend();

            gui.setupOverlayRenderState(true, false);
            int pX = screenWidth / 2 - 91;
            Minecraft minecraft = Minecraft.getInstance();
            minecraft.getProfiler().push("jumpBar");
            RenderSystem.setShaderTexture(0, GuiComponent.GUI_ICONS_LOCATION);
            float blastPendingFuel = (float) Math.floor(projUser.getPlayerBoltBlastPendingScale());

            int j1 = (int) (blastPendingFuel / projUser.getMaxPlayerBoltBlast() * 183);
            int k = screenHeight - 32 + 3;
            GuiComponent.blit(mStack, pX, k, 0, 84, 182, 5, 256, 256);
            if (j1 > 0) {
                GuiComponent.blit(mStack, pX, k, 0, 89, j1, 5, 256, 256);
            }

            minecraft.getProfiler().pop();

            RenderSystem.enableBlend();
            minecraft.getProfiler().pop();
            RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        }
    };
    public static final IIngameOverlay BREATH_BAR_FUEL = (gui, mStack, partialTicks, screenWidth, screenHeight) -> {
        if (gui.minecraft.player.getVehicle() instanceof ADragonBaseFlyingRideableBreathUser breathUser && !gui.minecraft.options.hideGui) {
            RenderSystem.setShaderTexture(0, GUI_ICONS_LOCATION);
            RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
            RenderSystem.disableBlend();

            gui.setupOverlayRenderState(true, false);
            int pX = screenWidth / 2 - 91;
            Minecraft minecraft = Minecraft.getInstance();
            minecraft.getProfiler().push("jumpBar");
            RenderSystem.setShaderTexture(0, GuiComponent.GUI_ICONS_LOCATION);
            float remainingFuel = (float) Math.floor(breathUser.getRemainingFuel());

            int j1 = (int) (remainingFuel / breathUser.getMaxFuel() * 183);
            int k = screenHeight - 32 + 3;
            GuiComponent.blit(mStack, pX, k, 0, 84, 182, 5, 256, 256);
            if (j1 > 0) {
                GuiComponent.blit(mStack, pX, k, 0, 89, j1, 5, 256, 256);
            }

            minecraft.getProfiler().pop();

            RenderSystem.enableBlend();
            minecraft.getProfiler().pop();
            RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        }
    };
}
