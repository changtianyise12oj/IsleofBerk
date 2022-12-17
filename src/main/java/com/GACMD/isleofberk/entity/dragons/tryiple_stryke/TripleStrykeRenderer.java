package com.GACMD.isleofberk.entity.dragons.tryiple_stryke;

import com.GACMD.isleofberk.entity.base.render.render.BaseRendererFlying;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class TripleStrykeRenderer extends BaseRendererFlying<TripleStryke> {

    public TripleStrykeRenderer(EntityRendererProvider.Context renderManager) {
        super(renderManager, new TripleStrykeModel(renderManager));
    }

    @Override
    public RenderType getRenderType(TripleStryke animatable, float partialTicks, PoseStack stack,
                                    MultiBufferSource renderTypeBuffer, VertexConsumer vertexBuilder, int packedLightIn,
                                    ResourceLocation textureLocation) {
        return RenderType.entityCutoutNoCull(getTextureLocation(animatable));
    }

    protected String getMainBodyBone() {
        return "main";
    }

    @Override
    public String getDragonFolder() {
        return "triple_stryke";
    }
}
