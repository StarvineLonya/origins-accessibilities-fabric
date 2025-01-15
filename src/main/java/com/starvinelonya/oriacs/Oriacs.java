package com.starvinelonya.oriacs;

import com.starvinelonya.oriacs.registry.OriacsItems;
import net.fabricmc.api.ModInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Oriacs implements ModInitializer {

    public static final String MOD_ID = "oriacs";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);


    @Override
    public void onInitialize() {
        OriacsItems.registerModItems();
    }
}
