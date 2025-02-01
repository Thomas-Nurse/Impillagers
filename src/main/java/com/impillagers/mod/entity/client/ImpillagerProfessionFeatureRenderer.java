package com.impillagers.mod.entity.client;


import com.google.common.collect.ImmutableMap;
import java.util.Map;

import com.impillagers.mod.Impillagers;
import com.impillagers.mod.entity.custom.ImpillagerEntity;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.feature.FeatureRenderer;
import net.minecraft.client.render.entity.feature.FeatureRendererContext;
import net.minecraft.client.render.entity.model.WolfEntityModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.Identifier;
import net.minecraft.village.VillagerProfession;
import com.impillagers.mod.villager.ModVillagers;

@Environment(EnvType.CLIENT)
public class ImpillagerProfessionFeatureRenderer extends FeatureRenderer<ImpillagerEntity,ImpillagerModel<ImpillagerEntity>> {

    private static final Map<VillagerProfession, Identifier> PROFESSION_TEXTURES = ImmutableMap.of(
            VillagerProfession.ARMORER,
            Identifier.of(Impillagers.MOD_ID, "textures/entity/impillager/impillager_armorer.png")
    );

    public ImpillagerProfessionFeatureRenderer(FeatureRendererContext<ImpillagerEntity, ImpillagerModel<ImpillagerEntity>> featureRendererContext) {
        super(featureRendererContext);
    }

    @Override
    public void render(
            MatrixStack matrixStack,
            VertexConsumerProvider vertexConsumerProvider,
            int light,
            ImpillagerEntity impillagerEntity,
            float limbAngle,
            float limbDistance,
            float tickDelta,
            float animationProgress,
            float headYaw,
            float headPitch
    ) {
       // if (!ImpillagerEntity.isInvisible()) {
            VillagerProfession impillagerProfession = impillagerEntity.getVillagerData().getProfession();
            if (impillagerProfession != VillagerProfession.NONE) {
                Identifier identifier = (Identifier) PROFESSION_TEXTURES.get(impillagerProfession);
                renderModel(this.getContextModel(), identifier, matrixStack, vertexConsumerProvider, light, impillagerEntity, -1);
           // }
        }
    }
}
