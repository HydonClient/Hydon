package net.hydonclient.mods.wings;

import net.hydonclient.cosmetics.wings.WingsRenderer;
import net.hydonclient.event.EventBus;
import net.hydonclient.managers.HydonManagers;
import net.hydonclient.managers.impl.config.SaveVal;
import net.hydonclient.mods.Mod;
import net.hydonclient.mods.Mod.Info;

@Info(name = "Wings", author = "Canalex", version = "1.0")
public class Wings extends Mod {

    @SaveVal
    public static boolean WINGS_ENABLED = true;

    @Override
    public void load() {
        EventBus.register(new WingsRenderer());
        HydonManagers.INSTANCE.getConfigManager().register(this);
    }
}