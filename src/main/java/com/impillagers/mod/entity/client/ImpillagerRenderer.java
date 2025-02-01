package com.impillagers.mod.entity.client;

import com.impillagers.mod.Impillagers;
import com.impillagers.mod.entity.custom.ImpillagerEntity;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;

@Environment(EnvType.CLIENT)
public class ImpillagerRenderer extends MobEntityRenderer<ImpillagerEntity, ImpillagerModel<ImpillagerEntity>> {

    private static final Identifier TEXTURE = Identifier.of(Impillagers.MOD_ID, "textures/entity/impillager/impillager.png");

    public ImpillagerRenderer(EntityRendererFactory.Context context) {
        super(context, new ImpillagerModel<>(context.getPart(ImpillagerModel.IMPILLAGER)), 0.4f);
        this.addFeature(new ImpillagerProfessionFeatureRenderer(this));
    }

    @Override
    public Identifier getTexture(ImpillagerEntity entity) {return TEXTURE; }

    protected void scale(ImpillagerEntity impillagerEntity, MatrixStack matrixStack, float f) {
        float g = 0.9375F * impillagerEntity.getScaleFactor();
        matrixStack.scale(g, g, g);
    }

    protected float getShadowRadius(ImpillagerEntity impillagerEntity) {
        float f = super.getShadowRadius(impillagerEntity);
        return impillagerEntity.isBaby() ? f * 0.5F : f;
    }
}
