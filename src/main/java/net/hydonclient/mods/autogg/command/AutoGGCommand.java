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
        AutoGGConfig config = HydonManagers.INSTANCE.getModManager()
            .getAutoGGMod().getConfig();
        if (args.length <= 0) {
            sendMsg(ChatColor.RED + getUsage());
        } else if (args[0].equalsIgnoreCase("toggle")) {
            config.ENABLED = !config.ENABLED;
            sendMsg((config.ENABLED ? "Enabled" : "Disabled") + " AutoGG.");
        } else if (args[0].equalsIgnoreCase("delay")) {
            if (args.length == 1) {
                sendMsg("The current delay is " + config.DELAY + " seconds.");
            } else {
                try {
                    int length = Integer.parseInt(args[1]);
                    if (length > 10 || length < 0) {
                        sendMsg(ChatColor.RED + "Please enter a number 1 - 10.");
                    } else {
                        config.DELAY = length;
                        sendMsg("Set the delay to " + config.DELAY + " seconds.");
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
