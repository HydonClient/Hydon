package net.hydonclient.commands.modules;

import net.hydonclient.managers.impl.command.Command;

public class CommandGC extends Command {

    @Override
    public String getName() {
        return "gc";
    }

    @Override
    public String getUsage() {
        return "/gc";
    }

    /**
     * Used to call garbage collect
     * Free up some memory easily (incase of low memory)
     *
     * @param args arguments after the usage
     */

    @Override
    public void onCommand(String[] args) {
        System.gc();
        sendMsg("Garbage collect called");
    }
}
