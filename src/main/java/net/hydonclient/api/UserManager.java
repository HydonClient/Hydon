package net.hydonclient.api;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import net.hydonclient.api.objects.EnumCosmetic;
import net.hydonclient.api.objects.User;
import net.hydonclient.cosmetics.capes.Capes;
import net.hydonclient.event.EventListener;
import net.hydonclient.event.events.game.WorldChangedEvent;
import net.hydonclient.util.Multithreading;
import net.minecraft.client.Minecraft;

public class UserManager {

    private static final UserManager instance = new UserManager();

    private Map<UUID, User> userMap = new HashMap<>();
    private List<UUID> processingList = new ArrayList<>();

    public User getUser(UUID uuid) {
        if (userMap.containsKey(uuid)) {
            return userMap.get(uuid);
        } else {
            processingList.add(uuid);
            User user = new User(uuid);
            userMap.put(uuid, user);
            return user;
        }
    }

    @EventListener
    public void onWorldChange(WorldChangedEvent e) {
        userMap.clear();
        processingList.clear();
        Capes.getCapes().clear();
    }

    public void reprocess(UUID uuid) {
        Multithreading.run(() -> {
            try {
                Thread.sleep(2500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            userMap.remove(uuid);
            processingList.remove(uuid);
            Capes.getCapes().remove(uuid);
            User user = UserManager.getInstance().getUser(uuid);
            Capes.loadCape(uuid,
                user.hasUnlockedCosmetic(EnumCosmetic.CAPE) ? user.getCapeUrl()
                    : String.format("http://s.optifine.net/capes/%s.png",
                        Minecraft.getMinecraft().theWorld.getPlayerEntityByUUID(uuid).getName()));
        });
    }

    public boolean isProcessing(UUID uuid) {
        return processingList.contains(uuid);
    }

    public void stopProcessing(UUID uuid) {
        processingList.remove(uuid);
    }

    public static UserManager getInstance() {
        return instance;
    }
}
