package net.hydonclient;

import me.aycy.blockoverlay.utils.BlockOverlayMode;
import net.hydonclient.gui.enums.EnumBackground;
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

    @SaveVal
    public boolean replaceDefaultFont = false;

    @SaveVal
    public boolean disableScoreboard = false;

    @SaveVal
    public int boMode = BlockOverlayMode.DEFAULT.ordinal();

    @SaveVal
    public boolean boPersistent = false;

    @SaveVal
    public boolean boChroma = false;

    @SaveVal
    public boolean boIgnoreDepth = false;

    @SaveVal
    public double boLineWidth = 2.0;

    @SaveVal
    public int boRed = 255;

    @SaveVal
    public int boGreen = 255;

    @SaveVal
    public int boBlue = 255;

    @SaveVal
    public int boAlpha = 255;

    @SaveVal
    public int boChromaSpeed = 5;

    @SaveVal
    public int currentBackground = 0;

    @SaveVal
    public boolean heldPerspective = true;

    @SaveVal
    public boolean hydonButtons = true;

    @SaveVal
    public boolean itemPhysics = false;

    @SaveVal
    public float rotateSpeed = 1.0F;

    public boolean isBoChroma() {
        return boChroma;
    }

    public BlockOverlayMode getBoMode() {
        return BlockOverlayMode.values()[boMode];
    }

    public EnumBackground getCurrentBackground() {
        return EnumBackground.values()[currentBackground];
    }
}
