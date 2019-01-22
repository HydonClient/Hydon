package net.hydonclient.mods.autogg.command;

import net.hydonclient.Hydon;
import net.hydonclient.managers.impl.command.Command;
import net.hydonclient.util.maps.ChatColor;

public class AutoGGCommand extends Command {

    @Override
    public String getName() {
        return "autogg";
    }

    @Override
    public String getUsage() {
        return "/autogg <Toggle|Delay [New Delay]>";
    }

    @Override
    public void onCommand(String[] args) {
        if (args.length <= 0) {
            sendMsg(ChatColor.RED + getUsage());
        } else if (args[0].equalsIgnoreCase("toggle")) {
            Hydon.SETTINGS.autoGGEnabled = !Hydon.SETTINGS.autoGGEnabled;
            sendMsg((Hydon.SETTINGS.autoGGEnabled ? "Enabled" : "Disabled") + " AutoGG.");
        } else if (args[0].equalsIgnoreCase("delay")) {
            if (args.length == 1) {
                sendMsg("The current delay is " + Hydon.SETTINGS.autoGGDelay + " seconds.");
            } else {
                try {
                    int length = Integer.parseInt(args[1]);
                    if (length > 10 || length < 0) {
                        sendMsg(ChatColor.RED + "Please enter a number 1 - 10.");
                    } else {
                        Hydon.SETTINGS.autoGGDelay = length;
                        sendMsg("Set the delay to " + Hydon.SETTINGS.autoGGDelay + " seconds.");
                    }
                } catch (NumberFormatException e) {
                    sendMsg(ChatColor.RED + "Please enter a valid number.");
                }
            }
        } else {
            sendMsg(ChatColor.RED + "Unrecognised sub-command.");
            sendMsg(ChatColor.RED + getUsage());
        }
    }
}
