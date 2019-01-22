package net.hydonclient.api;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.util.UUID;

import net.hydonclient.util.WebUtil;

public class HydonApi {

    /**
     * The JsonParser that's used for when the endpoint
     * is being accessed and the endpoint is in a JSON format
     */
    private static JsonParser parser = new JsonParser();

    /**
     * Get the UUID of the user that's launching the client
     *
     * @param uuid the users uuid
     * @return request access to the endpoint
     */
    public static JsonObject getUser(UUID uuid) {
        return request("http://hydonapi.kodingking.com/user/" + uuid.toString());
    }

    /**
     * Get the cape endpoint of the user that's launching the client
     *
     * @param uuid the users uuid
     * @return request access to the cape endpoint
     */
    public static JsonObject getCape(UUID uuid) {
        return request("http://hydonapi.kodingking.com/user/" + uuid.toString() + "/cape");
    }

    /**
     * Request the endpoint that's currently being accessed
     * to register any cosmetics for the user
     *
     * @param url the endpoint
     * @return a json object
     */
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
