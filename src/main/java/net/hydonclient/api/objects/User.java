package net.hydonclient.api.objects;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import net.hydonclient.api.HydonApi;
import net.hydonclient.api.UserManager;

public class User {

    private String lastUsername;
    private UUID uuid;
    private long lastLoginTime, firstSeen;

    private List<Cosmetic> cosmetics;

    public User(UUID uuid) {
        this.uuid = uuid;
        this.cosmetics = new ArrayList<>();

        JsonObject result = HydonApi.getUser(uuid);

        this.lastUsername =
            result.has("lastUsername") && !result.get("lastUsername").isJsonNull() ? result.get("lastUsername").getAsString() : "";
        this.lastLoginTime = result.get("lastLoginTime").getAsLong();
        this.firstSeen = result.get("firstSeen").getAsLong();

        for (JsonElement element : result.getAsJsonArray("cosmetics")) {
            JsonObject jsonObject = element.getAsJsonObject();
            Cosmetic cosmetic = new Cosmetic(jsonObject.get("name").getAsString());
            cosmetic.setData(jsonObject.getAsJsonObject("data"));
            cosmetics.add(cosmetic);
        }

        UserManager.getInstance().stopProcessing(uuid);
    }

    public void setLastUsername(String lastUsername) {
        this.lastUsername = lastUsername;
    }

    public UUID getUuid() {
        return uuid;
    }

    public long getLastLoginTime() {
        return lastLoginTime;
    }

    public long getFirstSeen() {
        return firstSeen;
    }

    public String getLastUsername() {
        return lastUsername;
    }

    public List<Cosmetic> getCosmetics() {
        return cosmetics;
    }

    public boolean hasUnlockedCosmetic(EnumCosmetic cosmetic) {
        return getCosmetics().stream()
            .anyMatch(cosmetic1 -> cosmetic1.getName().equalsIgnoreCase(cosmetic.name()));
    }

    public String getCapeUrl() {
        for (Cosmetic cosmetic : getCosmetics()) {
            if (cosmetic.getName().equalsIgnoreCase(EnumCosmetic.CAPE.name()) && cosmetic.getData().has("url")) {
                return cosmetic.getData().get("url").getAsString();
            }
        }
        return "";
    }
}
