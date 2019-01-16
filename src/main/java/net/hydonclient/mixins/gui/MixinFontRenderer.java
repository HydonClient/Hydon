package net.hydonclient.mixins.gui;

import com.google.common.base.CharMatcher;
import net.hydonclient.ttf.HydonFonts;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.renderer.GlStateManager;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(FontRenderer.class)
public abstract class MixinFontRenderer {

    @Shadow
    private boolean bidiFlag;

    @Shadow
    protected abstract String bidiReorder(String text);

    @Shadow
    private float red;
    @Shadow
    private float blue;
    @Shadow
    private float green;
    @Shadow
    private float alpha;
    @Shadow
    private float posX;
    @Shadow
    private float posY;

    @Shadow
    protected abstract void renderStringAtPos(String text, boolean shadow);

    /**
     * @author Koding
     */
    @Overwrite
    private int renderString(String text, float x, float y, int color, boolean dropShadow) {
        //TODO: Add option to override font
        if (text == null) {
            return 0;
        } else {
            if (this.bidiFlag) {
                text = this.bidiReorder(text);
            }

            if ((color & -67108864) == 0) {
                color |= -16777216;
            }

            if (dropShadow) {
                color = (color & 16579836) >> 2 | color & -16777216;
            }

            boolean shouldOverride = true;
            shouldOverride = shouldOverride && CharMatcher.ASCII.matchesAllOf(text);

            if (shouldOverride) {
                return (int) HydonFonts.PRODUCT_SANS_REGULAR.drawString(text, x, y, color);
            } else {
                this.red = (float) (color >> 16 & 255) / 255.0F;
                this.blue = (float) (color >> 8 & 255) / 255.0F;
                this.green = (float) (color & 255) / 255.0F;
                this.alpha = (float) (color >> 24 & 255) / 255.0F;
                GlStateManager.color(this.red, this.blue, this.green, this.alpha);
                this.posX = x;
                this.posY = y;
                this.renderStringAtPos(text, dropShadow);
                return (int) this.posX;
            }
        }
    }

    //    @Inject(method = "drawString(Ljava/lang/String;FFIZ)I", at = @At("HEAD"))
//    private void preDrawString(String text, float x, float y, int color, boolean dropShadow, CallbackInfoReturnable<Float> callbackInfo) {
//        GlStateManager.pushAttrib();
//        GlStateManager.pushMatrix();
//        GlStateManager.enableAlpha();
//    }
//
//    @Inject(method = "drawString(Ljava/lang/String;FFIZ)I", at = @At("RETURN"))
//    private void postDrawString(String text, float x, float y, int color, boolean dropShadow, CallbackInfoReturnable<Float> callbackInfo) {
//        GlStateManager.popAttrib();
//        GlStateManager.popMatrix();
//        GlStateManager.disableAlpha();
//    }

}
