package com.GACMD.isleofberk.entity.dragons.montrous_nightmare;

import com.GACMD.isleofberk.IsleofBerk;
import com.GACMD.isleofberk.entity.base.render.render.BaseRenderer;
import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexFormat;
import net.minecraft.Util;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderStateShard;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.renderers.geo.GeoLayerRenderer;
import software.bernie.geckolib3.renderers.geo.IGeoRenderer;

import java.util.function.Function;

public class MonstrousNightMareFireArmor<T extends MonstrousNightmare & IAnimatable> extends GeoLayerRenderer<T> {
    private static final ResourceLocation POWER_LOCATION = new ResourceLocation(IsleofBerk.MOD_ID, "textures/dragons/nightmare/fire.png");

    BaseRenderer<T> baseRenderer;

    public MonstrousNightMareFireArmor(IGeoRenderer<T> entityRendererIn) {
        super(entityRendererIn);

        this.baseRenderer = (BaseRenderer<T>) entityRendererIn;
    }

//    private static final Function<ResourceLocation, RenderType> EYES = Util.memoize((p_173255_) -> {
//        RenderStateShard.TextureStateShard renderstateshard$texturestateshard = new RenderStateShard.TextureStateShard(p_173255_, false, false);
//        return create("eyes", DefaultVertexFormat.NEW_ENTITY, VertexFormat.Mode.QUADS, 256, false, true, RenderType.CompositeState.builder().setShaderState(RENDERTYPE_EYES_SHADER).setTextureState(renderstateshard$texturestateshard).setTransparencyState(ADDITIVE_TRANSPARENCY).setWriteMaskState(COLOR_WRITE).createCompositeState(false));
//    });
//
//    public static RenderType energySwirl(ResourceLocation pLocation, float pU, float pV) {
//        return create("energy_swirl", DefaultVertexFormat.NEW_ENTITY, VertexFormat.Mode.QUADS, 256, false, true, RenderType.CompositeState.builder().
//                setShaderState(RENDERTYPE_ENERGY_SWIRL_SHADER).setTextureState(new RenderStateShard.TextureStateShard(pLocation, false, false)).
//                setTexturingState(new RenderStateShard.OffsetTexturingStateShard(pU, pV)).setWriteMaskState(RenderType.COLOR_WRITE).setTransparencyState(ADDITIVE_TRANSPARENCY).setCullState(NO_CULL).setLightmapState(LIGHTMAP).setOverlayState(OVERLAY).createCompositeState(false));
//    }

    @Override
    public void render(PoseStack matrixStackIn, MultiBufferSource buffer, int packedLightIn, T dragon, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch) {
        if (dragon.isOnFireAbility()) {
            float f = (float) dragon.tickCount + partialTicks;
            RenderType cameo = RenderType.energySwirl(POWER_LOCATION, this.xOffset(f) % 1.0F, f * 0.01F % 1.0F);

            this.getRenderer().render(getEntityModel().getModel(baseRenderer.getGeoModelProvider().getModelLocation(dragon)), dragon, partialTicks, cameo, matrixStackIn, buffer,
                    buffer.getBuffer(cameo), 15728640, OverlayTexture.NO_OVERLAY, 0.5F, 0.5F, 0.5F, 1.0F);
        }
    }

    protected float xOffset(float p_116683_) {
        return p_116683_ * 0.01F;
    }

    protected ResourceLocation getTextureLocation() {
        return POWER_LOCATION;
    }

}