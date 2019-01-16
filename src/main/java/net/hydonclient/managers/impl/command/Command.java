package net.hydonclient.managers.impl.command;

import net.hydonclient.util.ChatUtils;

public abstract class Command {

    public abstract String getName();

    public abstract String getUsage();

    public abstract void onCommand(String[] args);

    protected void sendMsg(String message) {
        ChatUtils.addChatMessage(message, true);
    }

}
