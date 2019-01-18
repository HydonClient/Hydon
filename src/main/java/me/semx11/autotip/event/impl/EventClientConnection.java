package me.semx11.autotip.event.impl;

import me.semx11.autotip.Autotip;
import me.semx11.autotip.core.SessionManager;
import me.semx11.autotip.core.TaskManager;
import me.semx11.autotip.core.TaskManager.TaskType;
import me.semx11.autotip.event.Event;
import me.semx11.autotip.universal.UniversalUtil;
import me.semx11.autotip.util.ErrorReport;
import net.hydonclient.event.EventListener;
import net.hydonclient.event.events.network.ServerJoinEvent;
import net.hydonclient.event.events.network.ServerLeaveEvent;
import net.minecraft.client.Minecraft;
import net.minecraft.util.IChatComponent;

public class EventClientConnection implements Event {

    public static IChatComponent header;

    private final Autotip autotip;
    private final String hypixelHeader;

    private String serverIp;
    private long lastLogin;

    public EventClientConnection(Autotip autotip) {
        this.autotip = autotip;
        this.hypixelHeader = autotip.getGlobalSettings().getHypixelHeader();
    }

    public String getServerIp() {
        return serverIp;
    }

    public long getLastLogin() {
        return lastLogin;
    }

    public IChatComponent getHeader() {
        try {
            return header;
        } catch (NullPointerException e) {
            ErrorReport.reportException(e);
            return null;
        }
    }

    private void resetHeader() {
        header = null;
    }

    @EventListener
    public void playerLoggedIn(ServerJoinEvent event) {
        TaskManager taskManager = autotip.getTaskManager();
        SessionManager manager = autotip.getSessionManager();

        autotip.getMessageUtil().clearQueues();

        this.serverIp = Minecraft.getMinecraft().getCurrentServerData().serverIP;
        this.lastLogin = System.currentTimeMillis();

        taskManager.getExecutor().execute(() -> {
            IChatComponent header;
            int attempts = 0;
            while ((header = this.getHeader()) == null) {
                if (attempts > 15) {
                    return;
                }
                try {
                    Thread.sleep(500);
                } catch (InterruptedException ignored) {
                }
                attempts++;
            }

            if (UniversalUtil.getUnformattedText(header).equals(hypixelHeader)) {
                manager.setOnHypixel(true);
                if (autotip.getConfig().isEnabled()) {
                    taskManager.executeTask(TaskType.LOGIN, manager::login);
                    taskManager.schedule(manager::checkVersions, 5);
                }
            } else {
                manager.setOnHypixel(false);
            }
        });
    }

    @EventListener
    public void playerLoggedOut(ServerLeaveEvent event) {
        TaskManager taskManager = autotip.getTaskManager();
        SessionManager manager = autotip.getSessionManager();
        manager.setOnHypixel(false);
        taskManager.executeTask(TaskType.LOGOUT, manager::logout);
        this.resetHeader();
    }

}