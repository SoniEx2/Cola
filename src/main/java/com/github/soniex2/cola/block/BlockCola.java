package com.github.soniex2.cola.block;

import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fluids.BlockFluidClassic;
import net.minecraftforge.fluids.Fluid;

import java.util.Random;

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
        //System.out.println("Registering icons");
        icons[0] = p_149651_1_.registerIcon(this.getTextureName());
        icons[1] = p_149651_1_.registerIcon(this.getTextureName() + "_flow");
        //System.out.println(icons[0]);
        //System.out.println(icons[1]);
        this.getFluid().setIcons(icons[0], icons[1]);
    }

    @Override
    public IIcon getIcon(int s, int m) {
        return s != 0 && s != 1 ? icons[1] : icons[0];
    }

    @Override
    public void randomDisplayTick(World p_149734_1_, int p_149734_2_, int p_149734_3_, int p_149734_4_, Random p_149734_5_) {
        int l;

        if (p_149734_5_.nextInt(10) == 0) {
            l = p_149734_1_.getBlockMetadata(p_149734_2_, p_149734_3_, p_149734_4_);

            if (l <= 0 || l >= 8) {
                p_149734_1_.spawnParticle("suspended", (double) ((float) p_149734_2_ + p_149734_5_.nextFloat()), (double) ((float) p_149734_3_ + p_149734_5_.nextFloat()), (double) ((float) p_149734_4_ + p_149734_5_.nextFloat()), 0.0D, 0.0D, 0.0D);
            }
        }

        if (p_149734_1_.getBlock(p_149734_2_, p_149734_3_ + 1, p_149734_4_).getMaterial() == Material.air) {
            for (l = 0; l < 0; l++) {
                // doesn't work because of particle glitch >.>
                double d0 = (double) ((float) p_149734_2_ + p_149734_5_.nextFloat());
                double d1 = (double) ((float) p_149734_3_ + this.getQuantaPercentage(p_149734_1_, p_149734_2_, p_149734_3_, p_149734_4_) + p_149734_5_.nextFloat());
                double d2 = (double) ((float) p_149734_4_ + p_149734_5_.nextFloat());
                p_149734_1_.spawnParticle("bubble", d0, d1, d2, 0.0, 0.5, 0.0);
            }
        }

        for (l = 0; l < 0; ++l) {
            int i1 = p_149734_5_.nextInt(4);
            int j1 = p_149734_2_;
            int k1 = p_149734_4_;

            switch (i1) {
                case 0:
                    j1 = p_149734_2_ - 1;
                    break;
                case 1:
                    ++j1;
                    break;
                case 2:
                    k1 = p_149734_4_ - 1;
                    break;
                case 3:
                    ++k1;
                    break;
            }

            if (p_149734_1_.getBlock(j1, p_149734_3_, k1).getMaterial() == Material.air && (p_149734_1_.getBlock(j1, p_149734_3_ - 1, k1).getMaterial().blocksMovement() || p_149734_1_.getBlock(j1, p_149734_3_ - 1, k1).getMaterial().isLiquid())) {
                float f = 0.0625F;
                double d0 = (double) ((float) p_149734_2_ + p_149734_5_.nextFloat());
                double d1 = (double) ((float) p_149734_3_ + p_149734_5_.nextFloat());
                double d2 = (double) ((float) p_149734_4_ + p_149734_5_.nextFloat());

                switch (i1) {
                    case 0:
                        d0 = (double) ((float) p_149734_2_ - f);
                        break;
                    case 1:
                        d0 = (double) ((float) (p_149734_2_ + 1) + f);
                        break;
                    case 2:
                        d2 = (double) ((float) p_149734_4_ - f);
                        break;
                    case 3:
                        d2 = (double) ((float) (p_149734_4_ + 1) + f);
                        break;
                }

                double d3 = 0.0D;
                double d4 = 0.0D;

                switch (i1) {
                    case 0:
                        d3 = (double) (-f);
                        break;
                    case 1:
                        d3 = (double) f;
                        break;
                    case 2:
                        d4 = (double) (-f);
                        break;
                    case 3:
                        d4 = (double) f;
                        break;
                }

                p_149734_1_.spawnParticle("splash", d0, d1, d2, d3, 0.0D, d4);
            }
        }
        if (p_149734_5_.nextInt(64) == 0) {
            l = p_149734_1_.getBlockMetadata(p_149734_2_, p_149734_3_, p_149734_4_);

            if (l > 0 && l < 8) {
                p_149734_1_.playSound((double) ((float) p_149734_2_ + 0.5F), (double) ((float) p_149734_3_ + 0.5F), (double) ((float) p_149734_4_ + 0.5F), "liquid.water", p_149734_5_.nextFloat() * 0.25F + 0.75F, p_149734_5_.nextFloat() * 1.0F + 0.5F, false);
            }
        }
        double d5;
        double d6;
        double d7;
        if (p_149734_5_.nextInt(10) == 0 && World.doesBlockHaveSolidTopSurface(p_149734_1_, p_149734_2_, p_149734_3_ - 1, p_149734_4_) && !p_149734_1_.getBlock(p_149734_2_, p_149734_3_ - 2, p_149734_4_).getMaterial().blocksMovement()) {
            d5 = (double) ((float) p_149734_2_ + p_149734_5_.nextFloat());
            d6 = (double) p_149734_3_ - 1.05D;
            d7 = (double) ((float) p_149734_4_ + p_149734_5_.nextFloat());

            p_149734_1_.spawnParticle("dripWater", d5, d6, d7, 0.0D, 0.0D, 0.0D);
        }
    }
}
