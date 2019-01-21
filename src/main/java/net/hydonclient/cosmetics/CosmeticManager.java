package net.hydonclient.cosmetics;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class CosmeticManager {

    private static Map<UUID, CosmeticData> cosmeticDataMap = new HashMap<>();
    static List<UUID> processingList = new ArrayList<>();

    public static CosmeticData getData(UUID uuid) {
        if (cosmeticDataMap.containsKey(uuid)) {
            return cosmeticDataMap.get(uuid);
        } else {
            processingList.add(uuid);
            CosmeticData cosmeticData = new CosmeticData(uuid);
            cosmeticDataMap.put(uuid, cosmeticData);
            return cosmeticData;
        }
    }

    public static boolean isProcessing(UUID uuid) {
        return processingList.contains(uuid);
    }

}
