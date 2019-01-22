package net.hydonclient.mixins.client;

import net.hydonclient.util.GuiUtils;
import net.hydonclient.util.maps.Images;
import net.hydonclient.util.ResolutionUtil;
import net.minecraft.client.LoadingScreenRenderer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.client.shader.Framebuffer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(LoadingScreenRenderer.class)
public class MixinLoadingScreenRenderer {

    @Shadow
    private Minecraft mc;
    @Shadow
    private long systemTime;
    @Shadow
    private Framebuffer framebuffer;
    @Shadow
    private String currentlyDisplayedText;
    @Shadow
    private String message;

    /**
     * @author KodingKing
     */
    @Overwrite
    public void setLoadingProgress(int progress) {
        long i = Minecraft.getSystemTime();

        if (i - this.systemTime >= 100L) {
            this.systemTime = i;
            ScaledResolution scaledresolution = new ScaledResolution(this.mc);
            int j = scaledresolution.getScaleFactor();
            int k = scaledresolution.getScaledWidth();
            int l = scaledresolution.getScaledHeight();

            if (OpenGlHelper.isFramebufferEnabled()) {
                this.framebuffer.framebufferClear();
            } else {
                GlStateManager.clear(256);
            }

            this.framebuffer.bindFramebuffer(false);
            GlStateManager.matrixMode(5889);
            GlStateManager.loadIdentity();
            GlStateManager.ortho(0.0D, scaledresolution.getScaledWidth_double(),
                    scaledresolution.getScaledHeight_double(), 0.0D, 100.0D, 300.0D);
            GlStateManager.matrixMode(5888);
            GlStateManager.loadIdentity();
            GlStateManager.translate(0.0F, 0.0F, -200.0F);

            if (!OpenGlHelper.isFramebufferEnabled()) {
                GlStateManager.clear(16640);
            }

            Tessellator tessellator = Tessellator.getInstance();
            WorldRenderer worldrenderer = tessellator.getWorldRenderer();

            GuiUtils.drawBG(true);

            if (this.currentlyDisplayedText.isEmpty() && this.message.isEmpty()) {
                GlStateManager.enableAlpha();
                this.mc.getTextureManager().bindTexture(Images.LOGO.getLocation());
                double logoScaleFactor = ResolutionUtil.getImageScaleFactor();
                int logoWidth = (int) (Images.LOGO.getWidth() * logoScaleFactor);
                int logoHeight = (int) (Images.LOGO.getHeight() * logoScaleFactor);
                int logoX = (ResolutionUtil.getCurrent().getScaledWidth() - logoWidth) / 2;
                int logoY = (ResolutionUtil.getCurrent().getScaledHeight() + logoHeight) / 4;

                GlStateManager.enableBlend();
                GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);

                GlStateManager.color(0.0f, 0.0f, 0.0f, 0.2f);
                Gui.drawModalRectWithCustomSizedTexture(logoX + 2, logoY + 2, 0, 0, logoWidth,
                        logoHeight,
                        logoWidth, logoHeight);

                GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
                Gui.drawModalRectWithCustomSizedTexture(logoX, logoY, 0, 0, logoWidth, logoHeight,
                        logoWidth, logoHeight);
            }

            if (progress >= 0) {
                int i1 = 100;
                int j1 = 2;
                int k1 = k / 2 - i1 / 2;
                int l1 = l / 2 + 16;
                GlStateManager.disableTexture2D();
                worldrenderer.begin(7, DefaultVertexFormats.POSITION_COLOR);
                worldrenderer.pos((double) k1, (double) l1, 0.0D).color(128, 128, 128, 255)
                        .endVertex();
                worldrenderer.pos((double) k1, (double) (l1 + j1), 0.0D).color(128, 128, 128, 255)
                        .endVertex();
                worldrenderer.pos((double) (k1 + i1), (double) (l1 + j1), 0.0D)
                        .color(128, 128, 128, 255)
                        .endVertex();
                worldrenderer.pos((double) (k1 + i1), (double) l1, 0.0D).color(128, 128, 128, 255)
                        .endVertex();
                worldrenderer.pos((double) k1, (double) l1, 0.0D).color(128, 255, 128, 255)
                        .endVertex();
                worldrenderer.pos((double) k1, (double) (l1 + j1), 0.0D).color(128, 255, 128, 255)
                        .endVertex();
                worldrenderer.pos((double) (k1 + progress), (double) (l1 + j1), 0.0D)
                        .color(128, 255, 128, 255).endVertex();
                worldrenderer.pos((double) (k1 + progress), (double) l1, 0.0D)
                        .color(128, 255, 128, 255)
                        .endVertex();
                tessellator.draw();
                GlStateManager.enableTexture2D();
            }

            GlStateManager.enableBlend();
            GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
            this.mc.fontRendererObj.drawStringWithShadow(this.currentlyDisplayedText,
                    (float) ((k - this.mc.fontRendererObj.getStringWidth(this.currentlyDisplayedText))
                            / 2),
                    (float) (l / 2 - 4 - 16), 16777215);
            this.mc.fontRendererObj.drawStringWithShadow(this.message,
                    (float) ((k - this.mc.fontRendererObj.getStringWidth(this.message)) / 2),
                    (float) (l / 2 - 4 + 8), 16777215);
            this.framebuffer.unbindFramebuffer();

            if (OpenGlHelper.isFramebufferEnabled()) {
                this.framebuffer.framebufferRender(k * j, l * j);
            }

            this.mc.updateDisplay();

            try {
                Thread.yield();
            } catch (Exception ignored) {

            }
        }
    }
}
