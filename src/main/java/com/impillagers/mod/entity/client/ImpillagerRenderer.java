package com.impillagers.mod.entity.client;

import com.impillagers.mod.Impillagers;
import com.impillagers.mod.entity.custom.ImpillagerEntity;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;

@Environment(EnvType.CLIENT)
public class ImpillagerRenderer extends MobEntityRenderer<ImpillagerEntity, ImpillagerModel<ImpillagerEntity>> {

    private static final Identifier TEXTURE = Identifier.of(Impillagers.MOD_ID, "textures/entity/impillager/impillager.png");

    public ImpillagerRenderer(EntityRendererFactory.Context context) {
        super(context, new ImpillagerModel<>(context.getPart(ImpillagerModel.IMPILLAGER)), 0.75f);
        this.addFeature(new ImpillagerProfessionFeatureRenderer(this));
    }

    @Override
    public Identifier getTexture(ImpillagerEntity entity) {return TEXTURE; }
/*
    @Override
    public void render(ImpillagerEntity livingEntity, float f, float g, MatrixStack matrixStack,
                       VertexConsumerProvider vertexConsumerProvider, int i) {
        if (livingEntity.isBaby()) {
            matrixStack.scale(0.5f, 0.5f, 0.5f);
        } else {
            matrixStack.scale(1f, 1f, 1f);
        }

        super.render(livingEntity, f, g, matrixStack, vertexConsumerProvider, i);
    }
 */
}
