package com.impillagers.mod.entity.client;


import com.google.common.collect.ImmutableMap;
import java.util.Map;

import com.impillagers.mod.Impillagers;
import com.impillagers.mod.entity.custom.ImpillagerEntity;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.feature.FeatureRenderer;
import net.minecraft.client.render.entity.feature.FeatureRendererContext;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import net.minecraft.village.VillagerProfession;
import com.impillagers.mod.villager.ModVillagers;

@Environment(EnvType.CLIENT)
public class ImpillagerProfessionFeatureRenderer extends FeatureRenderer<ImpillagerEntity,ImpillagerModel<ImpillagerEntity>> {

    private static final Map<VillagerProfession, Identifier> PROFESSION_TEXTURES = ImmutableMap.ofEntries(
            Map.entry(VillagerProfession.ARMORER,
            Identifier.of(Impillagers.MOD_ID, "textures/entity/impillager/impillager_armorer.png")),
            Map.entry(VillagerProfession.BUTCHER,
            Identifier.of(Impillagers.MOD_ID, "textures/entity/impillager/impillager_butcher.png")),
            Map.entry(VillagerProfession.CARTOGRAPHER,
            Identifier.of(Impillagers.MOD_ID, "textures/entity/impillager/impillager_cartographer.png")),
            Map.entry(VillagerProfession.CLERIC,
            Identifier.of(Impillagers.MOD_ID, "textures/entity/impillager/impillager_cleric.png")),
            Map.entry(VillagerProfession.FARMER,
            Identifier.of(Impillagers.MOD_ID, "textures/entity/impillager/impillager_farmer.png")),
            Map.entry(VillagerProfession.FISHERMAN,
            Identifier.of(Impillagers.MOD_ID, "textures/entity/impillager/impillager_fisherman.png")),
            Map.entry(VillagerProfession.FLETCHER,
            Identifier.of(Impillagers.MOD_ID, "textures/entity/impillager/impillager_fletcher.png")),
            Map.entry(VillagerProfession.LEATHERWORKER,
            Identifier.of(Impillagers.MOD_ID, "textures/entity/impillager/impillager_leatherworker.png")),
            Map.entry(VillagerProfession.MASON,
            Identifier.of(Impillagers.MOD_ID, "textures/entity/impillager/impillager_mason.png")),
            Map.entry(VillagerProfession.SHEPHERD,
            Identifier.of(Impillagers.MOD_ID, "textures/entity/impillager/impillager_shepherd.png")),
            Map.entry(VillagerProfession.TOOLSMITH,
            Identifier.of(Impillagers.MOD_ID, "textures/entity/impillager/impillager_toolsmith.png")),
            Map.entry(VillagerProfession.WEAPONSMITH,
            Identifier.of(Impillagers.MOD_ID, "textures/entity/impillager/impillager_weaponsmith.png")),
            Map.entry(VillagerProfession.NITWIT,
            Identifier.of(Impillagers.MOD_ID, "textures/entity/impillager/impillager_nitwit.png")),
            Map.entry(ModVillagers.DUNG_COLLECTOR,
            Identifier.of(Impillagers.MOD_ID, "textures/entity/impillager/impillager_dung_collector.png"))
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

        VillagerProfession impillagerProfession = impillagerEntity.getVillagerData().getProfession();

        if (impillagerProfession != VillagerProfession.NONE) {
            Identifier identifier = (Identifier) PROFESSION_TEXTURES.get(impillagerProfession);
            renderModel(this.getContextModel(), identifier, matrixStack, vertexConsumerProvider, light, impillagerEntity, -1);
        }
    }
}

