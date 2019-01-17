package me.semx11.autotip.event;

import me.semx11.autotip.Autotip;
import me.semx11.autotip.core.SessionManager;
import me.semx11.autotip.core.TaskManager;
import me.semx11.autotip.universal.UniversalUtil;
import net.hydonclient.event.Event;
import net.hydonclient.event.EventListener;
import net.hydonclient.event.events.network.ServerJoinEvent;
import net.hydonclient.event.events.network.ServerLeaveEvent;
import net.minecraft.client.Minecraft;
import net.minecraft.util.IChatComponent;

public class EventClientConnection extends Event {

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

    public Object getHeader() {
        return Autotip.tabHeader;
    }

    public void setHeader(IChatComponent component) {
        Minecraft.getMinecraft().ingameGUI.getTabList().setHeader(component);
    }

    private void resetHeader() {
        setHeader(null);
    }

    @EventListener
    public void loggedIn(ServerJoinEvent event) {
        TaskManager taskManager = autotip.getTaskManager();
        SessionManager manager = autotip.getSessionManager();

        autotip.getMessageUtil().clearQueues();

        serverIp = UniversalUtil.getRemoteAddress(event).toLowerCase();
        lastLogin = System.currentTimeMillis();

        taskManager.getExecutor().execute(() -> {
            Object header;
            int attempts = 0;

            while ((header = getHeader()) == null) {
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
                    taskManager.executeTask(TaskManager.TaskType.LOGIN, manager::login);
                    taskManager.schedule(manager::checkVersions, 5);
                }
            } else {
                manager.setOnHypixel(false);
            }
        });
    }

    @EventListener
    public void logOut(ServerLeaveEvent event) {
        TaskManager taskManager = autotip.getTaskManager();
        SessionManager manager = autotip.getSessionManager();

        manager.setOnHypixel(false);

        taskManager.executeTask(TaskManager.TaskType.LOGOUT, manager::logout);

        resetHeader();
    }
}
