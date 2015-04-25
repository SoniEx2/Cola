package com.github.soniex2.cola.block;

import net.minecraft.block.material.Material;
import net.minecraftforge.fluids.BlockFluidClassic;
import net.minecraftforge.fluids.Fluid;

/**
 * @author soniex2
 */
public class BlockCola extends BlockFluidClassic {
    public BlockCola(Fluid fluid, Material material) {
        super(fluid, material);
        this.setBlockName("cola:cola");
    }
}
