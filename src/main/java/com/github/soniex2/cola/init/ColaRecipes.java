package com.github.soniex2.cola.init;

import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;

/**
 * @author soniex2
 */
public class ColaRecipes {

    public static void init() {
        GameRegistry.addShapedRecipe(new ItemStack(ColaItems.cola_bottle), " P ", "DbS", " B ", 'P', Items.redstone, 'D', new ItemStack(Items.dye, 1, 3), 'b', Items.glass_bottle, 'S', Items.sugar, 'B', Items.water_bucket);
        GameRegistry.addShapedRecipe(new ItemStack(ColaBlocks.cola_engine), "###", "#x#", "###", '#', Items.iron_ingot, 'x', ColaItems.cola_bottle);
    }
}
