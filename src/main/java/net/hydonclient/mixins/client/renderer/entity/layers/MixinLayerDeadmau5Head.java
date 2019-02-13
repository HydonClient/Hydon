package net.hydonclient.mixins.client.renderer.entity.layers;

import net.hydonclient.api.UserManager;
import net.hydonclient.api.objects.EnumCosmetic;
import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderPlayer;
import net.minecraft.client.renderer.entity.layers.LayerDeadmau5Head;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(LayerDeadmau5Head.class)
public class MixinLayerDeadmau5Head {

    @Shadow
    @Final
    private RenderPlayer playerRenderer;

    /**
     * @author Koding
     * @reason Deadmau5 cosmetic
     */
    @Overwrite
    public void doRenderLayer(AbstractClientPlayer entitylivingbaseIn, float p_177141_2_,
        float p_177141_3_, float partialTicks, float p_177141_5_, float p_177141_6_,
        float p_177141_7_,
        float scale) {
        if ((entitylivingbaseIn.getName().equals("deadmau5") || (
            !UserManager.getInstance().isProcessing(entitylivingbaseIn.getUniqueID()) && UserManager
                .getInstance().getUser(entitylivingbaseIn.getUniqueID()).hasUnlockedCosmetic(
                    EnumCosmetic.EARS))) && entitylivingbaseIn.hasSkin()
            && !entitylivingbaseIn.isInvisible()) {
            this.playerRenderer.bindTexture(entitylivingbaseIn.getLocationSkin());

            for (int i = 0; i < 2; ++i) {
                float f = entitylivingbaseIn.prevRotationYaw
                    + (entitylivingbaseIn.rotationYaw - entitylivingbaseIn.prevRotationYaw)
                    * partialTicks - (entitylivingbaseIn.prevRenderYawOffset
                    + (entitylivingbaseIn.renderYawOffset - entitylivingbaseIn.prevRenderYawOffset)
                    * partialTicks);
                float f1 = entitylivingbaseIn.prevRotationPitch
                    + (entitylivingbaseIn.rotationPitch - entitylivingbaseIn.prevRotationPitch)
                    * partialTicks;
                GlStateManager.pushMatrix();
                GlStateManager.rotate(f, 0.0F, 1.0F, 0.0F);
                GlStateManager.rotate(f1, 1.0F, 0.0F, 0.0F);
                GlStateManager.translate(0.375F * (float) (i * 2 - 1), 0.0F, 0.0F);
                GlStateManager.translate(0.0F, -0.375F, 0.0F);
                GlStateManager.rotate(-f1, 1.0F, 0.0F, 0.0F);
                GlStateManager.rotate(-f, 0.0F, 1.0F, 0.0F);
                float f2 = 1.3333334F;
                GlStateManager.scale(f2, f2, f2);
                this.playerRenderer.getMainModel().renderDeadmau5Head(0.0625F);
                GlStateManager.popMatrix();
            }
        }
    }
}
