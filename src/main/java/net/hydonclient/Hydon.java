package net.hydonclient;

import com.kodingking.mods.core.KodingMod;
import com.kodingking.netty.client.NetClientBuilder;
import com.kodingking.netty.universal.Constants;
import com.kodingking.netty.universal.UniversalNetty;
import java.io.File;
import net.hydonclient.api.UserManager;
import net.hydonclient.commands.DefaultCommands;
import net.hydonclient.event.EventBus;
import net.hydonclient.integrations.compactchat.CompactChat;
import net.hydonclient.integrations.discord.DiscordPresence;
import net.hydonclient.managers.HydonManagers;
import net.hydonclient.netty.listeners.MainListener;
import net.hydonclient.util.Multithreading;
import net.minecraft.client.Minecraft;
import net.minecraft.util.Session;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Hydon {

    public static final Hydon INSTANCE = new Hydon();
    public static final Logger LOGGER = LogManager.getLogger("Hydon");
    public static final File STORAGE_FOLDER = new File(Minecraft.getMinecraft().mcDataDir, "Hydon");
    public static final String VERSION = "B1";
    public static final Settings SETTINGS = new Settings();
    public static final KodingMod KODING_MOD = new KodingMod("hydon", "Hydon", VERSION);

    /**
     * Invoked when the client starts.
     */
    public void start() {
        LOGGER.info("Starting Hydon");

        if (!STORAGE_FOLDER.exists()) {
            STORAGE_FOLDER.mkdirs();
        }

        LOGGER.info("Loading Koding mod");
        KODING_MOD.init();

        LOGGER.info("Loading managers");
        HydonManagers.INSTANCE.init();
        EventBus.register(CompactChat.getInstance());
        EventBus.register(UserManager.getInstance());
        EventBus.register(DefaultCommands.getInstance());
        DefaultCommands.getInstance().load();

        LOGGER.info("Connecting to Netty");
        Multithreading.run(() -> {
            Session session = Minecraft.getMinecraft().getSession();
            NetClientBuilder.create(Constants.CLIENT_BRAND_MINECRAFT, "1.0", "netty.kodingking.com",
                Constants.SERVER_BIND_PORT).setAutoReconnect()
                .withMinecraftAuth(session.getUsername(), session.getToken(),
                    session.getProfile().getId()).boot();
            UniversalNetty.getCurrentBootstrap().getUniversalNetty().getListenerAdapters()
                .add(new MainListener());
        });

        LOGGER.info("Loading Discord RPC");
        EventBus.register(DiscordPresence.getInstance());
        DiscordPresence.getInstance().load();

        LOGGER.info("Done");
    }

    /**
     * Invoked when the client is shutting down.
     */
    public void stop() {
        LOGGER.info("Stopping Hydon");

        LOGGER.info("Stopping managers");
        HydonManagers.INSTANCE.close();

        LOGGER.info("Stopping Discord RPC");
        DiscordPresence.getInstance().shutdown();

        LOGGER.info("Done");
    }

}
