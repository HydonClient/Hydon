package net.hydonclient.mods.autogg;

import net.hydonclient.event.EventListener;
import net.hydonclient.event.events.hypixel.GameEndEvent;
import net.hydonclient.managers.HydonManagers;
import net.hydonclient.util.ChatUtils;
import net.hydonclient.util.Multithreading;

public class AutoGGListener {

    @EventListener
    public void onGameEnd(GameEndEvent e) {
        Multithreading.run(() -> {
            try {
                Thread.sleep(
                    HydonManagers.INSTANCE.getModManager().getAutoGGMod().getConfig().DELAY
                        * 1000);
                ChatUtils.sendChatMessage("gg");
            } catch (InterruptedException e1) {
                e1.printStackTrace();
            }
        });
    }

}
