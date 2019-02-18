package net.hydonclient.mods.accountmanager;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import net.hydonclient.Hydon;
import net.hydonclient.mods.accountmanager.info.AccountInfo;
import net.hydonclient.mods.Mod;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Map;

@Mod.Info(name = "AccountManager", author = "Its_its", version = "1.6")
public class AccountManager extends Mod {

    /**
     * credit to https://www.minecraftforum.net/members/Its_Its for the mod
     * original source code: https://github.com/Its-its/AltManager
     * all the base code is by him (with modifications to fit for Hydon)
     * port by asbyth
     *
     * everything put into this mod is encrypted to save the information that only YOU will see
     * it is only unencrypted when the client launches & detects accounts
     * all the data is stored to your minecraft folder (.minecraft/Hydon/accounts.json)
     * everything there is encrypted and unreadable (username, password, email is cut off
     * after the first 3 characters)
     *
     * if you still feel there is a security risk / some vulnerability in the code with the encryption
     * PLEASE contact asbyth (asbyth#4717 on Discord, https://github.com/asbyth), or Koding,
     * or make a pull request with code that can improve it. thank you, have a good time
     */

    public static AccountManager instance;
    private final File accountLocation = new File(Hydon.STORAGE_FOLDER, "/accounts.json");
    private final Gson gson = new GsonBuilder().setPrettyPrinting().create();
    public final ArrayList<AccountInfo> accounts = new ArrayList<>();

    @Override
    public void load() {
        instance = this;

        if (!accountLocation.exists())
            saveAccounts();
        else
            loadAccounts();
    }

    public void loadAccounts() {
        try {
            final BufferedReader reader = new BufferedReader(new FileReader(accountLocation));
            final JsonObject json = (JsonObject) new JsonParser().parse(reader);
            reader.close();

            for (Map.Entry<String, JsonElement> entry : json.entrySet()) {
                final JsonObject element = (JsonObject) entry.getValue();

                String user = element.get("username").getAsString();
                String alias = element.get("alias").getAsString();
                String pass = element.get("password").getAsString();
                String shou = element.get("user").getAsString();
                AccountInfo accountInfo = new AccountInfo(user, shou, pass, alias);
                accounts.add(accountInfo);
            }
        } catch (IOException e) {
            Hydon.LOGGER.error("Error saving account data", e);
        }
    }

    public void saveAccounts() {
        try {
            final JsonObject json = new JsonObject();
            int x = 1;

            for (AccountInfo data : accounts) {
                final JsonObject jsonData = new JsonObject();
                jsonData.addProperty("alias", data.alias);
                jsonData.addProperty("user", data.disuser);
                jsonData.addProperty("username", data.user);
                jsonData.addProperty("password", data.pass);
                json.add("Alt #" + x, jsonData);
                x++;
            }

            final PrintWriter save = new PrintWriter(new FileWriter(accountLocation));
            save.println(gson.toJson(json));
            save.close();
        } catch (IOException e) {
            Hydon.LOGGER.error("Error saving account data", e);
        }
    }
}
