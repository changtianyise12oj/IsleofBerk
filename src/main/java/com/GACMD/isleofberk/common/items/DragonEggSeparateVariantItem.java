package com.GACMD.isleofberk.common.items;

import com.GACMD.isleofberk.common.entity.entities.eggs.entity.base.ADragonEggBase;
import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;
import java.util.function.Supplier;

public class DragonEggSeparateVariantItem extends DragonEggItem {

    int variantItem;

    public DragonEggSeparateVariantItem(Item.Properties pProperties, Supplier<? extends EntityType<? extends LivingEntity>> eggSpecies, int variantItem) {
        super(pProperties, eggSpecies);
        this.variantItem = variantItem;
    }

    public int getVariant() {
        return variantItem;
    }

    public void setVariant(int variant) {
        this.variantItem = variant;
    }

    /**
     * Called when this item is used when targetting a Block
     * if placed, the player will be set as owner in the egg entity and the recurring hatching, used on obtaining speed stinger.
     */
    @Override
    public @NotNull InteractionResult useOn(UseOnContext pContext) {
        ItemStack playerHeldItem = pContext.getItemInHand();
        BlockPos pos = pContext.getClickedPos();
        Level level = pContext.getLevel();
        ADragonEggBase eggEntity = (ADragonEggBase) eggSpecies.get().create(level);
        // Set owner
        assert eggEntity != null;
        eggEntity.moveTo(pContext.getClickLocation());
        eggEntity.setDragonVariant(variantItem);

        if (!level.isClientSide()) {
            level.addFreshEntity(eggEntity);
            eggEntity.setCustomName(playerHeldItem.getHoverName());
        }
        playerHeldItem.shrink(1);
        return InteractionResult.SUCCESS;
    }
}
