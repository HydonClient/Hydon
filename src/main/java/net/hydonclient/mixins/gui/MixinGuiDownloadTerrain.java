package net.hydonclient.mixins.gui;

import net.hydonclient.util.GuiUtils;
import net.minecraft.client.gui.GuiDownloadTerrain;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.resources.I18n;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

@Mixin(GuiDownloadTerrain.class)
public class MixinGuiDownloadTerrain extends GuiScreen {

    /**
     * @author Koding
     */
    @Overwrite
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        GuiUtils.drawBG(true);
        this.drawCenteredString(this.fontRendererObj,
            I18n.format("multiplayer.downloadingTerrain", new Object[0]), this.width / 2,
            this.height / 2 - 50, 16777215);
        super.drawScreen(mouseX, mouseY, partialTicks);
    }
}
