package com.github.soniex2.cola.tile;

import com.github.soniex2.cola.util.Inventory;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraft.nbt.NBTTagCompound;

/**
 * @author soniex2
 */
public class TileColaFurnace extends TileColaBase implements ISidedInventory {

    private static final int[] slotsTop = new int[]{0};
    private static final int[] slotsOther = new int[]{1};

    private Inventory slots = new Inventory(2, "cola:cola_furnace");
    private int cookTime;

    /**
     * Returns an array containing the indices of the slots that can be accessed by automation on the given side of this
     * block.
     *
     * @param side
     */
    @Override
    public int[] getAccessibleSlotsFromSide(int side) {
        return side == 1 ? slotsTop : slotsOther;
    }

    /**
     * Returns true if automation can insert the given item in the given slot from the given side. Args: Slot, item,
     * side
     *
     * @param slot
     * @param stack
     * @param side
     */
    @Override
    public boolean canInsertItem(int slot, ItemStack stack, int side) {
        return isItemValidForSlot(slot, stack);
    }

    /**
     * Returns true if automation can extract the given item in the given slot from the given side. Args: Slot, item,
     * side
     *
     * @param slot
     * @param stack
     * @param side
     */
    @Override
    public boolean canExtractItem(int slot, ItemStack stack, int side) {
        return true;
    }

    /**
     * Returns true if automation is allowed to insert the given stack (ignoring stack size) into the given slot.
     *
     * @param slot
     * @param stack
     */
    @Override
    public boolean isItemValidForSlot(int slot, ItemStack stack) {
        return slot != 1;
    }

    public void readFromNBT(NBTTagCompound tag) {
        super.readFromNBT(tag);
        slots.readFromNBT(tag);
        cookTime = tag.getShort("CookTime");
    }

    public void writeToNBT(NBTTagCompound tag) {
        super.writeToNBT(tag);
        tag.setShort("CookTime", (short) cookTime);
        slots.writeToNBT(tag);
    }

    public void updateEntity() {
        boolean shouldMarkDirty = false;

        if (!worldObj.isRemote) {
            if (colaTank.getFluidAmount() != 0 && getStackInSlot(0) != null && canSmelt()) {
                colaTank.drain(1, true);
                ++cookTime;

                if (cookTime == 200) {
                    cookTime = 0;
                    smeltItem();
                    shouldMarkDirty = true;
                }
            } else {
                cookTime = 0;
            }
        }

        if (shouldMarkDirty) {
            markDirty();
        }
    }

    private boolean canSmelt() {
        ItemStack input = getStackInSlot(0);
        if (input == null) return false;
        ItemStack result = FurnaceRecipes.smelting().getSmeltingResult(input);
        if (result == null) return false;
        ItemStack output = getStackInSlot(1);
        if (output == null) return true;
        if (!output.isItemEqual(result)) return false;
        int count = output.stackSize + result.stackSize;
        return count <= getInventoryStackLimit() && count <= output.getMaxStackSize();
    }

    public void smeltItem() {
        if (canSmelt()) {
            ItemStack result = FurnaceRecipes.smelting().getSmeltingResult(getStackInSlot(0));
            ItemStack output = getStackInSlot(1);

            if (output == null) {
                setInventorySlotContents(1, result.copy());
            } else {
                output.stackSize += result.stackSize;
            }

            if (--getStackInSlot(0).stackSize <= 0) {
                setInventorySlotContents(0, null);
            }
        }
    }

    // Below methods simply proxy into the internal Inventory.

    /**
     * Returns the number of slots in the inventory.
     */
    @Override
    public int getSizeInventory() {
        return slots.getSizeInventory();
    }

    /**
     * Returns the stack in slot i
     *
     * @param slot
     */
    @Override
    public ItemStack getStackInSlot(int slot) {
        return slots.getStackInSlot(slot);
    }

    /**
     * Removes from an inventory slot (first arg) up to a specified number (second arg) of items and returns them in a
     * new stack.
     *
     * @param slot
     * @param count
     */
    @Override
    public ItemStack decrStackSize(int slot, int count) {
        return slots.decrStackSize(slot, count);
    }

    /**
     * When some containers are closed they call this on each slot, then drop whatever it returns as an EntityItem -
     * like when you close a workbench GUI.
     *
     * @param slot
     */
    @Override
    public ItemStack getStackInSlotOnClosing(int slot) {
        return slots.getStackInSlotOnClosing(slot);
    }

    /**
     * Sets the given item stack to the specified slot in the inventory (can be crafting or armor sections).
     *
     * @param slot
     * @param stack
     */
    @Override
    public void setInventorySlotContents(int slot, ItemStack stack) {
        slots.setInventorySlotContents(slot, stack);
    }

    /**
     * Returns the name of the inventory
     */
    @Override
    public String getInventoryName() {
        return slots.getInventoryName();
    }

    /**
     * Returns if the inventory is named
     */
    @Override
    public boolean hasCustomInventoryName() {
        return slots.hasCustomInventoryName();
    }

    /**
     * Returns the maximum stack size for a inventory slot.
     */
    @Override
    public int getInventoryStackLimit() {
        return slots.getInventoryStackLimit();
    }

    /**
     * Do not make give this method the name canInteractWith because it clashes with Container
     *
     * @param player
     */
    @Override
    public boolean isUseableByPlayer(EntityPlayer player) {
        return worldObj.getTileEntity(xCoord, yCoord, zCoord) == this && player.getDistanceSq(xCoord + 0.5D, yCoord + 0.5D, zCoord + 0.5D) <= 64.0D;
    }

    @Override
    public void openInventory() {

    }

    @Override
    public void closeInventory() {

    }
}
