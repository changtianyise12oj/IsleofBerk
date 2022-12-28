package com.GACMD.isleofberk.entity.dragons.lightfury;

import com.GACMD.isleofberk.entity.dragons.nightfury.NightFuryGlowLayer;
import com.GACMD.isleofberk.entity.base.render.render.BaseRenderer;
import com.GACMD.isleofberk.entity.dragons.nightfury.NightFury;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.renderers.geo.IGeoRenderer;

public class LightFuryGlowLayer<T extends LightFury & IAnimatable> extends NightFuryGlowLayer<T> {

    protected BaseRenderer<T> baseRenderer;

    public LightFuryGlowLayer(IGeoRenderer<T> entityRendererIn) {
        super(entityRendererIn);
        this.baseRenderer = (BaseRenderer<T>) entityRendererIn;
    }

    public ResourceLocation getTextureLocation(NightFury entity) {
        switch (entity.getGlowVariants()) {
            default:
            case 0:
                return new ResourceLocation("isleofberk:textures/dragons/light_fury/blue_glow.png");
            case 1:
                return new ResourceLocation("isleofberk:textures/dragons/light_fury/purple_glow.png");
        }
    }

}
