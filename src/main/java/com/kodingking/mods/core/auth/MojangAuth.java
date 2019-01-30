package com.kodingking.mods.core.auth;

import com.google.gson.JsonObject;
import com.kodingking.mods.core.KodingMod;
import com.kodingking.mods.core.util.HttpUtil;
import me.semx11.autotip.util.LoginUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.util.Session;

public class MojangAuth {

    private boolean authenticated;
    private String token;

    private String modId, modVersion;

    public MojangAuth(String modId, String modVersion) {
        this.modId = modId;
        this.modVersion = modVersion;
        this.token = "";
    }

    public void authenticate() {
        Session session = Minecraft.getMinecraft().getSession();

        if (session == null || session.getProfile() == null) {
            return;
        }

        JsonObject preResult = HttpUtil.getJson(String
            .format(KodingMod.API_ENDPOINT + "/mod/startLoading?uuid=%s&modId=%s&modVersion=%s",
                session.getProfile().getId().toString(), modId, modVersion).replace(" ", "%20"));
        if (preResult.has("success") && !preResult.get("success").getAsBoolean()) {
            this.authenticated = false;
            System.out.println("Authentication failed: LOADING");
            return;
        }

        String hash = preResult.get("hash").getAsString();

        int statusCode = LoginUtil
            .joinServer(session.getToken(), session.getPlayerID().replace("-", ""), hash);
        if (statusCode != 204) {
            this.authenticated = false;
            System.out.println("Authentication failed: JOINING");
            return;
        }


        JsonObject postResult = HttpUtil.getJson(String.format(
            KodingMod.API_ENDPOINT
                + "/mod/finishLoading?username=%s&hash=%s&uuid=%s&modId=%s&modVersion=%s&mcVersion=%s",
            session.getUsername(), hash, session.getProfile().getId().toString(),
            modId, modVersion, Minecraft.getMinecraft().getVersion()).replace(" ", "%20"));
        if (postResult.has("success") && !postResult.get("success").getAsBoolean()) {
            this.authenticated = false;
            System.out.println("Authentication failed: POST-LOAD");
            System.out.println("Details: " + postResult.get("message").getAsString());
            return;
        }

        this.authenticated = true;
        this.token = postResult.get("token").getAsString();
        System.out.println("Authentication succeed: AUTHENTIC");
    }

    public boolean isAuthenticated() {
        return authenticated;
    }

    public String getToken() {
        return token;
    }
}
