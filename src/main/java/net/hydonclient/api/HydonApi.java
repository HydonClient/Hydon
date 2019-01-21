package net.hydonclient.api;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.util.UUID;
import net.hydonclient.util.WebUtil;

public class HydonApi {

    private static JsonParser parser = new JsonParser();

    public static JsonObject getUser(UUID uuid) {
        return request("http://hydonapi.kodingking.com/user/" + uuid.toString());
    }

    public static JsonObject getCape(UUID uuid) {
        return request("http://hydonapi.kodingking.com/user/" + uuid.toString() + "/cape");
    }

    private static JsonObject request(String url) {
        try {
            return parser
                .parse(WebUtil.httpGet(url))
                .getAsJsonObject();
        } catch (Exception e) {
            return new JsonObject();
        }
    }

}
