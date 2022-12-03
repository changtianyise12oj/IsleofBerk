//package com.GACMD.isleofberk.common.entity.model.render;
//
//import com.GACM.isleofberk.IsleofBerk;
//import com.GACMD.isleofberk.common.entity.entities.StingerEntity;
//import net.minecraft.client.render.VertexConsumerProvider;
//import net.minecraft.client.renderer.entity.layers.RenderLayer;
//import net.minecraft.client.renderer.texture.OverlayTexture;
//import net.minecraft.client.util.math.MatrixStack;
//import net.minecraft.util.Identifier;
//import software.bernie.geckolib3.renderers.geo.GeoLayerRenderer;
//import software.bernie.geckolib3.renderers.geo.IGeoRenderer;
//
//public class StingerChestRender extends GeoLayerRenderer<StingerEntity> {
//    private final StingerRender stingerRender;
//    Identifier layerLocation = null;
//
//    public StingerChestRender(IGeoRenderer<StingerEntity> entityRendererIn, StingerRender stingerRender) {
//        super(entityRendererIn);
//        this.stingerRender = stingerRender;
//    }
//
//    @Override
//    public void render(MatrixStack matrixStackIn, VertexConsumerProvider bufferIn, int packedLightIn, StingerEntity stinger, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch) {
//        if (stinger.isChested()) {
//            this.layerLocation = new Identifier(IsleofBerk.MOD_ID, "textures/dragons/stinger/chest.png");
//        }
//
//        if (stinger.isElytrat()) {
//            stingerRender.render(getEntityModel().getModel(new Identifier(IsleofBerk.MOD_ID, "geo/dragon/stinger.geo.json")),
//                    stinger,
//                    partialTicks,
//                    RenderLayer.getEntityCutout(layerLocation),
//                    matrixStackIn,
//                    bufferIn,
//                    bufferIn.getBuffer(RenderLayer.getEntityCutout(layerLocation)),
//                    packedLightIn, OverlayTexture.DEFAULT_UV, 1, 1, 1, 1);
//        }
//    }
//}