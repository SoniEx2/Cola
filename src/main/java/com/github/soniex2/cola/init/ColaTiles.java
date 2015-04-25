package com.github.soniex2.cola.init;

import com.github.soniex2.cola.tile.TileColaEngine;
import cpw.mods.fml.common.registry.GameRegistry;

/**
 * @author soniex2
 */
public class ColaTiles {
    public static void init() {
        GameRegistry.registerTileEntity(TileColaEngine.class, "cola:cola_engine");
    }
}
