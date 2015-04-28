package com.github.soniex2.cola.block;

import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraftforge.fluids.BlockFluidClassic;
import net.minecraftforge.fluids.Fluid;

/**
 * @author soniex2
 */
public class BlockCola extends BlockFluidClassic {

    private IIcon[] icons = new IIcon[2];

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
        //super.registerBlockIcons(p_149651_1_);
        System.out.println("Registering icons");
        icons[0] = p_149651_1_.registerIcon(this.getTextureName());
        icons[1] = p_149651_1_.registerIcon(this.getTextureName() + "_flow");
        System.out.println(icons[0]);
        System.out.println(icons[1]);
        this.getFluid().setIcons(icons[0], icons[1]);
    }

    @Override
    public IIcon getIcon(int s, int m) {
        return s != 0 && s != 1 ? icons[1] : icons[0];
    }
}
