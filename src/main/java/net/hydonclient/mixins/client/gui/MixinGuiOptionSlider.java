package net.hydonclient.mixins.client.gui;

import java.awt.Color;

import net.hydonclient.Hydon;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiOptionSlider;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.settings.GameSettings.Options;
import net.minecraft.util.MathHelper;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(GuiOptionSlider.class)
public class MixinGuiOptionSlider extends GuiButton {

    @Shadow
    public boolean dragging;
    @Shadow
    private float sliderValue;
    @Shadow
    private Options options;

    public MixinGuiOptionSlider(int buttonId, int x, int y, String buttonText) {
        super(buttonId, x, y, buttonText);
    }

    /**
     * @author Koding
     */
    @Overwrite
    protected void mouseDragged(Minecraft mc, int mouseX, int mouseY) {
        if (Hydon.SETTINGS.HYDON_BUTTONS) {
            if (this.visible) {
                if (this.dragging) {
                    this.sliderValue = (float) (mouseX - (this.xPosition + 4)) / (float) (this.width - 8);
                    this.sliderValue = MathHelper.clamp_float(this.sliderValue, 0.0F, 1.0F);
                    float f = this.options.denormalizeValue(this.sliderValue);
                    mc.gameSettings.setOptionFloatValue(this.options, f);
                    this.sliderValue = this.options.normalizeValue(f);
                    this.displayString = mc.gameSettings.getKeyBinding(this.options);
                }

                GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
                Gui.drawRect(
                        this.xPosition + (int) (this.sliderValue * (float) (this.width - 8)), this.yPosition,
                        this.xPosition + (int) (this.sliderValue * (float) (this.width - 8)) + 4, this.yPosition + 20, new Color(0, 0, 0, 40).getRGB());
            }
        } else {
            if (this.visible) {
                if (this.dragging) {
                    this.sliderValue = (float) (mouseX - (this.xPosition + 4)) / (float) (this.width - 8);
                    this.sliderValue = MathHelper.clamp_float(this.sliderValue, 0.0F, 1.0F);
                    float f = this.options.denormalizeValue(this.sliderValue);
                    mc.gameSettings.setOptionFloatValue(this.options, f);
                    this.sliderValue = this.options.normalizeValue(f);
                    this.displayString = mc.gameSettings.getKeyBinding(this.options);
                }

                mc.getTextureManager().bindTexture(buttonTextures);
                GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
                this.drawTexturedModalRect(this.xPosition + (int) (this.sliderValue * (float) (this.width - 8)), this.yPosition, 0, 66, 4, 20);
                this.drawTexturedModalRect(this.xPosition + (int) (this.sliderValue * (float) (this.width - 8)) + 4, this.yPosition, 196, 66, 4, 20);
            }
        }
    }
}
