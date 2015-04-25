package com.github.soniex2.cola;

import com.github.soniex2.cola.init.ColaBlocks;
import com.github.soniex2.cola.init.ColaFluids;
import com.github.soniex2.cola.init.ColaTiles;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;

/**
 * @author soniex2
 */
@Mod(modid = "cola", name = "Cola", version = "1.0")
public class Cola {
    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        ColaFluids.init();
        ColaBlocks.init();
        ColaTiles.init();
    }
}
