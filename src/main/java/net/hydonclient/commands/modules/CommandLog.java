package net.hydonclient.commands.modules;

import net.hydonclient.managers.impl.command.Command;
import net.hydonclient.util.maps.ChatColor;
import net.minecraft.client.Minecraft;

import java.awt.*;
import java.awt.datatransfer.StringSelection;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;

public class CommandLog extends Command {
    @Override
    public String getName() {
        return "log";
    }

    @Override
    public String getUsage() {
        return "/log";
    }

    /**
     * Used to copy the players latest logs so we can use it to detect an issue
     *
     * @param args arguments after /log
     */
    @Override
    public void onCommand(String[] args) {
        StringBuilder message =
                new StringBuilder();

        try {
            FileReader fr =
                    new FileReader(
                            new File(Minecraft.getMinecraft().mcDataDir,
                                    "logs" + File.separator + "latest.log"));

            BufferedReader br =
                    new BufferedReader(fr);

            String line;

            while ((line = br.readLine()) != null) {
                message.append(line).append("\n");
            }

            br.close();
            fr.close();
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }

        message = new StringBuilder(message.toString().
                replace(System.getProperty("user.name"), "{USERNAME}"));

        Toolkit.getDefaultToolkit().getSystemClipboard().setContents(
                new StringSelection(message.toString()), null);

        sendMsg("Log copied to clipboard. Paste it on https://hastebin.com using ctrl + v. Hastebin will open shortly." +
                ChatColor.GRAY + "\nIf it hasn't opened within the next 15 seconds, please go there yourself and paste it.");

        Desktop desktop = Desktop.isDesktopSupported() ? Desktop.getDesktop() : null;

        if (desktop != null && desktop.isSupported(
                Desktop.Action.BROWSE)) {
            try {
                desktop.browse(
                        new URL("https://hastebin.com").
                                toURI());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
