package com.github.soniex2.cola.init;

import com.github.soniex2.cola.fluid.FluidCola;
import net.minecraftforge.fluids.FluidRegistry;

/**
 * @author soniex2
 */
public class ColaFluids {
    public static final FluidCola cola = new FluidCola("cola:cola");

    public static void init() {
        FluidRegistry.registerFluid(cola);
    }
}
