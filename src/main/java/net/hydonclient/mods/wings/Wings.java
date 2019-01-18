package net.hydonclient.mods.wings;

import net.hydonclient.cosmetics.wings.WingsRenderer;
import net.hydonclient.event.EventBus;
import net.hydonclient.mods.Mod;
import net.hydonclient.mods.Mod.Info;

@Info(name = "Wings", author = "Canalex", version = "1.0")
public class Wings extends Mod {

    @Override
    public void load() {
        EventBus.register(new WingsRenderer());
    }
}
