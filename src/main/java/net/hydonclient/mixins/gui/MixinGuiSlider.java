package net.hydonclient.mixins.gui;

import java.awt.Color;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiPageButtonList.GuiResponder;
import net.minecraft.client.gui.GuiSlider;
import net.minecraft.client.renderer.GlStateManager;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(GuiSlider.class)
public abstract class MixinGuiSlider extends GuiButton {

    @Shadow public boolean isMouseDown;
    @Shadow private float sliderPosition;

    @Shadow protected abstract String getDisplayString();

    @Shadow @Final private GuiResponder responder;

    @Shadow public abstract float func_175220_c();

    public MixinGuiSlider(int buttonId, int x, int y, String buttonText) {
        super(buttonId, x, y, buttonText);
    }

    /**
     * @author Koding
     */
    @Overwrite
    protected void mouseDragged(Minecraft mc, int mouseX, int mouseY) {
        if (this.visible) {
            if (this.isMouseDown) {
                this.sliderPosition =
                    (float) (mouseX - (this.xPosition + 4)) / (float) (this.width - 8);

                if (this.sliderPosition < 0.0F) {
                    this.sliderPosition = 0.0F;
                }

                if (this.sliderPosition > 1.0F) {
                    this.sliderPosition = 1.0F;
                }

                this.displayString = this.getDisplayString();
                this.responder.onTick(this.id, this.func_175220_c());
            }

            GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
            Gui.drawRect(
                this.xPosition + (int) (this.sliderPosition * (float) (this.width - 8)), this.yPosition,
                this.xPosition + (int) (this.sliderPosition * (float) (this.width - 8)) + 4, this.yPosition + 20, new Color(0, 0, 0, 40).getRGB());
        }
    }
}
