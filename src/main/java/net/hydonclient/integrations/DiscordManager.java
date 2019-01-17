package net.hydonclient.integrations;

import club.minnced.discord.rpc.DiscordEventHandlers;
import club.minnced.discord.rpc.DiscordRPC;
import club.minnced.discord.rpc.DiscordRichPresence;
import net.hydonclient.Hydon;

public class DiscordManager {

    public void init() {
        DiscordRPC library = DiscordRPC.INSTANCE;
        String applicationID = "535300495640625153";
        String steamID = "";

        DiscordEventHandlers handlers = new DiscordEventHandlers();
        handlers.ready = (user) -> Hydon.LOGGER.info("Discord RPC is now ready.");

        library.Discord_Initialize(applicationID, handlers, true, steamID);

        DiscordRichPresence presence = new DiscordRichPresence();

        presence.details = "Hydon Client";
        library.Discord_UpdatePresence(presence);

        new Thread(() -> {
            while (!Thread.currentThread().isInterrupted()) {
                library.Discord_RunCallbacks();
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException ignored) {
                }
            }
        }, "RPC-Callback-Handler").start();
    }
}
