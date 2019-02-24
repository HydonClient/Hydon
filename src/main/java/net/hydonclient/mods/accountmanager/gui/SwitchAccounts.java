package net.hydonclient.mods.accountmanager.gui;

import net.hydonclient.Hydon;
import net.hydonclient.mods.accountmanager.AccountManager;
import net.hydonclient.mods.accountmanager.info.AccountInfo;
import net.hydonclient.mods.accountmanager.info.ManageAccountSwitch;
import net.hydonclient.mods.accountmanager.util.Encryption;
import net.hydonclient.mods.accountmanager.util.GuiUtility;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.client.multiplayer.GuiConnecting;
import net.minecraft.util.MathHelper;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;

import javax.swing.*;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;

public class SwitchAccounts extends GuiScreen {

    private final Minecraft mc = Minecraft.getMinecraft();
    private final AccountManager manager = AccountManager.instance;

    private AccountInfo alt = null;
    private Throwable failed;

    private int page = 0;

    private boolean help = false;

    private final GuiTextField textBox = new GuiTextField(0, mc.fontRendererObj, 4, 4, 80, 20);
    private GuiTextField searchBox;

    public GuiButton add;
    private GuiButton edit;
    public GuiButton remove;
    private GuiButton change;
    private GuiButton reconnect;
    private GuiButton importAccount;
    private GuiButton helpManager;
    private GuiButton plus;
    private GuiButton minus;

    public SwitchAccounts() {
        manager.accounts.clear();
        Encryption.setKey(textBox.getText());
        manager.loadAccounts();

        if (!manager.accounts.isEmpty()) alt = manager.accounts.get(0);
    }

    @Override
    public void updateScreen() {
        textBox.updateCursorCounter();
    }

    @Override
    public void initGui() {
        textBox.setFocused(false);
        textBox.setText("Password");

        int bwidth = 100;
        int bheight = 20;
        int dist = 4;

        int middle = width / 2 - bwidth / 2;
        int bottom = height - bheight - 4;

        int swidth = 98;
        searchBox = new GuiTextField(1, mc.fontRendererObj, width - swidth - 1 - dist, 14, swidth, 20);
        searchBox.setFocused(false);

        buttonList.clear();
        buttonList.add(add = new GuiButton(2, middle - (bwidth / 2) - (dist / 2), bottom - bheight - dist, bwidth, bheight, "Add Account"));
        buttonList.add(edit = new GuiButton(3, middle - (bwidth / 2) - (dist / 2), bottom, bwidth, bheight, "Edit Account"));
        buttonList.add(remove = new GuiButton(4, middle + (bwidth / 2) + (dist / 2), bottom - bheight - dist, bwidth, bheight, "Remove Account"));
        buttonList.add(change = new GuiButton(5, middle + (bwidth / 2) + (dist / 2), bottom, bwidth, bheight, "Change Account"));
        buttonList.add(reconnect = new GuiButton(6, middle + bwidth + 6 + bwidth / 2, bottom - bheight - dist, bwidth / 2, bheight, "Reconnect"));
        buttonList.add(importAccount = new GuiButton(7, width - bwidth - dist, 34 + dist, bwidth, bheight, "Import Accounts"));
        buttonList.add(helpManager = new GuiButton(8, width - 24, bottom, 20, 20, "?"));
        buttonList.add(plus = new GuiButton(9, middle + bwidth + 6 + bwidth / 2, bottom, bwidth / 2, bheight, "Next"));
        buttonList.add(minus = new GuiButton(10, middle - bwidth - dist - (dist / 2), bottom, bwidth / 2, bheight, "Previous"));
        if (manager.accounts.isEmpty()) change.enabled = false;
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        drawDefaultBackground();
        drawCenteredString(fontRendererObj, "Accounts", width / 2, 7, -1);

        if (failed != null) {
            drawCenteredString(fontRendererObj, "\u00A7c Failed Login" + failed.getLocalizedMessage(), width / 2, height - 60, -1);
        }

        drawString(fontRendererObj, "\u00A7cLogged in as:", 2, height - 20, 0xffffffff);
        String user = mc.getSession().getUsername();
        if (mc.getSession().getToken().equals("0")) user += " \u00A7cOFFLINE";
        drawString(fontRendererObj, "\u00A79" + user, 2, height - 10, 0xffffffff);

        int height = 40;
        int multi = height + 5;
        int selWidth = 200;
        int x = width / 2 - selWidth / 2;
        int maxAmount = ((this.height - 74) / multi);

        GL11.glPushMatrix();
        int i = 0;
        int re = 0;

        for (AccountInfo acc : manager.accounts) {
            if (!searchBox.getText().isEmpty() && !acc.alias.contains(searchBox.getText())) continue;

            if (i < maxAmount * page) {
                i++;
                re = i;
                continue;
            }

            if (i >= maxAmount * (page + 1)) break;

            int pos = (i - re) * multi;

            GuiUtility.drawBorderedRect(x, pos + 20, selWidth, 40, 0x55000000, 0x88000000);
//            GuiUtility.drawBorderedRect(x + 1, pos + 21, 36, 38, 0x55000000, 0x88000000);

            String alias = acc.alias;

            if (alias.length() > 20) alias = alias.substring(0, 21) + ".";
            drawString(fontRendererObj, "\u00A77Alias: " + alias, x + 42, pos + 30, -1);
            String name = acc.disuser;
            drawString(fontRendererObj, "\u00A77User: " + name, x + 42, pos + 42, -1);

            if (alt != null && alt.equals(acc)) {
                GuiUtility.drawHLine(x, pos + 20, selWidth, 0xff9999ff);
                GuiUtility.drawHLine(x, pos + 59, selWidth, 0xff9999ff);
            }

            i++;
        }

        GL11.glPopMatrix();
        String encryptionText = "§cEncryption password. §7Toggle Help for more info, Bottom right Corner";
        if (help)
            encryptionText = "§cEncryption password. §7Default is 'Password'. The password you used to create the account will be used to login and edit the account, without the right one you cannot login or edit the account.";
        mc.fontRendererObj.drawSplitString(encryptionText, 4, 30, 85, 16777215);
        String importTip = "§cImport accounts from text file, formats are §buser§7: §bpassword§7, §balias§7: §buser§7:§bpassword §cNOTE: Freezes MC while window is open.";
        mc.fontRendererObj.drawSplitString(importTip, width - 102, 60, 100, 16777215);
        String searchname = "Search Alias";
        drawString(fontRendererObj, searchname, width - 65 - mc.fontRendererObj.getStringWidth(searchname) / 2, 4, 16777215);

        textBox.drawTextBox();
        searchBox.drawTextBox();

        super.drawScreen(mouseX, mouseY, partialTicks);
    }

    @Override
    protected void keyTyped(char typedChar, int keyCode) throws IOException {
        textBox.textboxKeyTyped(typedChar, keyCode);

        if (searchBox.textboxKeyTyped(typedChar, keyCode)) {
            page = 0;
            alt = null;
        }

        Encryption.setKey(textBox.getText());

        if (keyCode == Keyboard.KEY_ESCAPE) {
            mc.displayGuiScreen(null);
        }
    }

    @Override
    protected void mouseClicked(int mx, int my, int mouseButton) throws IOException {
        textBox.mouseClicked(mx, my, mouseButton);
        searchBox.mouseClicked(mx, my, mouseButton);

        Encryption.setKey(textBox.getText());

        int height = 40;
        int multi = height + 5;
        int selWidth = 200;
        int x = width / 2 - selWidth / 2;

        int maxAmount = ((this.height - 74) / multi);

        int i = 0;
        int re = 0;

        for (AccountInfo acc : manager.accounts) {
            if (!searchBox.getText().isEmpty() && !acc.alias.contains(searchBox.getText())) continue;

            if (i < maxAmount * page) {
                i++;
                re = i;
                continue;
            }

            if (i >= maxAmount * (page + 1)) break;
            int pos = (i - re) * multi + 20;

            if (mx >= x && my >= pos && mx < x + selWidth && my < pos + 40) {
                alt = acc;
            }
            i++;
        }

        super.mouseClicked(mx, my, mouseButton);
    }

    @Override
    protected void actionPerformed(GuiButton button) throws IOException {
        switch (button.id) {
            case 2:
                mc.displayGuiScreen(new AddAccount());
                if (!manager.accounts.isEmpty()) add.enabled = true;

                break;
            case 3:
                if (alt == null) break;

                mc.displayGuiScreen(new AddAccount(alt.user, alt.pass, alt.alias, textBox.getText(), alt));
                if (!manager.accounts.isEmpty()) change.enabled = true;
                break;

            case 4:
                if (alt == null) break;

                try {
                    manager.accounts.remove(alt);
                    manager.saveAccounts();
                    if (manager.accounts.isEmpty()) change.enabled = false;
                    alt = null;
                } catch (Exception e) {
                    Hydon.LOGGER.warn("Failed to remove account", e);
                }
                break;

            case 5:
                if (manager.accounts.isEmpty()) break;
                if (alt == null) break;

                if (Keyboard.isKeyDown(Keyboard.KEY_LSHIFT)) {
                    ManageAccountSwitch.getInstance().setUserOffline(alt.alias);
                    failed = null;
                    mc.displayGuiScreen(null);
                    break;
                }

                try {
                    failed = ManageAccountSwitch.getInstance().setUser(alt.user, alt.pass);
                    if (failed == null) {
                        mc.displayGuiScreen(null);
                    }
                } catch (Exception e) {
                    Hydon.LOGGER.error("Failed to authenticate / login the player.", e);
                }
                break;

            case 6:
                if (manager.accounts.isEmpty()) return;
                if (alt == null) break;

                if (Keyboard.isKeyDown(Keyboard.KEY_LSHIFT)) {
                    ManageAccountSwitch.getInstance().setUserOffline(alt.alias);
                    failed = null;
                    mc.displayGuiScreen(null);
                    break;
                }
                try {
                    failed = ManageAccountSwitch.getInstance().setUser(alt.user, alt.pass);
                    if (failed == null) {
                        mc.displayGuiScreen(null);
                    }
                } catch (Exception e) {
                    Hydon.LOGGER.error("Failed to authenticate / login the player.", e);
                }

                if (mc.getCurrentServerData() != null) {
                    mc.theWorld.sendQuittingDisconnectingPacket();
                    mc.displayGuiScreen(new GuiConnecting(this, this.mc, mc.getCurrentServerData()));
                }
                break;

            case 7:
                JFileChooser chooser = new JFileChooser();
                chooser.setFileView(null);

                try {
                    UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
                } catch (IllegalAccessException | InstantiationException | UnsupportedLookAndFeelException | ClassNotFoundException e) {
                    Hydon.LOGGER.error("Failed to set look and feel", e);
                }

                chooser.showOpenDialog(null);
                if (chooser.getSelectedFile() == null) break;

                List<String> alts = null;

                try {
                    alts = Files.readAllLines(chooser.getSelectedFile().toPath());
                } catch (IOException e) {
                    Hydon.LOGGER.error("Failed to read file", e);
                }

                if (alts != null) {
                    for (String str : alts) {
                        if (!str.contains(":")) continue;
                        String[] spl = str.split(":");

                        String user = str.split(":")[0];
                        String pass = str.split(":")[1];
                        String alias = "Imported alt";

                        if (spl.length == 2) {
                            user = spl[0];
                            pass = spl[1];
                        } else if (spl.length == 3) {
                            alias = spl[0];
                            user = spl[1];
                            pass = spl[2];
                        }

                        AccountInfo data = new AccountInfo(user, pass, alias);
                        contLabel:
                        for (AccountInfo acc : manager.accounts) {
                            if (acc.user.equals(data.user)) {
                                continue contLabel;
                            }
                        }
                        manager.accounts.add(data);
                    }
                    manager.saveAccounts();
                }
                break;

            case 8:
                help = !help;
                break;

            case 9:
                page++;
                if (page >= maxPages() - 1) page = maxPages() - 1;
                alt = null;
                break;

            case 10:
                page--;
                if (page < 0) page = 0;
                alt = null;
                break;
        }
    }

    private int maxPages() {
        int amount = manager.accounts.size();

        if (!searchBox.getText().isEmpty()) {
            amount = 0;

            for (AccountInfo acc : manager.accounts) {
                if (acc.alias.contains(searchBox.getText())) amount++;
            }
        }

        return MathHelper.ceiling_float_int(amount / (float) ((height - 74) / 45));
    }
}
