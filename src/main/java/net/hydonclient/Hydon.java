package net.hydonclient;

import com.kodingking.mods.core.KodingMod;
import java.io.File;
import net.hydonclient.api.UserManager;
import net.hydonclient.commands.DefaultCommands;
import net.hydonclient.event.EventBus;
import net.hydonclient.gui.main.HydonMainGui;
import net.hydonclient.integrations.compactchat.CompactChat;
import net.hydonclient.integrations.discord.DiscordPresence;
import net.hydonclient.managers.HydonManagers;
import net.hydonclient.packages.PackageBootstrap;
import net.minecraft.client.Minecraft;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Hydon {

    public static final Hydon INSTANCE = new Hydon();
    public static final Logger LOGGER = LogManager.getLogger("Hydon");
    public static final File STORAGE_FOLDER = new File(Minecraft.getMinecraft().mcDataDir, "Hydon");
    public static final String VERSION = "B1";
    public static final Settings SETTINGS = new Settings();
    public static final KodingMod KODING_MOD = new KodingMod("hydon", "Hydon", VERSION);

    private boolean optifineFound = false;

    /**
     * Invoked when the client starts.
     */
    public void start() {
        LOGGER.info("Starting Hydon");

        if (!STORAGE_FOLDER.exists()) {
            STORAGE_FOLDER.mkdirs();
            LOGGER.info("Creating storage folder");
        }

        LOGGER.info("Loading Koding mod");
        KODING_MOD.init();

        LOGGER.info("Loading managers");
        HydonManagers.INSTANCE.init();
        EventBus.register(CompactChat.getInstance());
        EventBus.register(UserManager.getInstance());
        EventBus.register(DefaultCommands.getInstance());
        DefaultCommands.getInstance().load();

        try {
            Class.forName("optifine.OptiFineTweaker");
            optifineFound = true;
            LOGGER.info("Found Optifine");
        } catch (ClassNotFoundException e) {
            LOGGER.info("Optifine not found");
            optifineFound = false;
        }

        LOGGER.info("Loading Discord RPC");
        EventBus.register(DiscordPresence.getInstance());
        DiscordPresence.getInstance().load();

        LOGGER.info("Initializing packages");
        PackageBootstrap.gameInit();

        LOGGER.info("Loading config values");
        HydonManagers.INSTANCE.getConfigManager().load();

        LOGGER.info("Initializing Main GUI");
        HydonMainGui.INSTANCE.loadPackageElements();

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

    public boolean wasOptifineFound() {
        return optifineFound;
    }
}
