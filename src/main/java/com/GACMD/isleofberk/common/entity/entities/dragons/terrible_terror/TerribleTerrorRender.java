package com.GACMD.isleofberk.common.entity.entities.dragons.terrible_terror;

import com.GACMD.isleofberk.common.entity.entities.base.render.render.BaseRenderer;
import com.GACMD.isleofberk.common.entity.entities.base.render.layer.DragonHeldItemLayer;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class TerribleTerrorRender extends BaseRenderer<TerribleTerror> {

    public TerribleTerrorRender(EntityRendererProvider.Context renderManager) {
        super(renderManager, new TerribleTerrorModel());
        this.addLayer(new DragonHeldItemLayer(this));
    }

    @Override
    public RenderType getRenderType(TerribleTerror animatable, float partialTicks, PoseStack stack,
                                    MultiBufferSource renderTypeBuffer, VertexConsumer vertexBuilder, int packedLightIn,
                                    ResourceLocation textureLocation) {
        return RenderType.entityCutoutNoCull(getTextureLocation(animatable));
    }

    @Override
    public String getDragonFolder() {
        return "terrible_terror";
    }
}
