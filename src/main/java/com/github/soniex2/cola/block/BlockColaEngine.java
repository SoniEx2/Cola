package com.github.soniex2.cola.block;

import com.github.soniex2.cola.tile.TileColaEngine;
import net.minecraft.block.material.Material;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

/**
 * @author soniex2
 */
public class BlockColaEngine extends BlockColaMachine {

    public BlockColaEngine(Material material) {
        super(material);
        this.setBlockName("cola:cola_engine");
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
}
