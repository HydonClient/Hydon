package net.hydonclient.managers;

import java.io.File;

import net.hydonclient.Hydon;
import net.hydonclient.managers.impl.CommandManager;
import net.hydonclient.managers.impl.DisplayManager;
import net.hydonclient.managers.impl.KeybindHandler;
import net.hydonclient.managers.impl.ModManager;
import net.hydonclient.managers.impl.config.ConfigManager;

public class HydonManagers {

    /**
     * The HydonManagers instance;
     */
    public static final HydonManagers INSTANCE = new HydonManagers();

    /**
     * The commands manager
     */
    private CommandManager commandManager;

    /**
     * The mod manager
     */
    private ModManager modManager;

    /**
     * The config manager
     */
    private ConfigManager configManager;

    /**
     * The keybind manager
     */
    private KeybindHandler keybindHandler;

    /**
     * The display manager
     */
    private DisplayManager displayManager;

    /**
     * Fired from Hydon#start
     * Starts the config, the commands, the mods and the keybinds.
     */
    public void init() {
        configManager = new ConfigManager(new File(Hydon.STORAGE_FOLDER, "config.json"));
        configManager.register(Hydon.SETTINGS);

        commandManager = new CommandManager();
        commandManager.load();

        keybindHandler = new KeybindHandler();
        keybindHandler.loadBinds();

        modManager = new ModManager();
        modManager.init();

        configManager.load();
        keybindHandler.loadPrevBinds();

        displayManager = new DisplayManager();
    }

    /**
     * When the client is closed, save the configuration
     */
    public void close() {
        configManager.save();
    }

    /**
     * The getCommandManager method for HydonManagers so commands can be properly registered or grabbed
     * without static calls
     *
     * @return the instance
     */
    public CommandManager getCommandManager() {
        return commandManager;
    }

    /**
     * The getModManager method for HydonManagers so mods can be properly registered or grabbed
     * without static calls
     *
     * @return the instance
     */
    public ModManager getModManager() {
        return modManager;
    }

    /**
     * The getConfigManager method for HydonManagers so config can be properly registered, saved, or loaded
     * without static calls
     *
     * @return the instance
     */
    public ConfigManager getConfigManager() {
        return configManager;
    }

    /**
     * The getKeybindHandler method for HydonManagers so config can be properly registered, called, or loaded
     * without static calls
     *
     * @return the instance
     */
    public KeybindHandler getKeybindHandler() {
        return keybindHandler;
    }

    /**
     * The getDisplayManager method, used to display a screen the next ingame tick
     *
     * @return the instance
     */
    public DisplayManager getDisplayManager() {
        return displayManager;
    }
}
