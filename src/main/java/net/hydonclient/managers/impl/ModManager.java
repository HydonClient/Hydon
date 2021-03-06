package net.hydonclient.managers.impl;

import me.aycy.blockoverlay.BlockOverlay;
import me.semx11.autotip.Autotip;
import net.hydonclient.Hydon;
import net.hydonclient.mods.accountmanager.AccountManager;
import net.hydonclient.mods.blur.BlurMod;
import net.hydonclient.mods.timechanger.TimeChangerMod;
import net.hydonclient.mods.vanillaenhancements.VanillaEnhancements;
import net.hydonclient.mods.wings.Wings;
import pw.cinque.keystrokesmod.KeystrokesMod;

public class ModManager {

    /**
     * The instance of all the mods
     */
    private BlurMod blurMod;
    private Autotip autotip;
    private Wings wings;
    private VanillaEnhancements vanillaEnhancements;
    private KeystrokesMod keystrokesMod;
    private BlockOverlay blockOverlay;
    private TimeChangerMod timeChangerMod;
    private AccountManager accountManager;

    /**
     * Load all of the mods that are registered
     */
    public void init() {
        Hydon.LOGGER.info("Loading mods");

        blurMod = new BlurMod();
        blurMod.load();

        autotip = new Autotip();
        autotip.load();

        wings = new Wings();
        wings.load();

        vanillaEnhancements = new VanillaEnhancements();
        vanillaEnhancements.load();

        keystrokesMod = new KeystrokesMod();
        keystrokesMod.load();

        blockOverlay = new BlockOverlay();
        blockOverlay.load();

        timeChangerMod = new TimeChangerMod();
        timeChangerMod.load();

        accountManager = new AccountManager();
        accountManager.load();
    }

    public BlurMod getBlurMod() {
        return blurMod;
    }

    public Autotip getAutotip() {
        return autotip;
    }

    public Wings getWings() {
        return wings;
    }

    public VanillaEnhancements getVanillaEnhancements() {
        return vanillaEnhancements;
    }

    public KeystrokesMod getKeystrokesMod() {
        return keystrokesMod;
    }

    public BlockOverlay getBlockOverlay() {
        return blockOverlay;
    }

    public TimeChangerMod getTimeChangerMod() {
        return timeChangerMod;
    }

    public AccountManager getAccountManager() {
        return accountManager;
    }
}
