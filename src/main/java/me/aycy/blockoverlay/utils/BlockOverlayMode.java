package me.aycy.blockoverlay.utils;

import net.hydonclient.Hydon;

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

    public static BlockOverlayMode getNextMode() {
        BlockOverlayMode currentMode = Hydon.SETTINGS.boMode;

        return values()[(currentMode.ordinal() + 1) % values().length];
    }

    public String getName() {
        return this.name;
    }
}
