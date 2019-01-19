package net.hydonclient.integrations.discord;

import net.arikia.dev.drpc.DiscordEventHandlers;
import net.arikia.dev.drpc.DiscordRPC;
import net.arikia.dev.drpc.DiscordRichPresence;
import net.hydonclient.Hydon;
import net.hydonclient.event.EventListener;
import net.hydonclient.event.events.gui.GuiDisplayEvent;
import net.hydonclient.gui.GuiHydonMainMenu;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiChat;
import net.minecraft.client.gui.GuiMultiplayer;

public class DiscordPresence {

    private static final DiscordPresence instance = new DiscordPresence();

    private long startTime;

    private final String details = "IGN: " + Minecraft.getMinecraft().getSession().getUsername();

    public void load() {
        if (!Hydon.SETTINGS.discordRichPresence) {
            return;
        }

        startTime = System.currentTimeMillis();

        DiscordRPC.discordInitialize("535300495640625153", new DiscordEventHandlers(), true);

        new Thread(() -> {
            while (true) {
                DiscordRPC.discordRunCallbacks();
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();

        onDisplayGui(new GuiDisplayEvent(Minecraft.getMinecraft().currentScreen));
    }

    public void shutdown() {
        DiscordRPC.discordClearPresence();
        DiscordRPC.discordShutdown();
    }

    @EventListener
    public void onDisplayGui(GuiDisplayEvent e) {
        if (e.getGuiScreen() instanceof GuiHydonMainMenu) {
            DiscordRPC.discordUpdatePresence(
                new DiscordRichPresence.Builder("Main menu")
                    .setDetails(details)
                    .setStartTimestamps(startTime)
                    .setBigImage("hydon", "Hydon")
                    .build()
            );
        } else if (e.getGuiScreen() instanceof GuiMultiplayer) {
            DiscordRPC.discordUpdatePresence(
                new DiscordRichPresence.Builder("Browsing servers")
                    .setDetails(details)
                    .setStartTimestamps(startTime)
                    .setBigImage("hydon", "Hydon")
                    .build()
            );
        } else if (e.getGuiScreen() instanceof GuiChat) {
            DiscordRPC.discordUpdatePresence(
                new DiscordRichPresence.Builder("Typing in chat")
                    .setDetails(details)
                    .setStartTimestamps(startTime)
                    .setBigImage("chat", "Chat")
                    .build()
            );
        } else if (e.getGuiScreen() == null) {
            if (Minecraft.getMinecraft().getCurrentServerData() != null) {
                if (Minecraft.getMinecraft().getCurrentServerData().serverIP.toLowerCase()
                    .endsWith("hypixel.net")) {
                    DiscordRPC.discordUpdatePresence(
                        new DiscordRichPresence.Builder("On Hypixel")
                            .setDetails(details)
                            .setStartTimestamps(startTime)
                            .setBigImage("hypixel", "Hypixel")
                            .build()
                    );
                } else {
                    DiscordRPC.discordUpdatePresence(
                        new DiscordRichPresence.Builder(
                            "Server: " + Minecraft.getMinecraft().getCurrentServerData().serverIP)
                            .setDetails(details)
                            .setStartTimestamps(startTime)
                            .setBigImage("game", "In game")
                            .build()
                    );
                }
            } else {
                DiscordRPC.discordUpdatePresence(
                    new DiscordRichPresence.Builder("Playing singleplayer")
                        .setDetails(details)
                        .setStartTimestamps(startTime)
                        .setBigImage("singleplayer", "Singleplayer")
                        .build()
                );
            }
        }
    }

    public static DiscordPresence getInstance() {
        return instance;
    }
}
