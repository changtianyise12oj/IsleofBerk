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
        int i = 3;
        dragonContainer.startOpen(playerInv.player);
        int j = -18;
        this.addSlot(new Slot(dragonContainer, 0, 8, 18) {
            /**
             * Check if the stack is allowed to be placed in this slot, used for armor slots as well as furnace fuel.
             */
            public boolean mayPlace(@NotNull ItemStack itemStack) {
                return itemStack.is(Items.SADDLE) && !this.hasItem() && dragon.isSaddleable();
            }

            /**
             * Actualy only call when we want to render the white square effect over the slots. Return always True, except
             * for the armor slot of the Donkey/Mule (we can't interact with the Undead and Skeleton dragons)
             */
            public boolean isActive() {
                return dragon.isSaddleable();
            }
        });
        this.addSlot(new Slot(dragonContainer, 1, 8, 36) {
            /**
             * Check if the stack is allowed to be placed in this slot, used for armor slots as well as furnace fuel.
             */
            public boolean mayPlace(@NotNull ItemStack itemStack) {
                return itemStack.is(Items.CHEST);
            }

            /**
             * Returns the maximum stack size for a given slot (usually the same as getInventoryStackLimit(), but 1 in the
             * case of armor slots)
             */
            public int getMaxStackSize() {
                return 1;
            }
        });
        if (dragon.hasChest()) {
            for (int k = 0; k < 3; ++k) {
                for (int l = 0; l < dragon.getInventoryColumns(); ++l) {
                    this.addSlot(new Slot(dragonContainer, 2 + l + k * dragon.getInventoryColumns(), 80 + l * 18, 18 + k * 18));
                }
            }
        }

        for (int i1 = 0; i1 < 3; ++i1) {
            for (int k1 = 0; k1 < 9; ++k1) {
                this.addSlot(new Slot(playerInv, k1 + i1 * 9 + 9, 8 + k1 * 18, 102 + i1 * 18 + -18));
            }
        }

        for (int j1 = 0; j1 < 9; ++j1) {
            this.addSlot(new Slot(playerInv, j1, 8 + j1 * 18, 142));
        }

    }

    /**
     * Determines whether supplied player can use this dragonContainer
     */
    public boolean stillValid(@NotNull Player pPlayer) {
        return !this.dragon.hasInventoryChanged(this.dragonContainer) && this.dragon.isAlive() && this.dragon.distanceTo(pPlayer) < 8.0F;
    }

    private boolean hasChest(ADragonRideableUtility p_150578_) {
        return p_150578_ != null && ((ADragonRideableUtility) p_150578_).hasChest();
    }

    /**
     * Handle when the stack in slot {@code index} is shift-clicked. Normally this moves the stack between the player
     * inventory and the other inventory(s).
     */
    public @NotNull ItemStack quickMoveStack(@NotNull Player pPlayer, int pIndex) {
        ItemStack itemstack = ItemStack.EMPTY;
        Slot slot = this.slots.get(pIndex);
        if (slot.hasItem() && dragon.hasChest()) {
            ItemStack itemstack1 = slot.getItem();
            itemstack = itemstack1.copy();
            int i = this.dragonContainer.getContainerSize();
            if (pIndex < i) {
                if (!this.moveItemStackTo(itemstack1, i, this.slots.size(), true)) {
                    return ItemStack.EMPTY;
                }
            } else if (this.getSlot(1).mayPlace(itemstack1) && !this.getSlot(1).hasItem()) {
                if (!this.moveItemStackTo(itemstack1, 1, 2, false)) {
                    return ItemStack.EMPTY;
                }
            } else if (this.getSlot(0).mayPlace(itemstack1)) {
                if (!this.moveItemStackTo(itemstack1, 0, 1, false)) {
                    return ItemStack.EMPTY;
                }
            } else if (i <= 2 || !this.moveItemStackTo(itemstack1, 2, i, false)) {
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