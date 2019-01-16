package net.hydonclient.managers.impl;

import net.hydonclient.Hydon;
import net.hydonclient.mods.autogg.AutoGGMod;

public class ModManager {

    private AutoGGMod autoGGMod;

    public void init() {
        Hydon.LOGGER.info("Loading mods");

        autoGGMod = new AutoGGMod();
        autoGGMod.load();
    }

    public AutoGGMod getAutoGGMod() {
        return autoGGMod;
    }
}
