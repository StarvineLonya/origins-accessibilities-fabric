package com.starvinelonya.oriacs;

import com.starvinelonya.oriacs.registry.OriacsItems;
import io.github.fabricators_of_create.porting_lib.config.ModConfigSpec;
import net.fabricmc.api.ModInitializer;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Oriacs implements ModInitializer {

    public static final String MOD_ID = "oriacs";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);
    public static final OriacsServerConfig CONFIG = new OriacsServerConfig(new ModConfigSpec.Builder());


    @Override
    public void onInitialize() {
        OriacsItems.registerModItems();
    }
}
