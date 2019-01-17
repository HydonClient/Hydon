package me.semx11.autotip.util;

import me.semx11.autotip.chat.ChatColor;

import java.util.Arrays;
import java.util.List;

public class VersionInfo {

    private AutoTipVersion autoTipVersion;
    private Severity severity;
    private List<String> changelog;

    public VersionInfo(AutoTipVersion autoTipVersion, Severity severity, String... changelog) {
        this(autoTipVersion, severity, Arrays.asList(changelog));
    }

    public VersionInfo(AutoTipVersion autoTipVersion, Severity severity, List<String> changelog) {
        this.autoTipVersion = autoTipVersion;
        this.severity = severity;
        this.changelog = changelog;
    }

    public AutoTipVersion getAutoTipVersion() {
        return autoTipVersion;
    }

    public Severity getSeverity() {
        return severity;
    }

    public List<String> getChangelog() {
        return changelog;
    }

    public enum Severity {
        OPTIONAL, ADVISED, CRITICAL;

        public String toColoredString() {
            ChatColor color;
            switch (this) {
                default:
                case OPTIONAL:
                    color = ChatColor.GREEN;
                    break;
                case ADVISED:
                    color = ChatColor.YELLOW;
                    break;
                case CRITICAL:
                    color = ChatColor.RED;
                    break;
            }
            return color + this.toString();
        }
    }
}
