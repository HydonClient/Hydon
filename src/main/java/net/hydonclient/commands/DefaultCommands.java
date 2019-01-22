package net.hydonclient.commands;

import net.hydonclient.commands.modules.CommandLog;
import net.hydonclient.managers.HydonManagers;

public class DefaultCommands {

    private static DefaultCommands instance = new DefaultCommands();

    /**
     * Register any command that isn't through a mod
     */
    public void load() {
        HydonManagers.INSTANCE.getCommandManager().register(
                new CommandLog());
    }

    public static DefaultCommands getInstance() {
        return instance;
    }
}
