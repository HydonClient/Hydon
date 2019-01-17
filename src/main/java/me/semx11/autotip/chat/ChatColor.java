package me.semx11.autotip.chat;

import java.util.regex.Pattern;

public enum ChatColor {

    GREEN('a'),
    RED('c'),
    YELLOW('e');

    private static final Pattern PATTERN = Pattern.compile("(?i)\\u00a7[0-9A-FK-OR]");

    private char formattingCode;

    ChatColor(char formattingCode) {
        this.formattingCode = formattingCode;
    }

    @Override
    public String toString() {
        return "\u00a7" + formattingCode;
    }

    public static String stripFormatting(String text) {
        return text == null ? null : PATTERN.matcher(text).replaceAll("");
    }
}
