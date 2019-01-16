package net.hydonclient;

import java.io.File;
import net.hydonclient.managers.HydonManagers;
import net.minecraft.client.Minecraft;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Hydon {

    public static final Hydon INSTANCE = new Hydon();
    public static final Logger LOGGER = LogManager.getLogger("Hydon");
    public static final File STORAGE_FOLDER = new File(Minecraft.getMinecraft().mcDataDir, "Hydon");
    public static final String VERSION = "B1";

    public void start() {
        LOGGER.info("Starting Hydon");

        if (!STORAGE_FOLDER.exists()) {
            STORAGE_FOLDER.mkdirs();
        }

        LOGGER.info("Loading managers");
        HydonManagers.INSTANCE.init();

        LOGGER.info("Done");
    }

    public void stop() {
        LOGGER.info("Stopping Hydon");

        LOGGER.info("Stopping managers");
        HydonManagers.INSTANCE.close();

        LOGGER.info("Done");
    }

}
