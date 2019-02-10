package net.hydonclient.mixins.client.gui;

import java.awt.Color;

import net.hydonclient.ttf.HydonFonts;
import net.hydonclient.ttf.MinecraftFontRenderer;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiTextField;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(GuiTextField.class)
public abstract class MixinGuiTextField extends Gui {

    @Shadow
    public abstract boolean getVisible();

    @Shadow
    public abstract boolean getEnableBackgroundDrawing();

    @Shadow
    public int xPosition;
    @Shadow
    public int yPosition;
    @Shadow
    @Final
    private int width;
    @Shadow
    @Final
    private int height;
    @Shadow
    private boolean isEnabled;
    @Shadow
    private int enabledColor;
    @Shadow
    private int disabledColor;
    @Shadow
    private int cursorPosition;
    @Shadow
    private int lineScrollOffset;
    @Shadow
    private int selectionEnd;
    @Shadow
    @Final
    private FontRenderer fontRendererInstance;
    @Shadow
    private String text;

    @Shadow
    public abstract int getWidth();

    @Shadow
    private boolean isFocused;
    @Shadow
    private int cursorCounter;
    @Shadow
    private boolean enableBackgroundDrawing;

    @Shadow
    public abstract int getMaxStringLength();

    @Shadow
    protected abstract void drawCursorVertical(int p_146188_1_, int p_146188_2_,
                                               int p_146188_3_, int p_146188_4_);

    /**
     * @author Koding
     */
    @Overwrite
    public void drawTextBox() {
        if (this.getVisible()) {
            if (this.getEnableBackgroundDrawing()) {
                drawRect(this.xPosition, this.yPosition, this.xPosition + this.width,
                        this.yPosition + this.height, new Color(0, 0, 0, 40).getRGB());
            }

            int i = this.isEnabled ? this.enabledColor : this.disabledColor;
            int j = this.cursorPosition - this.lineScrollOffset;
            int k = this.selectionEnd - this.lineScrollOffset;

            MinecraftFontRenderer fontRenderer = HydonFonts.FONT_REGULAR;

            String s = fontRenderer
                    .trimStringToWidth(this.text.substring(this.lineScrollOffset), this.getWidth(), false);
            boolean flag = j >= 0 && j <= s.length();
            boolean flag1 = this.isFocused && this.cursorCounter / 6 % 2 == 0 && flag;
            int l = this.enableBackgroundDrawing ? this.xPosition + 4 : this.xPosition;
            int i1 =
                    this.enableBackgroundDrawing ? this.yPosition + (this.height - 8) / 2 : this.yPosition;
            int j1 = l;

            if (k > s.length()) {
                k = s.length();
            }

            if (s.length() > 0) {
                String s1 = flag ? s.substring(0, j) : s;
                j1 = (int) fontRenderer.drawStringWithShadow(s1, (float) l, (float) i1, i);
            }

            boolean flag2 = this.cursorPosition < this.text.length() || this.text.length() >= this
                    .getMaxStringLength();
            int k1 = j1;

            if (!flag) {
                k1 = j > 0 ? l + this.width : l;
            } else if (flag2) {
                k1 = j1 - 1;
                --j1;
            }

            if (s.length() > 0 && flag && j < s.length()) {
                j1 = (int) fontRenderer
                        .drawStringWithShadow(s.substring(j), (float) j1 + fontRenderer.getStringWidth("|"), (float) i1, i);
            }

            if (flag1) {
                if (flag2) {
                    Gui.drawRect(k1, i1 - 1, k1 + 1, i1 + 1 + fontRenderer.getHeight(),
                            -3092272);
                } else {
                    fontRenderer.drawStringWithShadow("|", (float) k1, (float) i1, i);
                }
            }

            if (k != j) {
                int l1 = l + fontRenderer.getStringWidth(s.substring(0, k));
                this.drawCursorVertical(k1, i1 - 1, l1 - 1,
                        i1 + 1 + this.fontRendererInstance.FONT_HEIGHT);
            }
        }
    }
}
