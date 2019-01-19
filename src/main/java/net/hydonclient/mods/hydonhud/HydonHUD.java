package net.hydonclient.mods.hydonhud;

import net.hydonclient.event.EventBus;
import net.hydonclient.mods.Mod;
import net.hydonclient.mods.Mod.Info;
import net.hydonclient.mods.hydonhud.config.HydonHUDSettings;
import net.hydonclient.mods.hydonhud.modules.CoordinateDisplay;
import net.hydonclient.mods.hydonhud.modules.FPSDisplay;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;

@Info(name = "Hydon Hud", author = "asbyth", version = "1.0")
public class HydonHUD extends Mod {

    private HydonHUDSettings config;
    private Minecraft mc;
    private FontRenderer fontRenderer;

    @Override
    public void load() {
        config = new HydonHUDSettings();
        mc = Minecraft.getMinecraft();
        fontRenderer = mc.fontRendererObj;
        EventBus.register(new CoordinateDisplay(this));
        EventBus.register(new FPSDisplay(this));
    }

    public HydonHUDSettings getConfig() {
        return config;
    }
    public Minecraft getMinecraft() {
        return mc;
    }
    public FontRenderer getFontRenderer() {
        return fontRenderer;
    }
    public void drawString(String text) {
       getFontRenderer().drawString(text, 1, 1, 16777215);
    }
    public void drawStringWithShadow(String text) {
        getFontRenderer().drawStringWithShadow(text, 1, 1, 16777215);
    }
}
