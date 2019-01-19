package net.hydonclient.mixins.gui;

import java.awt.Color;

import net.hydonclient.ttf.HydonFonts;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.renderer.GlStateManager;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

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
    }
}
