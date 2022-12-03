package com.GACMD.isleofberk.common.entity.entities.dragons.tryiple_stryke;

import com.GACMD.isleofberk.common.entity.entities.base.render.render.BaseRendererFlying;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;

public class TripleStrykeRenderer extends BaseRendererFlying<TripleStryke> {

    public TripleStrykeRenderer(EntityRendererProvider.Context renderManager) {
        super(renderManager, new TripleStrykeModel(renderManager));
//        this.addLayer(new TSStingerLayer(this));
    }

    @Override
    public float getBabyScale() {
        return 0.6F;
    }

    @Override
    public float getScale() {
        return 1.47F;
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

    protected String getLeftEyeBodyBone() {
        return "EyeL";
    }

    protected String getRightEyeBodyBone() {
        return "EyeR";
    }

    @Override
    public String getDragonFolder() {
        return "triple_stryke";
    }

    @Override
    public float getSaddleX() {
        return super.getSaddleX();
    }

    @Override
    public float getSaddleY() {
        return -0.01F;
    }

    @Override
    public float getSaddleZ() {
        return super.getSaddleZ();
    }

    @Override
    public float getSaddleScaleX() {
        return 0.7f;
    }

    @Override
    public float getSaddleScaleY() {
        return 0.7F;
    }

    @Override
    public float getSaddleScaleZ() {
        return 0.7F;
    }
}
