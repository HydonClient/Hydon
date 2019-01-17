package net.hydonclient.managers.impl;

import net.hydonclient.Hydon;
import net.hydonclient.mods.autogg.AutoGGMod;
import net.hydonclient.mods.blur.BlurMod;

public class ModManager {

    private AutoGGMod autoGGMod;
    private BlurMod blurMod;

    public void init() {
        Hydon.LOGGER.info("Loading mods");

        autoGGMod = new AutoGGMod();
        autoGGMod.load();

        blurMod = new BlurMod();
        blurMod.load();
    }

    public AutoGGMod getAutoGGMod() {
        return autoGGMod;
    }

    public BlurMod getBlurMod() {
        return blurMod;
    }
}
