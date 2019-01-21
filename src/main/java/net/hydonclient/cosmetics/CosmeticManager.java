package net.hydonclient.cosmetics;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import net.hydonclient.cosmetics.capes.Capes;
import net.hydonclient.event.EventListener;
import net.hydonclient.event.events.game.WorldChangedEvent;

public class CosmeticManager {

    private Map<UUID, CosmeticData> cosmeticDataMap = new HashMap<>();
    List<UUID> processingList = new ArrayList<>();

    private static final CosmeticManager instance = new CosmeticManager();

    public CosmeticData getData(UUID uuid) {
        if (cosmeticDataMap.containsKey(uuid)) {
            return cosmeticDataMap.get(uuid);
        } else {
            processingList.add(uuid);
            CosmeticData cosmeticData = new CosmeticData(uuid);
            cosmeticDataMap.put(uuid, cosmeticData);
            return cosmeticData;
        }
    }

    @EventListener
    public void onWorldChange(WorldChangedEvent e) {
        cosmeticDataMap.clear();
        processingList.clear();
        Capes.getCapes().clear();
    }

    public boolean isProcessing(UUID uuid) {
        return processingList.contains(uuid);
    }

    public static CosmeticManager getInstance() {
        return instance;
    }
}
