package com.impillagers.mod;

import com.impillagers.mod.entity.ModEntities;
import com.impillagers.mod.entity.client.ImpillagerModel;
import com.impillagers.mod.entity.client.ImpillagerRenderer;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.EntityModelLayerRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.minecraft.client.render.RenderLayer;

public class ImpillagersClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {


        EntityModelLayerRegistry.registerModelLayer(ImpillagerModel.IMPILLAGER, ImpillagerModel::getTexturedModelData);
        EntityRendererRegistry.register(ModEntities.IMPILLAGER, ImpillagerRenderer::new);
    }
}
