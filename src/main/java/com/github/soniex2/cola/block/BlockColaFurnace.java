package com.github.soniex2.cola.block;

import com.github.soniex2.cola.init.ColaItems;
import com.github.soniex2.cola.tile.TileColaFurnace;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.SlotFurnace;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

/**
 * @author soniex2
 */
public class BlockColaFurnace extends BlockColaMachine {
    public BlockColaFurnace(Material material) {
        super(material);
        this.setBlockName("cola:cola_furnace");
        this.setBlockTextureName("cola:cola_furnace");
    }

    /**
     * Returns a new instance of a block's tile entity class. Called on placing the block.
     *
     * @param world
     * @param meta
     */
    @Override
    public TileEntity createNewTileEntity(World world, int meta) {
        return new TileColaFurnace();
    }

    @Override
    public boolean onBlockActivated(World w, int x, int y, int z, EntityPlayer p, int m, float hx, float hy, float hz) {
        if (p.isSneaking()) return false;
        if (w.isRemote) return true;
        TileEntity te = w.getTileEntity(x, y, z);
        if (te instanceof TileColaFurnace) {
            TileColaFurnace tcf = (TileColaFurnace) te;
            ItemStack held = p.getHeldItem();
            if (held == null) {
                // get output (and exp)
                // TODO fix sync issues
                SlotFurnace slot = new SlotFurnace(p, tcf, 1, 0, 0);
                if (slot.getHasStack()) {
                    ItemStack itemstack1 = slot.getStack();
                    ItemStack itemstack = itemstack1.copy();
                    if (p.inventory.addItemStackToInventory(itemstack1)) {
                        if (itemstack1.stackSize <= 0) {
                            slot.putStack(null);
                        } else {
                            slot.onSlotChanged();
                        }
                        if (itemstack1.stackSize != itemstack.stackSize) {
                            slot.onPickupFromSlot(p, itemstack1);
                        }
                    }
                } else if (tcf.getStackInSlot(0) != null) {
                    p.inventory.addItemStackToInventory(tcf.getStackInSlot(0));
                    if (tcf.getStackInSlot(0).stackSize <= 0) {
                        tcf.setInventorySlotContents(0, null);
                    }
                    tcf.markDirty();
                }
            } else if (held.getItem() == ColaItems.cola_bottle) {
                // add cola
                // TODO
            } else {
                // insert input
                ItemStack is = tcf.getStackInSlot(0);
                if (is == null) {
                    tcf.setInventorySlotContents(0, held.copy());
                    tcf.markDirty();
                    held.stackSize = 0;
                    // TODO clear held item?
                } else if (is.isItemEqual(held) && is.getMaxStackSize() > 1) {
                    // TODO test this?
                    int newStackSize = Math.min(tcf.getInventoryStackLimit(), is.stackSize + held.stackSize);
                    newStackSize = Math.min(newStackSize, is.getMaxStackSize());
                    held.stackSize -= newStackSize - is.stackSize;
                    is.stackSize = newStackSize;
                    if (held.stackSize < 0) held.stackSize = 0;
                    tcf.markDirty();
                }
            }
        }
        return true;
    }
}
