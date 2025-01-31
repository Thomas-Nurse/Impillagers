package com.impillagers.mod;

import com.impillagers.block.ModBlocks;
import com.impillagers.mod.item.ModItems;
import net.fabricmc.api.ModInitializer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Impillagers implements ModInitializer {
	public static final String MOD_ID = "impillagers";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitialize() {
		ModItems.registerModItems();
		ModBlocks.registerModBlocks();
	}
}