package net.hydonclient.mods.chatlocker.command;

import net.hydonclient.managers.impl.command.Command;
import net.hydonclient.mods.chatlocker.ChatLocker;
import net.hydonclient.util.maps.ChatColor;

public class ChatCommand extends Command {

    @Override
    public String getName() {
        return "chatlocker";
    }

    @Override
    public String getUsage() {
        return "/chatlocker <prefix>";
    }

    @Override
    public void onCommand(String[] args) {

        try {
            if (args.length <= 0) {
                sendMsg(ChatColor.RED + getUsage());
            }
            if (!args[0].equalsIgnoreCase("") && args.length <= 1) {
                ChatLocker.setPrefix(args[0]);
                sendMsg("Message prefix will now be " + ChatColor.GOLD + ChatLocker.getPrefix());
            }
            if (args.length > 1) {
                sendMsg(ChatColor.RED + getUsage());
            }
        } catch (ArrayIndexOutOfBoundsException ignored) {
        }
    }
}
