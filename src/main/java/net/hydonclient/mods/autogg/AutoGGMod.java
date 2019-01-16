package net.hydonclient.mods.autogg;

import net.hydonclient.event.EventBus;
import net.hydonclient.managers.HydonManagers;
import net.hydonclient.mods.Mod;
import net.hydonclient.mods.Mod.Info;
import net.hydonclient.mods.autogg.command.AutoGGCommand;
import net.hydonclient.mods.autogg.config.AutoGGConfig;

@Info(name = "Auto GG", version = "0.1", author = "Koding")
public class AutoGGMod extends Mod {

    private AutoGGConfig config;

    @Override
    public void load() {
        HydonManagers.INSTANCE.getConfigManager().register(config = new AutoGGConfig());
        EventBus.register(new AutoGGListener());
        HydonManagers.INSTANCE.getCommandManager().register(new AutoGGCommand());
    }

    public AutoGGConfig getConfig() {
        return config;
    }
}
