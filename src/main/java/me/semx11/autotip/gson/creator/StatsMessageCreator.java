package me.semx11.autotip.gson.creator;

import com.google.gson.InstanceCreator;
import me.semx11.autotip.message.StatsMessage;

import java.lang.reflect.Type;

public class StatsMessageCreator implements InstanceCreator<StatsMessage> {
    @Override
    public StatsMessage createInstance(Type type) {
        return new StatsMessage();
    }
}
