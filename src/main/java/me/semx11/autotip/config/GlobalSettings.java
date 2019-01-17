package me.semx11.autotip.config;

import me.semx11.autotip.message.Message;
import me.semx11.autotip.message.StatsMessage;
import me.semx11.autotip.util.AutoTipVersion;
import me.semx11.autotip.util.VersionInfo;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class GlobalSettings {

    private AutoTipVersion latestAutoTipVersion;
    private List<VersionInfo> versions;
    private String hypixelHeader;
    private int xpPerTipSent;
    private int xpPerTipReceived;
    private LocalDate xpChangeDate;
    private List<GameGroup> gameGroups;
    private List<GameAlias> gameAliases;
    private List<Message> messages;
    private List<StatsMessage> statsMessages;

    public VersionInfo getVersionInfo(AutoTipVersion autoTipVersion) {
        return versions.stream()
                .filter(v -> v.getAutoTipVersion().equals(autoTipVersion))
                .findFirst()
                .orElse(new VersionInfo(autoTipVersion, VersionInfo.Severity.OPTIONAL, "&cVersion not found."));
    }

    public List<VersionInfo> getHigherVersionInfo(AutoTipVersion lowest) {
        return versions.stream().filter(info -> {
            AutoTipVersion v = info.getAutoTipVersion();
            return v.compareTo(lowest) > 0 && v.compareTo(this.latestAutoTipVersion) < 1;
        }).collect(Collectors.toList());
    }

    public String getHypixelHeader() {
        return hypixelHeader;
    }

    public int getXpPerTipSent() {
        return xpPerTipSent;
    }

    public int getXpPerTipReceived() {
        return xpPerTipReceived;
    }

    public LocalDate getXpChangeDate() {
        return xpChangeDate;
    }

    public List<GameGroup> getGameGroups() {
        return gameGroups;
    }

    public List<GameAlias> getGameAliases() {
        return gameAliases;
    }

    public List<Message> getMessages() {
        return messages;
    }

    public List<StatsMessage> getStatsMessages() {
        return statsMessages;
    }

    public static class GameGroup {

        private String name;
        private Set<String> games;

        public String getName() {
            return name;
        }

        public Set<String> getGames() {
            return games;
        }

    }

    public static class GameAlias {

        private String alias;
        private List<String> aliases;

        private String game;
        private List<String> games;

        public List<String> getAliases() {
            if (alias != null) {
                return Collections.singletonList(alias);
            }
            return aliases;
        }

        public List<String> getGames() {
            if (game != null) {
                return Collections.singletonList(game);
            }
            return games;
        }

    }
}