package net.hydonclient;

import net.hydonclient.managers.impl.config.SaveVal;

public class Settings {

    @SaveVal
    public boolean fastChat = false;

    @SaveVal
    public boolean autoGGEnabled = true;

    @SaveVal
    public int autoGGDelay = 2;

    @SaveVal
    public boolean blurEnabled = true;

    @SaveVal
    public boolean disableArmorstands = false;

    @SaveVal
    public boolean disableSigns = false;

    @SaveVal
    public boolean disableItemFrames = false;

    @SaveVal
    public boolean windowedFullscreen;

    @SaveVal
    public boolean togglesprintEnabled = true;

    @SaveVal
    public boolean stopSprintingAfterReleased = false;

    @SaveVal
    public boolean oldDebugMenu = false;

    @SaveVal
    public boolean ampPreview = true;

    @SaveVal
    public boolean protPotential = true;

    @SaveVal
    public boolean projPotential = true;

    @SaveVal
    public boolean arrowCounter = true;

    @SaveVal
    public boolean damagePreview = true;

    @SaveVal
    public boolean thirdPersonCrosshair = true;

    @SaveVal
    public boolean compactChat = true;

    @SaveVal
    public boolean wingsEnabled = true;

    @SaveVal
    public boolean keyStrokesChroma = true;

    @SaveVal
    public boolean keyStrokesOutline = true;

    @SaveVal
    public boolean disableTitles = false;

    @SaveVal
    public boolean enableKeystrokes = false;

    @SaveVal
    public boolean disableBossFooter = false;

    @SaveVal
    public boolean disableBossBar = false;

    @SaveVal
    public boolean discordRichPresence = true;
}
