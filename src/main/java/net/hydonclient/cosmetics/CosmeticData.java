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

    /**
     * Get the cosmetic context from a player once they start the client
     *
     * @param uuid the users uuid
     */
    CosmeticData(UUID uuid) {
        this.uuid = uuid;

        JsonObject result = HydonApi.getUser(uuid);
        this.admin = result.has("admin") && result.get("admin").getAsBoolean();

        /*
         * Search through the list of cosmetics
         * If the player has a cosmetic that matches unlockedCosmetics
         * Put the cosmetic into the object
         */
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

        /*
         * Search through the list of cape url's
         * If the player has a cape, return the url that is assigned to their uuid
         */
        JsonObject result2 = HydonApi.getCape(uuid);
        this.hasCape = result2.has("hasCape") && result2.get("hasCape").getAsBoolean();
        this.capeUrl =
                result2.has("capeUrl") && !result2.get("capeUrl").isJsonNull() ? result2.get("capeUrl")
                        .getAsString() : "";

        /*
         * Remove them from the process queue
         */
        CosmeticManager.getInstance().processingList.remove(uuid);
    }

    /**
     * If the player is a Hydon admin
     *
     * @return true if player is admin
     */
    public boolean isAdmin() {
        return admin;
    }

    /**
     * If the player has a cosmetic
     *
     * @param cosmetic cosmetic the player has
     * @return unlocked cosmetic
     */
    public boolean hasUnlockedCosmetic(EnumCosmetic cosmetic) {
        return unlockedCosmeticMap.get(cosmetic);
    }

    /**
     * If the player has a cape
     *
     * @return true if cape is true
     */
    public boolean hasCape() {
        return hasCape;
    }

    /**
     * Get the uploaded cape texture for the player
     *
     * @return cape url
     */
    public String getCapeUrl() {
        return capeUrl;
    }

    /**
     * Get the players UUID
     *
     * @return player uuid
     */
    public UUID getUuid() {
        return uuid;
    }
}
