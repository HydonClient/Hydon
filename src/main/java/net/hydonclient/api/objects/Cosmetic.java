package net.hydonclient.api.objects;

import com.google.gson.JsonObject;

public class Cosmetic {

    private String name;
    private JsonObject data;

    public Cosmetic(String name) {
        this.name = name;
        this.data = new JsonObject();
    }

    public String getName() {
        return name;
    }

    public JsonObject getData() {
        return data;
    }

    public void setData(JsonObject data) {
        this.data = data;
    }
}
