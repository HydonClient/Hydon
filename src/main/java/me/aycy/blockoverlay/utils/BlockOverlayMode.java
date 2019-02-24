package me.aycy.blockoverlay.utils;

import net.hydonclient.Hydon;
import net.hydonclient.gui.main.HydonMainGui;

public enum BlockOverlayMode {

    NONE("None"),
    DEFAULT("Default"),
    SIDE_OUTLINE("Side Outline"),
    SIDE_FULL("Side Full"),
    OUTLINE("Outline"),
    FULL("Full");

    private String name;

    BlockOverlayMode(String name) {
        this.name = name;
    }

    public static void cycleNextMode() {
        BlockOverlayMode currentMode = Hydon.SETTINGS.getBoMode();
        Hydon.SETTINGS.BLOCKOVERLAY_MODE = (currentMode.ordinal() + 1) % values().length;
        HydonMainGui.outlineModeButton.setLabel("Outline Mode: " + Hydon.SETTINGS.getBoMode().getName());
    }

    public String getName() {
        return this.name;
    }
}
