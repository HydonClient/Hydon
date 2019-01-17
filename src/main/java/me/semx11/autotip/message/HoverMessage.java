package me.semx11.autotip.message;

import me.semx11.autotip.gson.exclusion.Exclude;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.regex.Pattern;

public class HoverMessage extends Message {

    @Exclude
    private final Map<String, HoverMessageMatcher> hoverMessageCache = new ConcurrentHashMap<>();

    private StatsType statsType;

    public HoverMessage() {
        super();
    }

    public HoverMessage(Pattern pattern, StatsType statsType) {
        super(pattern);
        this.statsType = statsType;
    }

    public HoverMessageMatcher getMatcherFor(String input) {
        if (hoverMessageCache.containsKey(input)) {
            return hoverMessageCache.get(input);
        }
        HoverMessageMatcher matcher = new HoverMessageMatcher(pattern, input, statsType);
        hoverMessageCache.put(input, matcher);
        return matcher;
    }
}
