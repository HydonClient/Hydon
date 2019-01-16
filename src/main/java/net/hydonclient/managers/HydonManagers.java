package net.hydonclient.managers;

import java.io.File;
import net.hydonclient.Hydon;
import net.hydonclient.managers.impl.CommandManager;
import net.hydonclient.managers.impl.ModManager;
import net.hydonclient.managers.impl.config.ConfigManager;

public class HydonManagers {

    public static final HydonManagers INSTANCE = new HydonManagers();

    private CommandManager commandManager;
    private ModManager modManager;
    private ConfigManager configManager;

    public void init() {
        configManager = new ConfigManager(new File(Hydon.STORAGE_FOLDER, "config.json"));

        commandManager = new CommandManager();
        commandManager.load();

        modManager = new ModManager();
        modManager.init();

        configManager.load();
    }

    public void close() {
        configManager.save();
    }

    public CommandManager getCommandManager() {
        return commandManager;
    }

    public ModManager getModManager() {
        return modManager;
    }

    public ConfigManager getConfigManager() {
        return configManager;
    }
}
