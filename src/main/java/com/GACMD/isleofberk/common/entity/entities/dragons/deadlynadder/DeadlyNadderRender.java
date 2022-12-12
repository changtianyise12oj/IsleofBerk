package com.GACMD.isleofberk.common.entity.entities.dragons.deadlynadder;

import com.GACMD.isleofberk.common.entity.entities.base.render.render.BaseRendererFlying;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.jetbrains.annotations.Nullable;

@OnlyIn(Dist.CLIENT)
public class DeadlyNadderRender extends BaseRendererFlying<DeadlyNadder> {

    public DeadlyNadderRender(EntityRendererProvider.Context renderManager) {
        super(renderManager, new DeadlyNadderModel(renderManager));
        this.addLayer(new DeadlyNadderWingLayer<>(this, renderManager));
    }

    @Override
    public RenderType getRenderType(DeadlyNadder animatable, float partialTicks, PoseStack stack, @Nullable MultiBufferSource renderTypeBuffer, @Nullable VertexConsumer vertexBuilder, int packedLightIn, ResourceLocation textureLocation) {
        return RenderType.entityCutoutNoCull(textureLocation);
    }

    @Override
    public String getDragonFolder() {
        return "deadly_nadder";
    }
    @Override
    protected String getMainBodyBone() {
        return "rotation";
    }
}
