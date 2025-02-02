package com.impillagers.mod.entity.ai.brain;

import com.mojang.serialization.Codec;
import java.util.Optional;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.brain.*;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class MemoryModuleType<U> extends net.minecraft.entity.ai.brain.MemoryModuleType<Object> {
    public static final MemoryModuleType<LivingEntity> NEAREST_VISIBLE_VILLAGER = (MemoryModuleType<LivingEntity>) register("nearest_visible_villager");
    private final Optional<Codec<Memory<U>>> codec;

    public MemoryModuleType(Optional<Codec<Object>> objectCodec, Optional<Codec<Memory<U>>> codec) {
        super(objectCodec);
        this.codec = codec;
    }

    private static <U> net.minecraft.entity.ai.brain.MemoryModuleType<U> register(String id, Codec<U> codec) {
        return Registry.register(Registries.MEMORY_MODULE_TYPE, Identifier.ofVanilla(id), new net.minecraft.entity.ai.brain.MemoryModuleType<>(Optional.of(codec)));
    }

    private static <U> net.minecraft.entity.ai.brain.MemoryModuleType<U> register(String id) {
        return Registry.register(Registries.MEMORY_MODULE_TYPE, Identifier.ofVanilla(id), new net.minecraft.entity.ai.brain.MemoryModuleType<>(Optional.empty()));
    }
}