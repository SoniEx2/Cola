package com.github.soniex2.cola.init;

import com.github.soniex2.cola.item.ItemColaBottle;
import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidContainerRegistry;
import net.minecraftforge.fluids.FluidStack;

/**
 * @author soniex2
 */
@GameRegistry.ObjectHolder("cola")
public class ColaItems {
    public static final ItemColaBottle cola_bottle = new ItemColaBottle();

    public static void init() {
        GameRegistry.registerItem(cola_bottle, "cola_bottle");
        FluidContainerRegistry.registerFluidContainer(new FluidStack(ColaFluids.cola, 1000), new ItemStack(cola_bottle), new ItemStack(Items.glass_bottle));
    }
}
