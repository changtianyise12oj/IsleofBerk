package com.GACMD.isleofberk.gui;

import com.GACMD.isleofberk.entity.base.dragon.ADragonRideableUtility;
import com.GACMD.isleofberk.registery.ModContainerTypes;
import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import org.jetbrains.annotations.NotNull;

public class DragonContainerMenu extends AbstractContainerMenu {
    public ADragonRideableUtility dragon;
    public Container dragonContainer;

    public DragonContainerMenu(int iD, Inventory playerInv, int entityID) {
        super(ModContainerTypes.DRAGON_INV.get(), iD);
        this.dragon = (ADragonRideableUtility) playerInv.player.level.getEntity(entityID);
        this.dragonContainer = dragon.dragonContainer;
        this.dragon.setIsRenderedOnGUI(true);
        dragonContainer.startOpen(playerInv.player);

        /*
         * We add all the Slot's to the Container
         * First Slot is Saddle Slot
         * Second is Chest Slot
         * After that we Add the 15 "Chest Contents" Slot's
         * Followed by the 27 Player inventory Slot's
         * And lastly the 9 Hot Bar Slot's
         */
        // Saddle Slot
        this.addSlot(new Slot(dragonContainer, 0, 8, 18) {
            @Override
            public boolean mayPlace(@NotNull ItemStack itemStack) {
                return itemStack.is(Items.SADDLE) && !this.hasItem() && dragon.isSaddleable();
            }

            @Override
            public boolean isActive() {
                return dragon.isSaddleable();
            }

            @Override
            public boolean mayPickup(Player pPlayer) {
                return !dragon.hasChest();
            }
        });
        // Chest Slot
        this.addSlot(new Slot(dragonContainer, 1, 8, 36) {
            @Override
            public boolean mayPlace(@NotNull ItemStack itemStack) {
                if (dragon.isSaddleable()) {
                    if (!dragon.isSaddled()) {
                        return false;
                    }
                }
                return itemStack.is(Items.CHEST) && dragon.isChestable();
            }

            @Override
            public int getMaxStackSize() {
                return 1;
            }

            @Override
            public boolean isActive() {
                return dragon.isChestable();
            }

            @Override
            public boolean mayPickup(Player player) {
                int items = 0;
                for(int i = 2; i < dragonContainer.getContainerSize(); i++) {
                    ItemStack itemstack = dragonContainer.getItem(i);
                    if (!itemstack.isEmpty()) items++;
                }
                return items == 0;
            }

        });
        // "Chest Contents" Slot's
        for (int y = 0; y < 3; ++y) {
            for (int x = 0; x < dragon.getInventoryColumns(); ++x) {
                this.addSlot(new Slot(dragonContainer, 2 + x + y * dragon.getInventoryColumns(), 80 + x * 18, 18 + y * 18)
                {
                    @Override
                    public boolean isActive() {
                        return dragon.hasChest();
                    }

                    @Override
                    public boolean mayPlace(ItemStack stack) {
                        return this.isActive();
                    }
                });
            }
        }

        // Player Inventory Slot's
        for (int i1 = 0; i1 < 3; ++i1) {
            for (int k1 = 0; k1 < 9; ++k1) {
                this.addSlot(new Slot(playerInv, k1 + i1 * 9 + 9, 8 + k1 * 18, 102 + i1 * 18 + -18));
            }
        }
        // Player Hot Bar Slot's
        for (int j1 = 0; j1 < 9; ++j1) {
            this.addSlot(new Slot(playerInv, j1, 8 + j1 * 18, 142));
        }
    }

    @Override
    public boolean stillValid(@NotNull Player pPlayer) {
        return !this.dragon.hasInventoryChanged(this.dragonContainer) && this.dragon.isAlive() && this.dragon.distanceTo(pPlayer) < 8.0F;
    }

    @Override
    public @NotNull ItemStack quickMoveStack(@NotNull Player pPlayer, int pIndex) {
        ItemStack itemstack = ItemStack.EMPTY;
        Slot slot = this.slots.get(pIndex);
        if (slot.hasItem()) {
            ItemStack itemstack1 = slot.getItem();
            itemstack = itemstack1.copy();
            int i = this.dragonContainer.getContainerSize();
            // Moving Items out of the Dragon Inventory
            if (pIndex < i) {
                if (!this.moveItemStackTo(itemstack1, i, this.slots.size(), true)) {
                    return ItemStack.EMPTY;
                }
            }
            // Called For Moving Chest Into Chest Slot
            else if (this.getSlot(1).mayPlace(itemstack1) && !this.getSlot(1).hasItem()) {
                if (!this.moveItemStackTo(itemstack1, 1, 2, false)) {
                    return ItemStack.EMPTY;
                }
            }
            // Called For Moving Saddle Into Saddle Slot
            else if (this.getSlot(0).mayPlace(itemstack1)) {
                if (!this.moveItemStackTo(itemstack1, 0, 1, false)) {
                    return ItemStack.EMPTY;
                }
            }
            // Called For Moving Items into the Container
            else if (i <= 2 || !this.moveItemStackTo(itemstack1, 2, i, false)) {
                int j = i + 27;
                int k = j + 9;
                if (pIndex >= j && pIndex < k) {
                    if (!this.moveItemStackTo(itemstack1, i, j, false)) {
                        return ItemStack.EMPTY;
                    }
                } else if (pIndex < j) {
                    if (!this.moveItemStackTo(itemstack1, j, k, false)) {
                        return ItemStack.EMPTY;
                    }
                } else if (!this.moveItemStackTo(itemstack1, j, j, false)) {
                    return ItemStack.EMPTY;
                }
                return ItemStack.EMPTY;
            }

            if (itemstack1.isEmpty()) {
                slot.set(ItemStack.EMPTY);
            } else {
                slot.setChanged();
            }
        }
        return itemstack;
    }

    /**
     * Called when the dragonContainer is closed.
     */
    public void removed(Player pPlayer) {
        super.removed(pPlayer);
        dragon.setIsRenderedOnGUI(false);
        this.dragonContainer.stopOpen(pPlayer);
    }
}