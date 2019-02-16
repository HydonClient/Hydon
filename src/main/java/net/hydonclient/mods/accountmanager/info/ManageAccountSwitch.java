package net.hydonclient.mods.accountmanager.info;

import com.mojang.authlib.Agent;
import com.mojang.authlib.AuthenticationService;
import com.mojang.authlib.UserAuthentication;
import com.mojang.authlib.minecraft.MinecraftSessionService;
import com.mojang.authlib.yggdrasil.YggdrasilAuthenticationService;
import com.mojang.util.UUIDTypeAdapter;
import net.hydonclient.Hydon;
import net.hydonclient.mods.accountmanager.AccountManager;
import net.hydonclient.mods.accountmanager.util.Encryption;
import net.hydonclient.mods.accountmanager.util.SessionChanger;
import net.minecraft.client.Minecraft;
import net.minecraft.util.Session;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.UUID;

public class ManageAccountSwitch {

    private final AccountManager manager = AccountManager.instance;

    private static ManageAccountSwitch accountSwitch = null;
    public AuthenticationService authService;
    public MinecraftSessionService sessionService;
    public UUID uuid;
    public UserAuthentication userAuthentication;
    private String currentUser;
    private String currentPass;

    private ManageAccountSwitch() {
        uuid = UUID.randomUUID();
        authService = new YggdrasilAuthenticationService(Minecraft.getMinecraft().getProxy(), uuid.toString());
        userAuthentication = authService.createUserAuthentication(Agent.MINECRAFT);
        sessionService = authService.createMinecraftSessionService();
    }

    public static ManageAccountSwitch getInstance() {
        if (accountSwitch == null) {
            accountSwitch = new ManageAccountSwitch();
        }

        return accountSwitch;
    }

    public Throwable setUser(String username, String password) {
        String newUser;
        String newPass;

        try {
            newUser = Encryption.decrypt(username);
        } catch (NoSuchPaddingException | NoSuchAlgorithmException | InvalidKeyException | IllegalBlockSizeException | BadPaddingException e) {
            Hydon.LOGGER.error("Failed to set user", e);
            return e;
        }

        try {
            newPass = Encryption.decrypt(password);
        } catch (NoSuchPaddingException | NoSuchAlgorithmException | InvalidKeyException | IllegalBlockSizeException | BadPaddingException e) {
            Hydon.LOGGER.error("Failed to set password", e);
            return e;
        }

        userAuthentication.logOut();
        userAuthentication.setUsername(newUser);
        userAuthentication.setPassword(newPass);

        try {
            userAuthentication.logIn();
            Session session = new Session(
                    userAuthentication.getSelectedProfile().getName(),
                    UUIDTypeAdapter.fromUUID(userAuthentication.getSelectedProfile().getId()),
                    userAuthentication.getAuthenticatedToken(),
                    userAuthentication.getUserType().getName());
            SessionChanger.setSession(session);

            for (int i = 0; i < manager.accounts.size(); i++) {
                AccountInfo data = manager.accounts.get(i);

                if (data.user.equals(newUser) && data.pass.equals(newPass)) {
                    data.alias = session.getUsername();
                }
            }
        } catch (Exception e) {
            Hydon.LOGGER.error("Failed to authenticate / login the player.", e);
            return e;
        }

        return null;
    }

    public boolean setUserOffline(String username) {
        userAuthentication.logOut();
        Session session = new Session(username, username, "0", "legacy");
        try {
            SessionChanger.setSession(session);
        } catch (Exception e) {
            Hydon.LOGGER.error("Failed to logout the player.", e);
        }
        return true;
    }
}
