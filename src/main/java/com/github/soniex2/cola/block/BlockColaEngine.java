package com.github.soniex2.cola.block;

import com.github.soniex2.cola.tile.TileColaEngine;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import java.util.Random;

/**
 * @author soniex2
 */
public class BlockColaEngine extends BlockColaMachine {

    private IIcon topIconEmpty, topIconFull, sideIconLowPressure, sideIconHighPressure;

    public BlockColaEngine(Material material) {
        super(material);
        this.setBlockName("cola:cola_engine");
        this.setBlockTextureName("cola:cola_engine");
    }

    /**
     * Returns a new instance of a block's tile entity class. Called on placing the block.
     *
     * @param world
     * @param meta
     */
    @Override
    public TileEntity createNewTileEntity(World world, int meta) {
        return new TileColaEngine();
    }

    @Override
    public boolean onBlockActivated(World w, int x, int y, int z, EntityPlayer p, int m, float hx, float hy, float hz) {
        if (p.getHeldItem() != null) return false;
        if (!w.isRemote) {
            TileEntity te = w.getTileEntity(x, y, z);
            if (te instanceof TileColaEngine) {
                ((TileColaEngine) te).print(p);
            }
        }
        return true;
    }

    @Override
    public boolean onBlockEventReceived(World w, int x, int y, int z, int i, int d) {
        boolean b = false;
        TileEntity te = w.getTileEntity(x, y, z);
        if (te instanceof TileColaEngine) {
            switch (i) {
                case 0:
                    if (w.isRemote) {
                        ((TileColaEngine) te).isGenerating = d != 0;
                    }
                    b = true;
                    break;
                case 1:
                    if (w.isRemote) {
                        ((TileColaEngine) te).isTankEmpty = d != 0;
                    }
                    b = true;
                    break;
            }
        }
        if (w.isRemote && b) {
            w.markBlockForUpdate(x, y, z);
        }
        return b;
    }

    @Override
    public void randomDisplayTick(World w, int x, int y, int z, Random rng) {
        TileEntity te = w.getTileEntity(x, y, z);
        if (te instanceof TileColaEngine) {
            if (((TileColaEngine) te).isGenerating) {
                for (int l = 0; l < 10; ++l) {
                    double d6 = (double) ((float) x + rng.nextFloat());
                    double py = (double) ((float) y + rng.nextFloat());
                    d6 = (double) ((float) z + rng.nextFloat());
                    double mx = 0.0D;
                    double my = 0.0D;
                    double mz = 0.0D;
                    int i1 = rng.nextInt(2) * 2 - 1;
                    int j1 = rng.nextInt(2) * 2 - 1;
                    mx = ((double) rng.nextFloat() - 0.5D) * 0.125D;
                    my = ((double) rng.nextFloat() - 0.5D) * 0.25D;
                    mz = ((double) rng.nextFloat() - 0.5D) * 0.125D;
                    double pz = (double) z + 0.5D + 0.25D * (double) j1;
                    mz = (double) (rng.nextFloat() * 1.0F * (float) j1);
                    double px = (double) x + 0.5D + 0.25D * (double) i1;
                    mx = (double) (rng.nextFloat() * 1.0F * (float) i1);
                    w.spawnParticle("smoke", px, py, pz, mx * 0.125D, my, mz * 0.125D);
                }
            }
        }
    }

    @Override
    public void registerBlockIcons(IIconRegister r) {
        //super.registerBlockIcons(p_149651_1_);
        topIconEmpty = r.registerIcon(getTextureName() + "_empty");
        topIconFull = r.registerIcon(getTextureName() + "_full");
        blockIcon = r.registerIcon(getTextureName() + "_bottom");
        sideIconLowPressure = r.registerIcon(getTextureName() + "_low");
        sideIconHighPressure = r.registerIcon(getTextureName() + "_high");
    }

    @Override
    public IIcon getIcon(IBlockAccess w, int x, int y, int z, int s) {
        TileEntity te;
        switch (s) {
            case 1:
                te = w.getTileEntity(x, y, z);
                if (te instanceof TileColaEngine) {
                    return ((TileColaEngine) te).isTankEmpty ? topIconEmpty : topIconFull;
                }
            case 0:
                return blockIcon;
            default:
                te = w.getTileEntity(x, y, z);
                if (te instanceof TileColaEngine) {
                    return ((TileColaEngine) te).isGenerating ? sideIconHighPressure : sideIconLowPressure;
                }
                return blockIcon;
        }
    }

    @Override
    public IIcon getIcon(int s, int m) {
        switch (s) {
            case 1:
                return topIconEmpty;
            case 0:
                return blockIcon;
            default:
                return sideIconLowPressure;
        }
    }
}
