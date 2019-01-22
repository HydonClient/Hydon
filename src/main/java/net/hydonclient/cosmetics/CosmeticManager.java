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

    /**
     * Mapping for the cosmetic
     * <p>
     * If the players UUID matches any of the cosmetics, they'll be added to this map
     */
    private Map<UUID, CosmeticData> cosmeticDataMap = new HashMap<>();

    /**
     * The process queue for searching through what cosmetics they have
     * <p>
     * If the players UUID contains any cosmetics on the list, they'll be added to this map
     */
    List<UUID> processingList = new ArrayList<>();

    private static final CosmeticManager instance = new CosmeticManager();

    /**
     * Get the players UUID and put it into a data map
     * If they match any of the UUID's put into the map
     * Add them to the process queue
     *
     * @param uuid the players uuid
     * @return true if the player has any of the cosmetics
     */
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

    /**
     * Clear the maps once changing worlds to free up some memory
     *
     * @param e event being used
     */
    @EventListener
    public void onWorldChange(WorldChangedEvent e) {
        cosmeticDataMap.clear();
        processingList.clear();
        Capes.getCapes().clear();
    }

    /**
     * If the player-uuid is in the process queue
     *
     * @param uuid the players uuid
     * @return true if they're in the queue
     */
    public boolean isProcessing(UUID uuid) {
        return processingList.contains(uuid);
    }

    /**
     * The getInstance method for CosmeticManager so it can be used in other classes
     * without static calls
     *
     * @return the instance
     */
    public static CosmeticManager getInstance() {
        return instance;
    }
}
