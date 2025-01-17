package com.starvinelonya.oriacs;

import com.starvinelonya.oriacs.registry.OriacsItemGroups;
import com.starvinelonya.oriacs.registry.OriacsItems;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.event.player.AttackBlockCallback;
import net.minecraft.util.ActionResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Oriacs implements ModInitializer {

    public static final String MOD_ID = "oriacs";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);


    @Override
    public void onInitialize() {
        OriacsItems.registerModItems();
        OriacsItemGroups.addItemsToItemGroup();
        AttackBlockCallback.EVENT.register((player, world, hand, pos, direction) -> {
            //
            return ActionResult.PASS;
        });
    }
}
