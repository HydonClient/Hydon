package net.hydonclient.mods.timechanger;

import net.hydonclient.managers.HydonManagers;
import net.hydonclient.mods.Mod;
import net.hydonclient.mods.Mod.Info;
import net.hydonclient.mods.timechanger.command.TimeChangerCommand;
import net.hydonclient.mods.timechanger.config.TimeChangerConfig;

@Info(name = "Time Changer", author = "Koding", version = "1.0")
public class TimeChangerMod extends Mod {

    private TimeChangerConfig config = new TimeChangerConfig();

    @Override
    public void load() {
        HydonManagers.INSTANCE.getCommandManager().register(new TimeChangerCommand());
        HydonManagers.INSTANCE.getConfigManager().register(config);
    }

    public TimeChangerConfig getConfig() {
        return config;
    }
}
