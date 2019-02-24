package net.hydonclient.mods.timechanger.command;

import net.hydonclient.managers.HydonManagers;
import net.hydonclient.managers.impl.command.Command;
import net.hydonclient.mods.timechanger.config.TimeChangerConfig;
import net.hydonclient.util.ChatUtils;

public class TimeChangerCommand extends Command {

    @Override
    public String getName() {
        return "timechanger";
    }

    @Override
    public String getUsage() {
        return "timechanger <reset|set <new time>|day|night|sunset>";
    }

    @Override
    public void onCommand(String[] args) {
        TimeChangerConfig config = HydonManagers.INSTANCE.getModManager().getTimeChangerMod()
                .getConfig();
        if (args.length == 0) {
            ChatUtils.addChatMessage("Usage: " + getUsage());
        } else if (args[0].equalsIgnoreCase("reset")) {
            config.ENABLED = false;
            config.TIME = -1;
            ChatUtils.addChatMessage("Time changer disabled.");
        } else if (args[0].equalsIgnoreCase("set")) {
            if (args.length == 1) {
                ChatUtils.addChatMessage("Please specify a time.");
            } else {
                try {
                    int time = Integer.parseInt(args[1]);
                    if (time >= 24000) {
                        ChatUtils.addChatMessage("Please enter a time from 0 - 23999");
                    } else {
                        config.TIME = time;
                        config.ENABLED = true;
                        ChatUtils.addChatMessage("Set time to: " + time + ".");
                    }
                } catch (Exception e) {
                    ChatUtils.addChatMessage("Invalid time.");
                }
            }
        } else if (args[0].equalsIgnoreCase("day")) {
            config.TIME = -6000;
            config.ENABLED = true;
            ChatUtils.addChatMessage("Set time to day.");
        } else if (args[0].equalsIgnoreCase("night")) {
            config.TIME = -18000;
            config.ENABLED = true;
            ChatUtils.addChatMessage("Set time to night.");
        } else if (args[0].equalsIgnoreCase("sunset")) {
            config.TIME = -22880;
            config.ENABLED = true;
            ChatUtils.addChatMessage("Set time to sunset.");
        } else {
            ChatUtils.addChatMessage("Unrecognized sub-command.");
            ChatUtils.addChatMessage("Usage: " + getUsage());
        }
    }
}
