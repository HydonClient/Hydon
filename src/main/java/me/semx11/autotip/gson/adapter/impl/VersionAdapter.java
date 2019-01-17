package me.semx11.autotip.gson.adapter.impl;

import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;
import me.semx11.autotip.gson.adapter.TypeAdapter;
import me.semx11.autotip.util.AutoTipVersion;

public class VersionAdapter implements TypeAdapter<AutoTipVersion> {
    @Override
    public AutoTipVersion deserialize(JsonElement json) {
        return new AutoTipVersion(json.getAsString());
    }

    @Override
    public JsonElement serialize(AutoTipVersion src) {
        return new JsonPrimitive(src.get());
    }
}
