package net.hydonclient.staff;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import net.hydonclient.util.WebUtil;

public class StaffManager {

    public static final Map<UUID, String> STAFF_CAPES = new HashMap<>();
    public static final List<UUID> STAFF_WINGS = new ArrayList<>();

    private static final String URL = "https://raw.githubusercontent.com/HydonClient/Repo/master/staff.json";

    /**
     * Fetches staff from the github repository
     */
    public static void fetchStaff() {
        try {
            String text = WebUtil.httpGet(URL);
            JsonParser jsonParser = new JsonParser();
            JsonArray object = jsonParser.parse(text).getAsJsonArray();

            object.forEach(jsonElement -> {
                JsonObject jsonObject = jsonElement.getAsJsonObject();
                if (!jsonObject.has("uuid")) {
                    return;
                }

                UUID uuid = UUID.fromString(jsonObject.get("uuid").getAsString());

                /*
                 * If the player matches a UUID on the capes file, give them the cape
                 */
                if (jsonObject.has("cape")) {
                    STAFF_CAPES.put(uuid, jsonObject.get("cape").getAsString());
                }

                /*
                 * If the player matches a UUID on the wings file, give them the wings
                 */
                if (jsonObject.has("wings") && jsonObject.get("wings").getAsBoolean()) {
                    STAFF_WINGS.add(uuid);
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
