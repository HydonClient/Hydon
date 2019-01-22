package net.hydonclient.managers.impl.command;

import net.hydonclient.util.ChatUtils;

public abstract class Command {

    /**
     * Get the name of the command
     *
     * @return the name
     */
    public abstract String getName();

    /**
     * Get the usage of the command
     *
     * @return the usage
     */
    public abstract String getUsage();

    /**
     * When the command is done, what happens
     *
     * @param args arguments after the usage
     */
    public abstract void onCommand(String[] args);

    /**
     * Easily send a message without needing all the extra stuff + a chat prefix
     *
     * @param message the message being sent to the user
     */
    protected void sendMsg(String message) {
        ChatUtils.addChatMessage(message, true);
    }

}
