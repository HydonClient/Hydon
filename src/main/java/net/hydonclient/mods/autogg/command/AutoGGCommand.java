package net.hydonclient.mods.autogg.command;

import net.hydonclient.managers.HydonManagers;
import net.hydonclient.managers.impl.command.Command;
import net.hydonclient.mods.autogg.config.AutoGGConfig;
import net.hydonclient.util.ChatColor;

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
            AutoGGConfig.ENABLED = !AutoGGConfig.ENABLED;
            sendMsg((AutoGGConfig.ENABLED ? "Enabled" : "Disabled") + " AutoGG.");
        } else if (args[0].equalsIgnoreCase("delay")) {
            if (args.length == 1) {
                sendMsg("The current delay is " + AutoGGConfig.DELAY + " seconds.");
            } else {
                try {
                    int length = Integer.parseInt(args[1]);
                    if (length > 10 || length < 0) {
                        sendMsg(ChatColor.RED + "Please enter a number 1 - 10.");
                    } else {
                        AutoGGConfig.DELAY = length;
                        sendMsg("Set the delay to " + AutoGGConfig.DELAY + " seconds.");
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
