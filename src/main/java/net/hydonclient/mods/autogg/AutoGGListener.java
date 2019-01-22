package net.hydonclient.mods.autogg;

import net.hydonclient.Hydon;
import net.hydonclient.event.EventListener;
import net.hydonclient.event.events.hypixel.GameEndEvent;
import net.hydonclient.util.ChatUtils;
import net.hydonclient.util.Multithreading;

public class AutoGGListener {

    /**
     * Called when a game on Hypixel has ended
     *
     * @param e the event being used
     */
    @EventListener
    public void onGameEnd(GameEndEvent e) {
        Multithreading.run(() -> {
            try {
                Thread.sleep(Hydon.SETTINGS.autoGGDelay * 1000);
                ChatUtils.sendChatMessage("/achat gg");
            } catch (InterruptedException e1) {
                e1.printStackTrace();
            }
        });
    }

}
