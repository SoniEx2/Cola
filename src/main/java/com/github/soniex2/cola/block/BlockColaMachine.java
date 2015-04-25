package com.github.soniex2.cola.block;

import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

/**
 * @author soniex2
 */
public abstract class BlockColaMachine extends BlockColaBase implements ITileEntityProvider {

    protected BlockColaMachine(Material material) {
        super(material);
        this.setHardness(5F);
        this.setResistance(2000F);
    }

    /**
     * Returns a new instance of a block's tile entity class. Called on placing the block.
     *
     * @param world
     * @param meta
     */
    @Override
    public abstract TileEntity createNewTileEntity(World world, int meta);
}
