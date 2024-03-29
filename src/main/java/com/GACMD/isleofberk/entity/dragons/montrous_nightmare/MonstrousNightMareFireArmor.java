package com.GACMD.isleofberk.entity.dragons.montrous_nightmare;

import com.GACMD.isleofberk.IsleofBerk;
import com.GACMD.isleofberk.entity.base.render.render.BaseRenderer;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.renderers.geo.GeoLayerRenderer;
import software.bernie.geckolib3.renderers.geo.IGeoRenderer;

public class MonstrousNightMareFireArmor<T extends MonstrousNightmare & IAnimatable> extends GeoLayerRenderer<T> {
    private static final ResourceLocation POWER_LOCATION = new ResourceLocation(IsleofBerk.MOD_ID, "textures/dragons/nightmare/fire.png");

    BaseRenderer<T> baseRenderer;

    public MonstrousNightMareFireArmor(IGeoRenderer<T> entityRendererIn) {
        super(entityRendererIn);

        this.baseRenderer = (BaseRenderer<T>) entityRendererIn;
    }


    @Override
    public void render(PoseStack matrixStackIn, MultiBufferSource buffer, int packedLightIn, T dragon, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch) {
        if (dragon.isOnFireAbility()) {
            float f = (float) dragon.tickCount + partialTicks;
            RenderType cameo = RenderType.energySwirl(POWER_LOCATION, this.xOffset(f) % 1.0F, f * 0.008F % 1.0F);

            this.getRenderer().render(getEntityModel().getModel(baseRenderer.getGeoModelProvider().getModelLocation(dragon)), dragon, partialTicks, cameo, matrixStackIn, buffer,
                    buffer.getBuffer(cameo), 15728640, OverlayTexture.NO_OVERLAY, 0.8F, 0.8F, 0.8F, 1.0F);
        }
    }

    protected float xOffset(float ticksExisted) {
        return ticksExisted * 0.008F;
    }

    protected ResourceLocation getTextureLocation() {
        return POWER_LOCATION;
    }

}