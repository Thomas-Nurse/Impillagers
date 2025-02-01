package com.impillagers.mod.item;

import com.impillagers.mod.Impillagers;
import com.impillagers.mod.entity.ModEntities;
import com.impillagers.mod.item.custom.DungBallItem;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroups;
import net.minecraft.item.SpawnEggItem;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class ModItems {

    public static final Item DUNG_BALL = registerItem("dung_ball", new DungBallItem(new Item.Settings()));
    public static final Item IMPILLAGER_SPAWN_EGG = registerItem("impillager_spawn_egg", new SpawnEggItem(ModEntities.IMPILLAGER, 0x995F40, 0xDB635F, new Item.Settings()));

    private static Item registerItem(String name, Item item) {
        return Registry.register(Registries.ITEM, Identifier.of(Impillagers.MOD_ID, name), item);
    }
    public static void registerModItems() {
        Impillagers.LOGGER.info("Registering Mod Items for " + Impillagers.MOD_ID);

        ItemGroupEvents.modifyEntriesEvent(ItemGroups.COMBAT).register(fabricItemGroupEntries -> {
            fabricItemGroupEntries.add(DUNG_BALL);
        });
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.SPAWN_EGGS).register(fabricItemGroupEntries -> {
            fabricItemGroupEntries.add(IMPILLAGER_SPAWN_EGG);
        });
    }
}
