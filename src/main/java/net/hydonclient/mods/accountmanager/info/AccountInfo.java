package net.hydonclient.mods.accountmanager.info;

import net.hydonclient.mods.accountmanager.util.Encryption;

public class AccountInfo {

    public String user, pass, alias, disuser;

    public AccountInfo(String user, String pass, String alias) {
        this.disuser = user;
        setUsername(user);
        this.pass = Encryption.encrypt(pass);
        this.alias = alias;
    }

    public AccountInfo(String user, String disuser, String pass, String alias) {
        this.user = user;
        this.disuser = disuser;
        this.pass = pass;
        this.alias = alias;
    }

    public void setUsername(String name) {
        if (name.contains("@")) {
            String[] reg5 = name.split("@");
            String b4 = reg5[0];
            if (b4.length() > 3) b4 = b4.substring(0, 3) + "...";
            else b4 = b4 + "...";
            String after = reg5[1];
            this.disuser = b4 + "@" + after;
        }

        this.user = Encryption.encrypt(name);
    }
}
