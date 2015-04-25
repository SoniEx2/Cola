package com.github.soniex2.cola.init;

import com.github.soniex2.cola.block.BlockCola;
import com.github.soniex2.cola.block.BlockColaEngine;
import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.block.material.Material;

/**
 * @author soniex2
 */
@GameRegistry.ObjectHolder("cola")
public class ColaBlocks {
    // dammit forge don't fuck me
    @GameRegistry.ObjectHolder("cola")
    public static BlockCola cola = null;
    //public static final BlockCola cola = new BlockCola(ColaFluids.cola, Material.water);
    public static final BlockColaEngine cola_engine = new BlockColaEngine(Material.iron);

    public static void init() {
        cola = new BlockCola(ColaFluids.cola, Material.water);
        GameRegistry.registerBlock(cola, "cola");
        GameRegistry.registerBlock(cola_engine, "cola_engine");
    }
}
