package com.GACMD.isleofberk.entity.base.path;

import com.GACMD.isleofberk.entity.base.dragon.ADragonBaseFlyingRideable;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.pathfinder.PathFinder;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;

public class FlyingPathNavigation extends PathNavigation {

    ADragonBaseFlyingRideable dragon;

    public FlyingPathNavigation(Mob p_26515_, Level p_26516_) {
        super(p_26515_, p_26516_);
        this.dragon = (ADragonBaseFlyingRideable) p_26515_;
    }

    @Override
    protected PathFinder createPathFinder(int p_26598_) {
        this.nodeEvaluator = new FlyNodeEvaluator();
        return new PathFinder(this.nodeEvaluator, p_26598_);
    }

    /**
     * If on ground or swimming and can swim
     */
    protected boolean canUpdatePath() {
        return !this.mob.isOnGround();
    }

    protected Vec3 getTempMobPos() {
        return new Vec3(this.mob.getX(), this.mob.getY(0.5D), this.mob.getZ());
    }

    protected double getGroundY(Vec3 p_186136_) {
        return p_186136_.y;
    }

    protected boolean canMoveDirectly(Vec3 pPosVec31, Vec3 pPosVec32) {
        Vec3 vec3 = new Vec3(pPosVec32.x, pPosVec32.y + (double)this.mob.getBbHeight() * 0.5D, pPosVec32.z);
        return this.level.clip(new ClipContext(pPosVec31, vec3, ClipContext.Block.COLLIDER, ClipContext.Fluid.NONE, this.mob)).getType() == HitResult.Type.MISS;
    }

    public boolean isStableDestination(BlockPos pPos) {
//        return !this.level.getBlockState(pPos).isSolidRender(this.level, pPos);
        return true;
    }

    public void setCanFloat(boolean pCanSwim) {
    }
}
