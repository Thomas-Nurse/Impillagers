package com.impillagers.mod;

import com.impillagers.mod.block.ModBlocks;
import com.impillagers.mod.entity.ModEntities;
import com.impillagers.mod.entity.custom.ImpillagerEntity;
import com.impillagers.mod.item.ModItems;
import com.impillagers.mod.villager.Banker;
import com.impillagers.mod.villager.DungCollector;
import net.fabricmc.api.ModInitializer;

import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;
import net.fabricmc.fabric.api.object.builder.v1.trade.TradeOfferHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.village.TradeOffer;
import net.minecraft.village.TradedItem;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Impillagers implements ModInitializer {
	public static final String MOD_ID = "impillagers";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitialize() {
		ModItems.registerModItems();
		ModBlocks.registerModBlocks();
		ModEntities.registerModEntities();

		DungCollector.registerVillager();
		Banker.registerVillager();

		FabricDefaultAttributeRegistry.register(ModEntities.IMPILLAGER, ImpillagerEntity.createVillagerAttributes());


		//Trades

		TradeOfferHelper.registerVillagerOffers(Banker.BANKER, 1, factories -> {
			factories.add((entity, random) -> new TradeOffer(
					new TradedItem(Items.EMERALD, 3),
					new ItemStack(ModItems.DUNG_BALL, 8), 7, 2, 0.04f));
	});
	}
}