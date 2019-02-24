package com.kodingking.mods.core;

import com.kodingking.mods.core.auth.MojangAuth;
import me.semx11.autotip.chat.ChatColor;
import net.hydonclient.util.Multithreading;

public class KodingMod {

    public static final String API_ENDPOINT = "http://api.kodingking.com";
//    public static final String API_ENDPOINT = "http://localhost:8080";

    private static KodingMod instance;

    private String modId, name, version, chatPrefix;
    private MojangAuth auth;

    public KodingMod(String modId, String name, String version) {
        KodingMod.instance = this;
        this.modId = modId;
        this.name = name;
        this.version = version;
        this.chatPrefix = ChatColor.WHITE + "" + ChatColor.BOLD + this.name
            + ChatColor.AQUA + " > " + ChatColor.WHITE;
    }

    public void init() {
        this.auth = new MojangAuth(modId, version);
        Multithreading.run(auth::authenticate);
    }

    public static KodingMod getInstance() {
        return instance;
    }

    public String getModId() {
        return modId;
    }

    public String getName() {
        return name;
    }

    public String getVersion() {
        return version;
    }

    public String getChatPrefix() {
        return chatPrefix;
    }

    public MojangAuth getAuth() {
        return auth;
    }
}
