package com.github.soniex2.cola.item;

import com.github.soniex2.cola.init.ColaFluids;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.ChatComponentText;
import net.minecraft.world.World;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.IFluidContainerItem;

/**
 * @author soniex2
 */
public class ItemColaBottle extends ItemFood {
    public ItemColaBottle() {
        super(2, 2, false);
        this.setUnlocalizedName("cola:cola_bottle");
        this.setTextureName("cola:cola_bottle");
        this.setAlwaysEdible();
    }

    @Override
    public ItemStack onEaten(ItemStack p_77654_1_, World p_77654_2_, EntityPlayer p_77654_3_) {
        ItemStack is = super.onEaten(p_77654_1_, p_77654_2_, p_77654_3_);
        if (is.stackSize <= 0) {
            return new ItemStack(Items.glass_bottle);
        } else {
            p_77654_3_.inventory.addItemStackToInventory(new ItemStack(Items.glass_bottle));
        }
        return is;
    }

    @Override
    protected void onFoodEaten(ItemStack p_77849_1_, World p_77849_2_, EntityPlayer p_77849_3_) {
        if (!p_77849_2_.isRemote) {
            p_77849_3_.addPotionEffect(new PotionEffect(Potion.moveSpeed.getId(), 600, 0));
            p_77849_3_.addPotionEffect(new PotionEffect(Potion.nightVision.getId(), 100, 0));
        } else {
            p_77849_3_.addChatMessage(new ChatComponentText("SUPER!"));
        }
    }
}
