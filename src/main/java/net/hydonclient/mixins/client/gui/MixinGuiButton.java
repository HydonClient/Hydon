package net.hydonclient.mixins.client.gui;

import java.awt.Color;

import net.hydonclient.Hydon;
import net.hydonclient.ttf.HydonFonts;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.ResourceLocation;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(GuiButton.class)
public abstract class MixinGuiButton extends Gui {

    @Shadow
    public boolean visible;
    @Shadow
    protected boolean hovered;
    @Shadow
    public int xPosition;
    @Shadow
    public int yPosition;
    @Shadow
    protected int width;
    @Shadow
    protected int height;

    @Final
    @Shadow
    protected static final ResourceLocation buttonTextures = new ResourceLocation("textures/gui/widgets.png");

    @Shadow
    protected int getHoverState(boolean mouseOver) {
        return 0;
    }


    @Shadow
    protected abstract void mouseDragged(Minecraft mc, int mouseX, int mouseY);

    @Shadow
    public String displayString;

    @Shadow
    public boolean enabled;
    private double hoverFade = 0;

    private long prevDeltaTime;

    /**
     * @author KodingKing
     */
    @Overwrite
    public void drawButton(Minecraft mc, int mouseX, int mouseY) {
        if (Hydon.SETTINGS.hydonButtons) {
            if (prevDeltaTime == 0) {
                prevDeltaTime = System.currentTimeMillis();
            }
            if (this.visible) {
                GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
                this.hovered =
                        mouseX >= this.xPosition && mouseY >= this.yPosition
                                && mouseX < this.xPosition + this.width
                                && mouseY < this.yPosition + this.height;

                double hoverInc = (System.currentTimeMillis() - prevDeltaTime) / 2f;
                hoverFade = hovered ? Math.min(100, hoverFade + hoverInc) : Math.max(0, hoverFade - hoverInc);

                Gui.drawRect(xPosition, yPosition, xPosition + width, yPosition + height, new Color(0, 0, 0,
                        (int) (100 - (hoverFade / 2))).getRGB());

                this.mouseDragged(mc, mouseX, mouseY);

                int textColor = enabled ? 255 : 180;

                HydonFonts.PRODUCT_SANS_REGULAR
                        .drawCenteredString(this.displayString, this.xPosition + this.width / 2f,
                                this.yPosition + (this.height - 8) / 2f,
                                new Color(textColor, textColor, textColor, 255).getRGB());

                prevDeltaTime = System.currentTimeMillis();
            }
        } else {
            if (this.visible) {
                FontRenderer fontrenderer = mc.fontRendererObj;
                mc.getTextureManager().bindTexture(buttonTextures);
                GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
                this.hovered = mouseX >= this.xPosition && mouseY >= this.yPosition && mouseX < this.xPosition + this.width && mouseY < this.yPosition + this.height;
                int i = this.getHoverState(this.hovered);
                GlStateManager.enableBlend();
                GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
                GlStateManager.blendFunc(770, 771);
                this.drawTexturedModalRect(this.xPosition, this.yPosition, 0, 46 + i * 20, this.width / 2, this.height);
                this.drawTexturedModalRect(this.xPosition + this.width / 2, this.yPosition, 200 - this.width / 2, 46 + i * 20, this.width / 2, this.height);
                this.mouseDragged(mc, mouseX, mouseY);
                int j = 14737632;

                if (!this.enabled) {
                    j = 10526880;
                } else if (this.hovered) {
                    j = 16777120;
                }

                this.drawCenteredString(fontrenderer, this.displayString, this.xPosition + this.width / 2, this.yPosition + (this.height - 8) / 2, j);
            }
        }
    }
}
