package net.hydonclient.api;

import com.google.gson.JsonObject;
import com.kodingking.mods.core.KodingMod;
import com.kodingking.mods.core.util.HttpUtil;
import java.util.UUID;
import net.minecraft.client.Minecraft;

public class HydonApi {

    public static JsonObject getUser(UUID uuid) {
        return HttpUtil
            .getJson(String.format("%s/user/%s", KodingMod.API_ENDPOINT, uuid.toString()));
    }

    public static JsonObject getCosmetics(UUID uuid) {
        return HttpUtil.getJson(String
            .format("%s/mods/%s/cosmetics?modId=%s&modVersion=%s&ownUuid=%s",
                KodingMod.API_ENDPOINT,
                uuid.toString(),
                KodingMod.getInstance().getModId(), KodingMod.getInstance().getVersion(),
                Minecraft.getMinecraft().getSession().getProfile().getId()));
    }

}
