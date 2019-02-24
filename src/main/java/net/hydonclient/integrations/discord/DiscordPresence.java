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

    /**
     * The DiscordPresence instance
     */
    private static final DiscordPresence instance = new DiscordPresence();

    /**
     * When the user started the client
     */
    private long startTime;

    /**
     * Called upon launching the client Initializes the DiscordRPC Thread
     */
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

    /**
     * Called upon shutting down the client Closes the DiscordRPC thread properly
     */
    public void shutdown() {
        DiscordRPC.discordClearPresence();
        DiscordRPC.discordShutdown();
    }

    /**
     * Update the current users status based on what they're currently doing
     *
     * @param e the event being used
     */
    @EventListener
    private void onDisplayGui(GuiDisplayEvent e) {

        /*
         * If the user is currently in the main menu, make the rpc say they're in the main menu
         */
        if (e.getGuiScreen() instanceof GuiHydonMainMenu) {
            DiscordRPC.discordUpdatePresence(
                new DiscordRichPresence.Builder("Main Menu")
                    .setDetails("IGN: " + Minecraft.getMinecraft().getSession().getUsername())
                    .setStartTimestamps(startTime)
                    .setBigImage("hydon", "Hydon")
                    .build()
            );

            /*
             * If the user is currently in the multiplayer menu, make the rpc say they're in the
             * multiplayer menu
             */
        } else if (e.getGuiScreen() instanceof GuiMultiplayer) {
            DiscordRPC.discordUpdatePresence(
                new DiscordRichPresence.Builder("Browsing servers")
                    .setDetails("IGN: " + Minecraft.getMinecraft().getSession().getUsername())
                    .setStartTimestamps(startTime)
                    .setBigImage("hydon", "Hydon")
                    .build()
            );

            /*
             * If the user is currently typing in chat, make the rpc say they're typing in chat
             */
        } else if (e.getGuiScreen() instanceof GuiChat) {
            DiscordRPC.discordUpdatePresence(
                new DiscordRichPresence.Builder("Typing in chat")
                    .setDetails("IGN: " + Minecraft.getMinecraft().getSession().getUsername())
                    .setStartTimestamps(startTime)
                    .setBigImage("chat", "Chat")
                    .build()
            );

            /*
             * If the server they're connected to is Hypixel, make the rpc say they're on Hypixel
             */
        } else if (e.getGuiScreen() == null) {
            if (Minecraft.getMinecraft().getCurrentServerData() != null) {
                DiscordRPC.discordUpdatePresence(
                    new DiscordRichPresence.Builder(
                        "Server: " + Minecraft.getMinecraft().getCurrentServerData().serverIP)
                        .setDetails("IGN: " + Minecraft.getMinecraft().getSession().getUsername())
                        .setStartTimestamps(startTime)
                        .setBigImage("server", "On a server")
                        .build()
                );

                /*
                 * If the player is in singleplayer, make the rpc say they're playing singleplayer
                 */
            } else {
                DiscordRPC.discordUpdatePresence(
                    new DiscordRichPresence.Builder("Playing Singleplayer")
                        .setDetails("IGN: " + Minecraft.getMinecraft().getSession().getUsername())
                        .setStartTimestamps(startTime)
                        .setBigImage("singleplayer", "Singleplayer")
                        .build()
                );
            }
        }
    }

    /**
     * The getInstance method for DiscordPresence so it can be used in other classes without static
     * calls
     *
     * @return the instance
     */
    public static DiscordPresence getInstance() {
        return instance;
    }
}
