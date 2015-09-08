package com.github.soniex2.cola.util;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;

import java.util.Arrays;

/**
 * @author soniex2
 */
public final class Inventory implements IInventory {
    private final int size;
    private final ItemStack[] slots;
    private final String name;
    private final boolean isNameCustom;
    private final int maxStackSize;

    public Inventory(int size, String name) {
        this(size, name, false, 64);
    }

    public Inventory(int size, String name, boolean isNameCustom) {
        this(size, name, isNameCustom, 64);
    }

    public Inventory(int size, String name, int maxStackSize) {
        this(size, name, false, maxStackSize);
    }

    public Inventory(int size, String name, boolean isNameCustom, int maxStackSize) {
        this.size = size;
        slots = new ItemStack[size];
        this.name = name;
        this.isNameCustom = isNameCustom;
        this.maxStackSize = maxStackSize;
    }

    /**
     * Returns the number of slots in the inventory.
     */
    @Override
    public int getSizeInventory() {
        return size;
    }

    /**
     * Returns the stack in the given slot
     *
     * @param slot The slot
     * @throws IndexOutOfBoundsException If the slot is beyond this inventory's valid slots.
     */
    @Override
    public ItemStack getStackInSlot(int slot) {
        return slots[slot];
    }

    /**
     * Removes items from the given slot up to a specified amount and returns them in a new stack.
     *
     * @param slot  The slot
     * @param count The maximum number of items to remove
     * @throws IndexOutOfBoundsException If the slot is beyond this inventory's valid slots.
     */
    @Override
    public ItemStack decrStackSize(int slot, int count) {
        if (slots[slot] == null) return null;

        ItemStack itemstack;

        if (slots[slot].stackSize <= count) {
            itemstack = slots[slot];
            slots[slot] = null;
        } else {
            itemstack = slots[slot].splitStack(count);
            if (slots[slot].stackSize == 0)
                slots[slot] = null;
        }

        return itemstack;
    }

    /**
     * Clear the given slot and return its contents.
     *
     * @param slot The slot
     * @throws IndexOutOfBoundsException If the slot is beyond this inventory's valid slots.
     */
    @Override
    public ItemStack getStackInSlotOnClosing(int slot) {
        if (slots[slot] == null) return null;

        ItemStack itemstack = slots[slot];
        slots[slot] = null;
        return itemstack;
    }

    /**
     * Replace the given slot's contents with the given stack.
     *
     * @param slot  The slot
     * @param stack The stack
     * @throws IndexOutOfBoundsException If the slot is beyond this inventory's valid slots.
     */
    @Override
    public void setInventorySlotContents(int slot, ItemStack stack) {
        slots[slot] = stack;

        if (stack != null && stack.stackSize > getInventoryStackLimit()) {
            stack.stackSize = getInventoryStackLimit();
        }
    }

    /**
     * Returns the name of the inventory
     */
    @Override
    public String getInventoryName() {
        return name;
    }

    /**
     * Returns whether the inventory has a custom name
     */
    @Override
    public boolean hasCustomInventoryName() {
        return isNameCustom;
    }

    /**
     * Returns the maximum stack size for an inventory slot.
     */
    @Override
    public int getInventoryStackLimit() {
        return maxStackSize;
    }

    // Shit methods.

    @Override
    public boolean isItemValidForSlot(int slot, ItemStack stack) {
        return true;
    }

    @Override
    public void markDirty() {
    }

    @Override
    public boolean isUseableByPlayer(EntityPlayer p_70300_1_) {
        return true;
    }

    @Override
    public void openInventory() {
    }

    @Override
    public void closeInventory() {
    }

    public void readFromNBT(NBTTagCompound tag) {
        Arrays.fill(slots, null); // clear inv

        NBTTagList itemList = tag.getTagList("Items", 10);
        for (int i = 0, itemCount = itemList.tagCount(); i < itemCount; ++i) {
            NBTTagCompound item = itemList.getCompoundTagAt(i);
            byte slot = item.getByte("Slot");

            if (slot >= 0 && slot < slots.length) {
                slots[slot] = ItemStack.loadItemStackFromNBT(item);
            }
        }
    }

    public void writeToNBT(NBTTagCompound tag) {
        NBTTagList itemList = new NBTTagList();

        for (int i = 0, slotCount = slots.length; i < slotCount; ++i) {
            if (slots[i] != null) {
                NBTTagCompound item = new NBTTagCompound();
                item.setByte("Slot", (byte) i);
                slots[i].writeToNBT(item);
                itemList.appendTag(item);
            }
        }

        tag.setTag("Items", itemList);
    }
}
