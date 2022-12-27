package com.GACMD.isleofberk.gui;

import com.GACMD.isleofberk.IsleofBerk;
import com.GACMD.isleofberk.entity.base.dragon.ADragonRideableUtility;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.gui.screens.inventory.InventoryScreen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;


public class DragonInventoryScreen extends AbstractContainerScreen<DragonContainerMenu> {
    public static final ResourceLocation DRAGON_INVENTORY_LOCATION = new ResourceLocation(IsleofBerk.MOD_ID, "textures/gui/container/inventory.png");
    /**
     * The EntityDragon whose inventory is currently being accessed.
     */
    public ADragonRideableUtility dragon;
    /**
     * The mouse x-position recorded during the last rendered frame.
     */
    public float xMouse;
    /**
     * The mouse y-position recorded during the last renderered frame.
     */
    public float yMouse;

    public DragonInventoryScreen(DragonContainerMenu pMenu, Inventory pPlayerInventory, Component title) {
        super(pMenu, pPlayerInventory, title);
        this.passEvents = false;
        this.dragon = this.getMenu().dragon;
    }

    protected void renderBg(PoseStack pPoseStack, float pPartialTick, int pX, int pY) {
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.setShaderTexture(0, DRAGON_INVENTORY_LOCATION);
        int i = (this.width - this.imageWidth) / 2;
        int j = (this.height - this.imageHeight) / 2;
        this.blit(pPoseStack, i, j, 0, 0, this.imageWidth, this.imageHeight);
        ADragonRideableUtility abstractchesteddragon = this.dragon;
        if (abstractchesteddragon.hasChest()) { // hasChest
            this.blit(pPoseStack, i + 79, j + 17, 0, this.imageHeight, abstractchesteddragon.getInventoryColumns() * 18, 54);
        }

        if (this.dragon.isSaddleable()) {
            this.blit(pPoseStack, i + 7, j + 35 - 18, 18, this.imageHeight + 54, 18, 18);
        }

        this.blit(pPoseStack, i + 7, j + 35, 36, this.imageHeight + 54, 18, 18);

        InventoryScreen.renderEntityInInventory(i + 51, j + 70, 8, (float) (i + 51) - this.xMouse, (float) (j + 75 - 50) - this.yMouse, this.dragon);
    }

    public void render(PoseStack pPoseStack, int pMouseX, int pMouseY, float pPartialTick) {
        this.renderBackground(pPoseStack);
        this.xMouse = (float) pMouseX;
        this.yMouse = (float) pMouseY;
        super.render(pPoseStack, pMouseX, pMouseY, pPartialTick);
        this.renderTooltip(pPoseStack, pMouseX, pMouseY);
    }
}