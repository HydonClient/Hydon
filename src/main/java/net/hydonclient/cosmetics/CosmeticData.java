package net.hydonclient.cosmetics;

import com.google.gson.JsonObject;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import net.hydonclient.api.HydonApi;

public class CosmeticData {

    private UUID uuid;
    private boolean admin, hasCape;
    private String capeUrl;
    private Map<EnumCosmetic, Boolean> unlockedCosmeticMap = new HashMap<>();

    public CosmeticData(UUID uuid) {
        this.uuid = uuid;

        JsonObject result = HydonApi.getUser(uuid);
        this.admin = result.has("admin") && result.get("admin").getAsBoolean();

        for (EnumCosmetic enumCosmetic : EnumCosmetic.values()) {
            if (result.has("unlockedCosmetics")) {
                JsonObject cosmeticsObject = result.getAsJsonObject("unlockedCosmetics");
                unlockedCosmeticMap.put(enumCosmetic,
                    cosmeticsObject.has(enumCosmetic.name()) && cosmeticsObject
                        .get(enumCosmetic.name()).getAsBoolean());
            } else {
                unlockedCosmeticMap.put(enumCosmetic, false);
            }
        }

        JsonObject result2 = HydonApi.getCape(uuid);
        this.hasCape = result2.has("hasCape") && result2.get("hasCape").getAsBoolean();
        this.capeUrl =
            result2.has("capeUrl") && !result2.get("capeUrl").isJsonNull() ? result2.get("capeUrl")
                .getAsString() : "";

        CosmeticManager.getInstance().processingList.remove(uuid);
    }

    public boolean isAdmin() {
        return admin;
    }

    public boolean hasUnlockedCosmetic(EnumCosmetic cosmetic) {
        return unlockedCosmeticMap.get(cosmetic);
    }

    public boolean hasCape() {
        return hasCape;
    }

    public String getCapeUrl() {
        return capeUrl;
    }

    public UUID getUuid() {
        return uuid;
    }
}
