package net.hydonclient.mods.hydonhud;

import net.hydonclient.event.EventBus;
import net.hydonclient.managers.HydonManagers;
import net.hydonclient.mods.Mod;
import net.hydonclient.mods.Mod.Info;
import net.hydonclient.mods.hydonhud.config.HydonHUDSettings;
import net.hydonclient.mods.hydonhud.modules.CoordinateDisplay;
import net.hydonclient.mods.hydonhud.modules.FPSDisplay;
import net.hydonclient.mods.hydonhud.modules.SprintDisplay;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.Gui;


@Info(name = "Hydon Hud", author = "asbyth", version = "1.0")
public class HydonHUD extends Mod {

    private HydonHUDSettings config;
    private Minecraft mc;
    private FontRenderer fontRenderer;
    private Gui gui;

    /**
     * this is really spaghetti code
     * i recommend not looking if u do not like
     * i'll clean it up sometime soon
     * <p>
     * making all of this without internet so im having a hard time
     * thinking of how to do some of this because its my
     * first time ever making a hud
     * uwu - asbyth
     */

    @Override
    public void load() {
        config = new HydonHUDSettings();
        mc = Minecraft.getMinecraft();
        fontRenderer = mc.fontRendererObj;
        gui = new Gui();
        EventBus.register(new CoordinateDisplay(this));
        EventBus.register(new FPSDisplay(this));
        EventBus.register(new SprintDisplay(this));
        HydonManagers.INSTANCE.getConfigManager().register(config);
    }

    public HydonHUDSettings getConfig() {
        return config;
    }

    public Minecraft getMinecraft() {
        return mc;
    }

    private FontRenderer getFontRenderer() {
        return fontRenderer;
    }

    public void drawString(String text, int x, int y, int color) {
        getFontRenderer().drawString(text, x, y, color);
    }

    public void drawStringWithShadow(String text, int x, int y, int color) {
        getFontRenderer().drawStringWithShadow(text, x, y, color);
    }

    public void drawCenteredString(String text, int width, int color) {
        gui.drawCenteredString(fontRenderer, text, width / 2, 150, color);
    }
}
