package net.hydonclient.mods.hydonhud.modules.maps;

import net.hydonclient.gui.main.HydonMainGui;
import net.hydonclient.mods.hydonhud.HydonHUD;

public enum EnumSeparators {

    ASTERISK(" * "),
    DASH(" - "),
    COLON(": "),
    NONE(" ");

    private String separator;
    static HydonHUD hud = new HydonHUD();

    EnumSeparators(String separator) {
        this.separator = separator;
    }

    public static void cycleSeparators() {
        EnumSeparators currentSeparator = hud.getConfig().getSeparator();
        hud.getConfig().POTIONSTATUS_SEPARATOR = (currentSeparator.ordinal() + 1) % values().length;
        HydonMainGui.seperatorButton.setLabel("Separator: " + hud.getConfig().getSeparator().getSeparatorOption());
    }

    public String getSeparatorOption() {
        return this.separator;
    }
}
