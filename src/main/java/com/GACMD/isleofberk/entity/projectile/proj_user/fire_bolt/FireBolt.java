package com.GACMD.isleofberk.entity.projectile.proj_user.fire_bolt;

import com.GACMD.isleofberk.entity.base.dragon.ADragonBase;
import com.GACMD.isleofberk.entity.base.dragon.ADragonBaseFlyingRideableProjUser;
import com.GACMD.isleofberk.registery.ModParticles;
import com.GACMD.isleofberk.entity.projectile.ScalableParticleType;
import com.GACMD.isleofberk.entity.projectile.abase.BaseLinearBoltProjectile;
import com.GACMD.isleofberk.entity.projectile.abase.BaseLinearFlightProjectile;
import com.GACMD.isleofberk.registery.ModEntities;
import com.google.common.collect.Sets;
import com.mojang.datafixers.util.Pair;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.PrimedTnt;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.ProtectionEnchantment;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.ExplosionDamageCalculator;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseFireBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.event.ForgeEventFactory;
import org.jetbrains.annotations.NotNull;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;

import javax.annotation.Nullable;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Set;

public class FireBolt extends BaseLinearBoltProjectile implements IAnimatable {

    AnimationFactory factory = new AnimationFactory(this);

    /**
     * registry constructor
     *
     * @param type
     * @param level
     */
    public FireBolt(EntityType<? extends FireBolt> type, Level level) {
        super(type, level);
    }

    public FireBolt(ADragonBaseFlyingRideableProjUser owner, Vec3 throat, Vec3 end, Level level, int strengthRadius) {
        super(ModEntities.FIRE_BOLT.get(), owner, throat, end, level, strengthRadius);
    }

    private <E extends IAnimatable> PlayState predicate(AnimationEvent<E> event) {
//        event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.projectile.spin", true));
        return PlayState.CONTINUE;
    }

    @Override
    public void registerControllers(AnimationData data) {
        data.addAnimationController(new AnimationController<>(this, "fire_bolt", 0, this::predicate));
    }

    @Override
    public AnimationFactory getFactory() {
        return factory;
    }


    @Override
    public void tick() {
        super.tick();
    }

    @Override
    protected ParticleOptions getTrailParticle() {
        return ModParticles.FLAME_TAIL.get();
    }

    @Override
    protected boolean canHitEntity(@NotNull Entity p_36842_) {
        return super.canHitEntity(p_36842_) && p_36842_ != this.getOwner(); //  && p_36842_ instanceof ADragonBase
    }

    @Override
    public boolean isNoGravity() {
        return true;
    }

    @Override
    public float getBrightness() {
        return 0;
    }

    /**
     * biggest without looking weird is 1.25F
     *
     * @param scalableParticleType
     */
    @Override
    public void scaleParticleSize(ScalableParticleType scalableParticleType, BaseLinearFlightProjectile projectile) {
//        if (projectile.getDamageTier() == 1) {
//            scalableParticleType.setScale(0.15f);
//        } else if (projectile.getDamageTier() == 2) {
//            scalableParticleType.setScale(0.25f);
//        } else if (projectile.getDamageTier() == 3) {
//            scalableParticleType.setScale(0.55f);
//        } else if (projectile.getDamageTier() == 4) {
//            scalableParticleType.setScale(0.85f);
//        }
    }
}
