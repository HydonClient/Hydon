package net.hydonclient.mods.accountmanager.gui;

import net.hydonclient.mods.accountmanager.AccountManager;
import net.hydonclient.mods.accountmanager.info.AccountInfo;
import net.hydonclient.mods.accountmanager.util.Encryption;
import net.hydonclient.mods.accountmanager.util.GuiExtendedTextField;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiTextField;
import org.lwjgl.input.Keyboard;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

public class AddAccount extends GuiScreen {

    private String lastName = "";
    private String lastPass = "";
    private String lastAlias = "";

    private GuiExtendedTextField userField;
    private GuiExtendedTextField passField;
    private GuiTextField aliasField;

    private GuiButton submit;
    private GuiButton breturn;
    private GuiButton showuser;
    private GuiButton showpass;

    private boolean newAlt;
    private AccountInfo alt;

    public int y = super.height / 4 + 120 + -16;

    AddAccount() {
        newAlt = true;
    }

    AddAccount(String user, String pass, String alias, String encryption, AccountInfo alt) {
        this.lastAlias = alias;
        try {
            lastName = Encryption.decrypt(user);
        } catch (NoSuchPaddingException | NoSuchAlgorithmException | InvalidKeyException | IllegalBlockSizeException | BadPaddingException e) {
            lastName = "Wrong password";
        }

        try {
            lastPass = Encryption.decrypt(pass);
        } catch (NoSuchPaddingException | NoSuchAlgorithmException | InvalidKeyException | IllegalBlockSizeException | BadPaddingException e) {
            lastPass = "Wrong password";
        }

        this.alt = alt;
        newAlt = false;
    }

    @Override
    public void initGui() {
        y = 40;
        int width = 180;
        int height = 20;
        int space = 14;

        if (userField != null) lastName = userField.getText();
        if (passField != null) lastPass = passField.getText();
        if (aliasField != null) lastAlias = aliasField.getText();

        userField = new GuiExtendedTextField(0, super.width / 2 - 100, y, width, height);
        y += space + height;
        passField = new GuiExtendedTextField(1, super.width / 2 - 100, y, width, height);
        y += space + height;
        aliasField = new GuiTextField(2, mc.fontRendererObj, super.width / 2 - 100, y, width, height);

        userField.setMaxStringLength(512);
        passField.setMaxStringLength(512);
        aliasField.setMaxStringLength(512);

        userField.setText(lastName);
        passField.setText(lastPass);
        aliasField.setText(lastAlias);

        userField.setSecret(true);
        passField.setSecret(true);

        buttonList.add(submit = new GuiButton(3, super.width / 2 - 100, super.height / 4 + 120 + -16, 100, 20, "Submit"));
        buttonList.add(breturn = new GuiButton(4, super.width / 2 + 2, super.height / 4 + 120 + -16, 100, 20, "Return"));
        y -= space + height;
        buttonList.add(showuser = new GuiButton(5, super.width / 2 - 100 + width + 2, y, 100, 20, "Show"));
        y -= space + height;
        buttonList.add(showpass = new GuiButton(6, super.width / 2 - 100 + width + 2, y, 100, 20, "Show"));
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        drawDefaultBackground();
        drawCenteredString(fontRendererObj, "Add Account", width / 2, 7, -1);

        drawString(fontRendererObj, "Username", super.width / 2 - 100, 29, 0xffffffff);
        drawString(fontRendererObj, "Password", super.width / 2 - 100, 29 + 34, 0xffffffff);
        drawString(fontRendererObj, "Alias", super.width / 2 - 100, 29 + 68, 0xffffffff);

        userField.drawTextBox();
        passField.drawTextBox();
        aliasField.drawTextBox();

        super.drawScreen(mouseX, mouseY, partialTicks);
    }

    @Override
    protected void keyTyped(char typedChar, int keyCode) throws IOException {
        Keyboard.enableRepeatEvents(true);

        userField.textboxKeyTyped(typedChar, keyCode);
        passField.textboxKeyTyped(typedChar, keyCode);
        aliasField.textboxKeyTyped(typedChar, keyCode);
    }

    @Override
    protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
        userField.mouseClicked(mouseX, mouseY, mouseButton);
        passField.mouseClicked(mouseX, mouseY, mouseButton);
        aliasField.mouseClicked(mouseX, mouseY, mouseButton);
        super.mouseClicked(mouseX, mouseY, mouseButton);
    }

    @Override
    protected void actionPerformed(GuiButton button) throws IOException {
        switch (button.id) {
            case 3:
                if (newAlt) {
                    AccountManager.instance.accounts.add(new AccountInfo(userField.getText(), passField.getText(), aliasField.getText()));
                } else {
                    alt.pass = Encryption.encrypt(passField.getText());
                    alt.setUsername(userField.getText());
                    alt.alias = aliasField.getText();
                }
                AccountManager.instance.saveAccounts();
                mc.displayGuiScreen(new SwitchAccounts());
                break;

            case 4:
                mc.displayGuiScreen(new SwitchAccounts());
                break;

            case 5:
                passField.showText(!passField.isTextShowing());
                break;

            case 6:
                userField.showText(!userField.isTextShowing());
                break;
        }
    }
}
