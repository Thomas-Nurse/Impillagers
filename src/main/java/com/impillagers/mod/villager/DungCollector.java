package com.impillagers.mod.villager;

import com.google.common.collect.ImmutableSet;
import net.fabricmc.fabric.api.object.builder.v1.world.poi.PointOfInterestHelper;
import com.impillagers.mod.Impillagers;
import com.impillagers.mod.block.ModBlocks;
import net.minecraft.block.Block;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Identifier;
import net.minecraft.village.VillagerProfession;
import net.minecraft.world.poi.PointOfInterestType;

public class DungCollector {

    public static final RegistryKey<PointOfInterestType> DUNG_COLLECTOR_POI_KEY = registerPoiKey("dung_collector_poi");
    public static final PointOfInterestType DUNG_COLLECTOR_POI = registerPOI("dung_collector_poi", ModBlocks.DUNG_BLOCK);

    public static final VillagerProfession DUNG_COLLECTOR = registerProfession("dung_collector", DUNG_COLLECTOR_POI_KEY);

    private static VillagerProfession registerProfession(String name, RegistryKey<PointOfInterestType> type) {
        return Registry.register(Registries.VILLAGER_PROFESSION, Identifier.of(Impillagers.MOD_ID, name),
                new VillagerProfession(name, entry -> entry.matchesKey(type), entry -> entry.matchesKey(type),
                        ImmutableSet.of(), ImmutableSet.of(), SoundEvents.BLOCK_MUD_HIT));
    }

    private static PointOfInterestType registerPOI(String name, Block block) {
        return PointOfInterestHelper.register(Identifier.of(Impillagers.MOD_ID, name),
                1, 1, block);
    }

    private static RegistryKey<PointOfInterestType> registerPoiKey(String name) {
        return RegistryKey.of(RegistryKeys.POINT_OF_INTEREST_TYPE, Identifier.of(Impillagers.MOD_ID, name));
    }

    public static void registerVillager() {
        Impillagers.LOGGER.info("Registering Dung Collector Villager for " + Impillagers.MOD_ID);
    }
}
