package me.semx11.autotip.message;

import me.semx11.autotip.stats.StatsDaily;

import java.util.regex.Pattern;

public class StatsMessageMatcher extends MessageMatcher {

    private final StatsType statsType;

    public StatsMessageMatcher(Pattern pattern, String input, StatsType statsType) {
        super(pattern, input);
        this.statsType = statsType;
    }

    public void applyStats(StatsDaily stats) {
        statsType.getConsumer().accept(stats, this);
    }
}
