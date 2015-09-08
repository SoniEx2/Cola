package com.github.soniex2.cola;

import com.github.soniex2.cola.init.*;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;

/**
 * @author soniex2
 */
@Mod(modid = "cola", name = "Cola", version = "1.0.0")
public class Cola {
    public static boolean usePulsedRF = true;

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        ColaFluids.init();
        ColaBlocks.init();
        ColaItems.init();
        ColaTiles.init();
    }

    @Mod.EventHandler
    public void init(FMLInitializationEvent event) {
        ColaRecipes.init();
    }
}
