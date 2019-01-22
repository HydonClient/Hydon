package net.hydonclient.mods.hydonhud.config;

import net.hydonclient.managers.impl.config.SaveVal;

public class HydonHUDSettings {

    /* TODO: add support for uppercase characters */

    /**
     * Coordinate Display Settings
     */
    @SaveVal
    public boolean COORDINATES = false;
    @SaveVal
    public int PRECISION = 1;
    @SaveVal
    public boolean COORDS_PARENTHESES = false;
    @SaveVal
    public int coordsX = 1;
    @SaveVal
    public int coordsY = 1;
    @SaveVal
    public boolean COORDS_SHADOW = false;
    @SaveVal
    public boolean SHOW_COORDS_IN_CHAT = false;
    @SaveVal
    public int COORDS_RED = 255;
    @SaveVal
    public int COORDS_GREEN = 255;
    @SaveVal
    public int COORDS_BLUE = 255;

    /**
     * FPS Display Settings
     */
    @SaveVal
    public boolean FPS = false;
    @SaveVal
    public boolean FPS_PARENTHESES = false;
    @SaveVal
    public int fpsX = 1;
    @SaveVal
    public int fpsY = 1;
    @SaveVal
    public boolean FPS_SHADOW = false;
    @SaveVal
    public boolean SHOW_FPS_IN_CHAT = false;
    @SaveVal
    public int FPS_RED = 255;
    @SaveVal
    public int FPS_GREEN = 255;
    @SaveVal
    public int FPS_BLUE = 255;

    /**
     * Sprint Display Settings
     */
    @SaveVal
    public boolean SPRINT = false;
    @SaveVal
    public boolean SPRINT_PARENTHESES = false;
    @SaveVal
    public boolean SPRINT_SHADOW = false;
    @SaveVal
    public int statusX = 1;
    @SaveVal
    public int statusY = 1;
    @SaveVal
    public boolean SHOW_STATUS_IN_CHAT = false;
    @SaveVal
    public int STATUS_RED = 255;
    @SaveVal
    public int STATUS_GREEN = 255;
    @SaveVal
    public int STATUS_BLUE = 255;
}
