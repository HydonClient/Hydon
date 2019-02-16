package net.hydonclient.mods.accountmanager.util;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiTextField;

public class GuiExtendedTextField extends GuiTextField {
    private boolean isSecret = false;
    private boolean showText = false;

    public GuiExtendedTextField(int component, int x, int y, int width, int height) {
        super(component, Minecraft.getMinecraft().fontRendererObj, x, y, width, height);
    }


    public void setSecret(boolean secret) {
        this.isSecret = secret;
    }

    public void showText(boolean show) {
        if (!isSecret) this.showText = true;
        this.showText = show;
    }

    public boolean isTextShowing() {
        if (!isSecret) return true;
        return this.showText;
    }

    public boolean isSecret() {
        return this.isSecret;
    }

    @Override
    public void drawTextBox() {
        boolean canShow = isSecret && !showText;
        String oldText = "";
        if (canShow) {
            oldText = getText();
            StringBuilder newText = new StringBuilder();
            for (int i = 0; i < getText().length(); i++) {
                newText.append("*");
            }
            setText(newText.toString());
        }

        super.drawTextBox();

        if (canShow) {
            setText(oldText);
        }
    }
}
