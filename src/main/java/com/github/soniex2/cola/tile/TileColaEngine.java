package com.github.soniex2.cola.tile;

import cofh.api.energy.EnergyStorage;
import cofh.api.energy.IEnergyProvider;
import cofh.api.energy.IEnergyReceiver;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ChatComponentText;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.fluids.FluidStack;

/**
 * @author soniex2
 */
public class TileColaEngine extends TileColaBase implements IEnergyProvider {

    private EnergyStorage storage = new EnergyStorage(80000, 10, 1000) /*{
        @Override
        public String toString() {
            return energy + " " + capacity + " " + maxExtract + " " + maxReceive;
        }
    }*/;
    public boolean isGenerating = false;
    public boolean isTankEmpty = true;

    public void print(EntityPlayer p) {
        String s = "Power: " + storage.getEnergyStored() + "/" + storage.getMaxEnergyStored();
        p.addChatMessage(new ChatComponentText(s));
        s = "Cola: " + colaTank.getFluidAmount() + "/" + colaTank.getCapacity();
        p.addChatMessage(new ChatComponentText(s));
    }

    @Override
    public void updateEntity() {
        super.updateEntity();
        if (worldObj.isRemote) return;
        //System.out.println(storage + " " + (colaTank.getFluid() == null ? "null" : colaTank.getFluid().getFluid().getName()) + " " + colaTank.getFluidAmount() + " " + colaTank.getCapacity());
        if (isTankEmpty && colaTank.getFluidAmount() > 0) {
            worldObj.addBlockEvent(this.xCoord, this.yCoord, this.zCoord, this.getBlockType(), 1, 0);
            this.isTankEmpty = false;
        } else if (!isTankEmpty && colaTank.getFluidAmount() == 0) {
            worldObj.addBlockEvent(this.xCoord, this.yCoord, this.zCoord, this.getBlockType(), 1, 1);
            this.isTankEmpty = true;
        }
        if (storage.receiveEnergy(10, true) == 10) {
            FluidStack fs = colaTank.drain(1, true);
            if (fs != null && fs.amount == 1) {
                storage.receiveEnergy(10, false);
                if (!isGenerating) {
                    worldObj.addBlockEvent(this.xCoord, this.yCoord, this.zCoord, this.getBlockType(), 0, 1);
                    this.isGenerating = true;
                }
            } else if (isGenerating) {
                worldObj.addBlockEvent(this.xCoord, this.yCoord, this.zCoord, this.getBlockType(), 0, 0);
                this.isGenerating = false;
            }
        } else if (isGenerating) {
            worldObj.addBlockEvent(this.xCoord, this.yCoord, this.zCoord, this.getBlockType(), 0, 0);
            this.isGenerating = false;
        }
        for (ForgeDirection dir : ForgeDirection.VALID_DIRECTIONS) {
            TileEntity te = worldObj.getTileEntity(this.xCoord + dir.offsetX, this.yCoord + dir.offsetY, this.zCoord + dir.offsetZ);
            if (te instanceof IEnergyReceiver) {
                storage.extractEnergy(((IEnergyReceiver) te).receiveEnergy(dir.getOpposite(), storage.extractEnergy(10, true), false), false);
            }
        }
    }

    /**
     * Returns TRUE if the TileEntity can connect on a given side.
     *
     * @param from
     */
    @Override
    public boolean canConnectEnergy(ForgeDirection from) {
        return true;
    }

    /**
     * Remove energy from an IEnergyProvider, internal distribution is left entirely to the IEnergyProvider.
     *
     * @param from       Orientation the energy is extracted from.
     * @param maxExtract Maximum amount of energy to extract.
     * @param simulate   If TRUE, the extraction will only be simulated.
     * @return Amount of energy that was (or would have been, if simulated) extracted.
     */
    @Override
    public int extractEnergy(ForgeDirection from, int maxExtract, boolean simulate) {
        return storage.extractEnergy(maxExtract, simulate);
    }

    /**
     * Returns the amount of energy currently stored.
     *
     * @param from
     */
    @Override
    public int getEnergyStored(ForgeDirection from) {
        return storage.getEnergyStored();
    }

    /**
     * Returns the maximum amount of energy that can be stored.
     *
     * @param from
     */
    @Override
    public int getMaxEnergyStored(ForgeDirection from) {
        return storage.getMaxEnergyStored();
    }

    @Override
    public void readFromNBT(NBTTagCompound tag) {
        super.readFromNBT(tag);
        storage.readFromNBT(tag);
    }

    @Override
    public void writeToNBT(NBTTagCompound tag) {
        super.writeToNBT(tag);
        storage.writeToNBT(tag);
    }

    @Override
    public Packet getDescriptionPacket() {
        NBTTagCompound tag = new NBTTagCompound();
        tag.setBoolean("isGenerating", isGenerating);
        tag.setBoolean("isTankEmpty", isTankEmpty);
        return new S35PacketUpdateTileEntity(this.xCoord, this.yCoord, this.zCoord, 0, tag);
    }

    @Override
    public void onDataPacket(NetworkManager net, S35PacketUpdateTileEntity pkt) {
        super.onDataPacket(net, pkt);
        NBTTagCompound tag = pkt.func_148857_g();

        if (tag.hasKey("isGenerating")) {
            this.isGenerating = tag.getBoolean("isGenerating");
        }
        if (tag.hasKey("isTankEmpty")) {
            this.isTankEmpty = tag.getBoolean("isTankEmpty");
        }
    }
}
