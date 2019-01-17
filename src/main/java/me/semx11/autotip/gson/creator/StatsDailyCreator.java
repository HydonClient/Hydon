package me.semx11.autotip.gson.creator;

import com.google.gson.InstanceCreator;
import me.semx11.autotip.Autotip;
import me.semx11.autotip.stats.StatsDaily;

import java.lang.reflect.Type;

public class StatsDailyCreator implements InstanceCreator<StatsDaily> {

    private final Autotip autotip;

    public StatsDailyCreator(Autotip autotip) {
        this.autotip = autotip;
    }

    @Override
    public StatsDaily createInstance(Type type) {
        return new StatsDaily(autotip);
    }
}
