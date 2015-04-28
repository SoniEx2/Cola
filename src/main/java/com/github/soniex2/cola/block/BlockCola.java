package com.github.soniex2.cola.block;

import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fluids.BlockFluidClassic;
import net.minecraftforge.fluids.Fluid;

/**
 * @author soniex2
 */
public class BlockCola extends BlockFluidClassic {
    public BlockCola(Fluid fluid, Material material) {
        super(fluid, material);
        this.setBlockName("cola:cola");
        this.setBlockTextureName("cola:cola");
    }

    @Override
    public boolean isReplaceable(IBlockAccess w, int x, int y, int z) {
        return !isSourceBlock(w, x, y, z);
    }

    @Override
    public void registerBlockIcons(IIconRegister p_149651_1_) {
        super.registerBlockIcons(p_149651_1_);
        this.getFluid().setIcons(this.blockIcon);
    }
}
