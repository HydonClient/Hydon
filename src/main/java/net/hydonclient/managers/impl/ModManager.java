package net.hydonclient.managers.impl;

import me.semx11.autotip.Autotip;
import net.hydonclient.Hydon;
import net.hydonclient.mods.autogg.AutoGGMod;
import net.hydonclient.mods.blur.BlurMod;

public class ModManager {

    private AutoGGMod autoGGMod;
    private BlurMod blurMod;
    private Autotip autotip;

    public void init() {
        Hydon.LOGGER.info("Loading mods");

        autoGGMod = new AutoGGMod();
        autoGGMod.load();

        blurMod = new BlurMod();
        blurMod.load();

        autotip = new Autotip();
        autotip.load();
    }

    public AutoGGMod getAutoGGMod() {
        return autoGGMod;
    }

    public BlurMod getBlurMod() {
        return blurMod;
    }

    public Autotip getAutotip() {
        return autotip;
    }
}
