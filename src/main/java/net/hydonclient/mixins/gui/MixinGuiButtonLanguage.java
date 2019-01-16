package net.hydonclient.mixins.gui;

import java.awt.Color;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiButtonLanguage;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.ResourceLocation;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

@Mixin(GuiButtonLanguage.class)
public class MixinGuiButtonLanguage extends GuiButton {

    public MixinGuiButtonLanguage(int buttonId, int x, int y, String buttonText) {
        super(buttonId, x, y, buttonText);
    }

    private double hoverFade = 0;
    private long prevDeltaTime;

    /**
     * @author Koding
     */
    @Overwrite
    public void drawButton(Minecraft mc, int mouseX, int mouseY) {
        if (prevDeltaTime == 0) {
            prevDeltaTime = System.currentTimeMillis();
        }

        if (this.visible) {
            this.hovered =
                mouseX >= this.xPosition && mouseY >= this.yPosition
                    && mouseX < this.xPosition + this.width
                    && mouseY < this.yPosition + this.height;

            double hoverInc = (System.currentTimeMillis() - prevDeltaTime) / 2f;
            if (this.hovered && hoverFade + hoverInc <= 100) {
                hoverFade += hoverInc;
            } else if (!this.hovered && hoverFade - hoverInc >= 0) {
                hoverFade -= hoverInc;
            }

            Gui.drawRect((int) (xPosition + (hoverFade / 10)), yPosition,
                (int) (xPosition + width - (hoverFade / 10)), yPosition + height, new Color(0, 0, 0,
                    (int) (60 - (hoverFade / 4))).getRGB());

            int padding = 2;
            mc.getTextureManager().bindTexture(new ResourceLocation("textures/language_icon.png"));
            GlStateManager.color(1.0f, 1.0f, 1.0f);
            drawModalRectWithCustomSizedTexture(xPosition + padding, yPosition + padding, 0, 0,
                width - padding * 2, height - padding * 2, width - padding * 2,
                height - padding * 2);
        }
    }
}
