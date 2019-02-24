package net.hydonclient;

import me.aycy.blockoverlay.utils.BlockOverlayMode;
import net.hydonclient.gui.enums.EnumBackground;
import net.hydonclient.managers.impl.config.SaveVal;

public class Settings {

    @SaveVal
    public boolean FAST_CHAT = false;

    @SaveVal
    public boolean GUI_BLUR = true;

    @SaveVal
    public boolean DISABLE_ARMORSTANDS = false;

    @SaveVal
    public boolean DISABLE_SIGNS = false;

    @SaveVal
    public boolean DISABLE_ITEMFRAMES = false;

    @SaveVal
    public boolean DISABLE_XPORBS = false;

    @SaveVal
    public boolean DISABLE_ALL_PARTICLES = false;

    @SaveVal
    public boolean DISABLE_THROWN_PROJECTILES = false;

    @SaveVal
    public boolean WINDOWED_FULLSCREEN = false;

    @SaveVal
    public boolean TOGGLESPRINT = true;

    @SaveVal
    public boolean STOP_SPRINTING_WHEN_RELEASED = false;

    @SaveVal
    public boolean OLD_DEBUG_MENU = false;

    @SaveVal
    public boolean OLD_BLOCKING = false;

    @SaveVal
    public boolean OLD_ITEM_HOLDING = false;

    @SaveVal
    public boolean OLD_ARMOR = false;

    @SaveVal
    public boolean OLD_DAMAGE_FLASH = false;

    @SaveVal
    public boolean OLD_SNEAKING = false;

    @SaveVal
    public boolean OLD_BOW = false;

    @SaveVal
    public boolean OLD_ROD = false;

    @SaveVal
    public boolean OLD_BLOCK_HITTING = false;

    @SaveVal
    public boolean OLD_EATING = false;

    @SaveVal
    public boolean AMPLIFIER_PREVIEW = true;

    @SaveVal
    public boolean PROTECTION_PREVIEW = true;

    @SaveVal
    public boolean PROJECTILE_PROT_PREVIEW = true;

    @SaveVal
    public boolean ARROW_COUNTER = true;

    @SaveVal
    public boolean DAMAGE_PREVIEW = true;

    @SaveVal
    public boolean THIRD_PERSON_CROSSHAIR = true;

    @SaveVal
    public boolean COMPACT_CHAT = true;

    @SaveVal
    public boolean WINGS = true;

    @SaveVal
    public double WINGS_SCALE = 80D;

    @SaveVal
    public boolean KEYSTROKES_CHROMA = true;

    @SaveVal
    public boolean KEYSTROKES_OUTLINE = true;

    @SaveVal
    public boolean DISABLE_TITLES = false;

    @SaveVal
    public boolean ENABLE_KEYSTROKES = false;

    @SaveVal
    public boolean DISABLE_BOSS_FOOTER = false;

    @SaveVal
    public boolean DISABLE_BOSS_BAR = false;

    @SaveVal
    public boolean DISCORD_RICH_PRESENCE = true;

    @SaveVal
    public boolean REPLACE_DEFAULT_FONT = false;

    @SaveVal
    public boolean DISABLE_SCOREBOARD = false;

    @SaveVal
    public int BLOCKOVERLAY_MODE = BlockOverlayMode.DEFAULT.ordinal();

    @SaveVal
    public boolean BLOCKOVERLAY_PERSISTENT = false;

    @SaveVal
    public boolean BLOCKOVERLAY_CHROMA = false;

    @SaveVal
    public boolean BLOCKOVERLAY_IGNORE_DEPTH = false;

    @SaveVal
    public double BLOCKOVERLAY_LINE_WIDTH = 2.0;

    @SaveVal
    public int BLOCKOVERLAY_RED = 255;

    @SaveVal
    public int BLOCKOVERLAY_GREEN = 255;

    @SaveVal
    public int BLOCKOVERLAY_BLUE = 255;

    @SaveVal
    public int BLOCKOVERLAY_ALPHA = 255;

    @SaveVal
    public int BLOCKOVERLAY_CHROMA_SPEED = 5;

    @SaveVal
    public int CURRENT_BACKGROUND = 0;

    @SaveVal
    public boolean HELD_PERSPECTIVE = true;

    @SaveVal
    public boolean HYDON_BUTTONS = true;

    @SaveVal
    public boolean FULLBRIGHT = false;

    @SaveVal
    public boolean DISABLE_ENCHANTMENTS = false;

    @SaveVal
    public boolean HOTBAR_NUMBERS = false;

    @SaveVal
    public boolean HOTBAR_NUMBER_SHADOW = false;

    @SaveVal
    public boolean CONFIRM_DISCONNECT = false;

    @SaveVal
    public boolean CONFIRM_QUIT = false;

    @SaveVal
    public boolean NUMBER_PING = false;

    @SaveVal
    public boolean LIMIT_FRAMERATE = false;

    @SaveVal
    public String PREVIOUS_SERVER = "";

    @SaveVal
    public boolean confirmDisconnect = false;

    @SaveVal
    public boolean discordRichPresence = true;

    public boolean isBoChroma() {
        return BLOCKOVERLAY_CHROMA;
    }

    public BlockOverlayMode getBoMode() {
        return BlockOverlayMode.values()[BLOCKOVERLAY_MODE];
    }

    public EnumBackground getCurrentBackground() {
        return EnumBackground.values()[CURRENT_BACKGROUND];
    }
}
