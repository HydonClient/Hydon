package net.hydonclient.util;

import net.hydonclient.Hydon;
import net.minecraft.client.Minecraft;
import net.minecraft.crash.CrashReport;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

public class CrashUtils {

    public static void outputReport(CrashReport report) {
        try {
            if (report.getFile() == null) {
                String reportName = "crash-";
                reportName += new SimpleDateFormat("yyyy-MM-dd_HH.mm.ss").format(new Date());
                reportName += Minecraft.getMinecraft().isCallingFromMinecraftThread() ? "-client" : "-server";
                reportName += ".txt";

                File reportsDirectory = isClient() ? new File(Minecraft.getMinecraft().mcDataDir, "/hydon/crash-reports") : new File("crash-reports");
                File reportFile = new File(reportsDirectory, reportName);

                report.saveToFile(reportFile);
            }
        } catch (Throwable t) {
            Hydon.LOGGER.error("Failed saving report", t);
        }

        Hydon.LOGGER.error("Minecraft ran into a problem! " + (report.getFile() != null ? "Report saved to: " + report.getFile() : "Crash report could not be saved.") + "\n" + report.getCompleteReport());
    }

    private static boolean isClient() {
        try {
            return Minecraft.getMinecraft() != null;
        } catch (NoClassDefFoundError e) {
            return false;
        }
    }
}
