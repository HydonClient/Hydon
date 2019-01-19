package net.hydonclient.mods.hydonhud.config;

import net.hydonclient.managers.impl.config.SaveVal;

public class HydonHUDSettings {

    @SaveVal
    public boolean SHADOW = false;

    /**
     * Coordinate Display Settings
     * TODO: add support for showing with chat open & positioning
     */
    @SaveVal
    public boolean COORDINATES = true;
    @SaveVal
    public int PRECISION = 1;
    @SaveVal
    public int SURROUNDING_CHARS = 0;
    @SaveVal
    public int coordsX = 1;
    @SaveVal
    public int coordsY = 1;

    /**
     * FPS Display Settings
     * TODO: add support for showing with chat open & positioning
     */
    @SaveVal
    public boolean FPS = true;
    @SaveVal
    public int fpsX = 1;
    @SaveVal
    public int fpsY = 1;
}
