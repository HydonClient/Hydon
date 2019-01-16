package net.hydonclient.mixins.gui;

import net.hydonclient.util.GuiUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiChat;
import net.minecraft.client.gui.GuiIngameMenu;
import net.minecraft.client.gui.GuiOptions;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiShareToLan;
import net.minecraft.client.gui.achievement.GuiAchievement;
import net.minecraft.client.gui.achievement.GuiAchievements;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

@Mixin(GuiScreen.class)
public abstract class MixinGuiScreen {

    /**
     * @author KodingKing
     */
    @Overwrite
    public void drawDefaultBackground() {
        renderBG();
    }

    /**
     * @author KodingKing
     */
    @Overwrite
    public void drawBackground(int tint) {
        renderBG();
    }

    private void renderBG() {
        if (Minecraft.getMinecraft().theWorld != null && Minecraft
            .getMinecraft().theWorld.playerEntities.contains(Minecraft.getMinecraft().thePlayer)) {
            GuiUtils.drawIngameGuiGradient();
        } else {
            GuiUtils.drawBG();
        }
    }

}
