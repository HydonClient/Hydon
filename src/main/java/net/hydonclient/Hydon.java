package net.hydonclient;

import java.io.File;

import net.hydonclient.event.EventBus;
import net.hydonclient.integrations.compactchat.CompactChat;
import net.hydonclient.managers.HydonManagers;
import net.hydonclient.staff.StaffManager;
import net.hydonclient.util.Multithreading;
import net.minecraft.client.Minecraft;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Hydon {

    public static final Hydon INSTANCE = new Hydon();
    public static final Logger LOGGER = LogManager.getLogger("Hydon");
    public static final File STORAGE_FOLDER = new File(Minecraft.getMinecraft().mcDataDir, "Hydon");
    public static final String VERSION = "B1";
    public static final Settings SETTINGS = new Settings();

    /**
     * Invoked when the client starts.
     */
    public void start() {
        LOGGER.info("Starting Hydon");

        if (!STORAGE_FOLDER.exists()) {
            STORAGE_FOLDER.mkdirs();
        }

        LOGGER.info("Loading managers");
        HydonManagers.INSTANCE.init();
        EventBus.register(CompactChat.getInstance());

        LOGGER.info("Loading staff");
        Multithreading.run(StaffManager::fetchStaff);

        LOGGER.info("Done");
    }

    /**
     * Invoked when the client is shutting down.
     */
    public void stop() {
        LOGGER.info("Stopping Hydon");

        LOGGER.info("Stopping managers");
        HydonManagers.INSTANCE.close();

        LOGGER.info("Done");
    }

}
