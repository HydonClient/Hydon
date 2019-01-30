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
    private boolean admin;
    private UUID uuid;

    private List<Cosmetic> cosmetics;

    public User(UUID uuid) {
        this.uuid = uuid;
        this.cosmetics = new ArrayList<>();

        JsonObject result = HydonApi.getUser(uuid);
        if (result.has("success") && result.get("success").getAsBoolean()) {
            this.lastUsername =
                result.has("lastUsername") && !result.get("lastUsername").isJsonNull() ? result
                    .get("lastUsername").getAsString() : "";
            this.admin = result.has("admin") && result.get("admin").getAsBoolean();
        }

        JsonObject cosmeticsResult = HydonApi.getCosmetics(uuid);
        if (cosmeticsResult.has("success") && cosmeticsResult.get("success").getAsBoolean()) {
            for (JsonElement element : cosmeticsResult.getAsJsonArray("cosmetics")) {
                JsonObject jsonObject = element.getAsJsonObject();
                Cosmetic cosmetic = new Cosmetic(jsonObject.get("name").getAsString());
                cosmetic.setEnabled(
                    !jsonObject.has("enabled") || jsonObject.get("enabled").getAsBoolean());
                cosmetic.setData(jsonObject.getAsJsonObject("data"));
                cosmetics.add(cosmetic);
            }
        }

        UserManager.getInstance().stopProcessing(uuid);
    }

    public UUID getUuid() {
        return uuid;
    }

    public String getLastUsername() {
        return lastUsername;
    }

    public List<Cosmetic> getCosmetics() {
        return cosmetics;
    }

    public boolean hasUnlockedCosmetic(EnumCosmetic cosmetic) {
        return getCosmetics().stream()
            .anyMatch(
                cosmetic1 -> cosmetic1.getName().equalsIgnoreCase(cosmetic.name()) && cosmetic1
                    .isEnabled());
    }

    public String getCapeUrl() {
        for (Cosmetic cosmetic : getCosmetics()) {
            if (cosmetic.getName().equalsIgnoreCase(EnumCosmetic.CAPE.name()) && cosmetic.getData()
                .has("url")) {
                return cosmetic.getData().get("url").getAsString();
            }
        }
        return "";
    }

    public boolean isAdmin() {
        return admin;
    }
}
