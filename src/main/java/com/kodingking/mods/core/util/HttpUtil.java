package com.kodingking.mods.core.util;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.kodingking.mods.core.KodingMod;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Locale;
import org.apache.commons.io.IOUtils;

public class HttpUtil {

    public static JsonObject getJson(String url) {
        try {
            return getJson(new URL(url));
        } catch (MalformedURLException e) {
            e.printStackTrace();
            return new JsonObject();
        }
    }

    public static JsonObject getJson(URL url) {
        try {
            URLConnection conn = url.openConnection();
            conn.addRequestProperty("Access-Token", KodingMod.getInstance().getAuth().getToken());
            conn.setRequestProperty("Accept-Language", Locale.getDefault().toLanguageTag());
            conn.connect();
            return new JsonParser().parse(IOUtils.toString(conn.getInputStream(), "UTF-8"))
                .getAsJsonObject();
        } catch (IOException e) {
            e.printStackTrace();
            return new JsonObject();
        }
    }

    public static HttpURLConnection postJson(String url, JsonObject json) {
        try {
            return postJson(new URL(url), json);
        } catch (MalformedURLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static HttpURLConnection postJson(URL url, JsonObject json) {
        try {
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.addRequestProperty("Content-Type", "application/json");
            conn.addRequestProperty("Access-Token", KodingMod.getInstance().getAuth().getToken());
            conn.setRequestProperty("Accept-Language", Locale.getDefault().toLanguageTag());
            conn.setDoOutput(true);

            OutputStreamWriter writer = new OutputStreamWriter(conn.getOutputStream());
            writer.write(json.toString());
            writer.flush();

            conn.connect();
            return conn;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

}
