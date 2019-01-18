package net.hydonclient.gui;

import com.google.common.collect.Lists;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URI;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Random;

import net.hydonclient.Hydon;
import net.hydonclient.gui.main.HydonMainGui;
import net.hydonclient.ttf.HydonFonts;
import net.hydonclient.ttf.MinecraftFontRenderer;
import net.hydonclient.util.AnimationUtil;
import net.hydonclient.util.ChatColor;
import net.hydonclient.util.Images;
import net.hydonclient.util.ResolutionUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiButtonLanguage;
import net.minecraft.client.gui.GuiConfirmOpenLink;
import net.minecraft.client.gui.GuiLanguage;
import net.minecraft.client.gui.GuiMultiplayer;
import net.minecraft.client.gui.GuiOptions;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiSelectWorld;
import net.minecraft.client.gui.GuiYesNo;
import net.minecraft.client.gui.GuiYesNoCallback;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.realms.RealmsBridge;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.demo.DemoWorldServer;
import net.minecraft.world.storage.ISaveFormat;
import net.minecraft.world.storage.WorldInfo;
import org.apache.commons.io.Charsets;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GLContext;

public class GuiHydonMainMenu extends GuiScreen implements GuiYesNoCallback {

    private static final Logger logger = LogManager.getLogger();
    private static final Random RANDOM = new Random();
    /**
     * The splash message.
     */
    private String splashText;
    /**
     * The Object object utilized as a thread lock when performing non thread-safe operations
     */
    private final Object threadLock = new Object();
    /**
     * OpenGL graphics card warning.
     */
    private String openGLWarning1;
    /**
     * OpenGL graphics card warning.
     */
    private String openGLWarning2;
    /**
     * Link to the Mojang Support about minimum requirements
     */
    private String openGLWarningLink;
    private static final ResourceLocation splashTexts = new ResourceLocation("texts/splashes.txt");
    private static final String field_96138_a = "Please click " + ChatColor.UNDERLINE + "here" + ChatColor.RESET + " for more information.";
    private int field_92024_r;
    private int field_92022_t;
    private int field_92021_u;
    private int field_92020_v;
    private int field_92019_w;
    private boolean field_183502_L;
    private GuiScreen field_183503_M;

    private static int yMod = 0;

    public GuiHydonMainMenu() {
        this.openGLWarning2 = field_96138_a;
        this.field_183502_L = false;
        this.splashText = "missingno";
        BufferedReader bufferedreader = null;

        try {
            List<String> list = Lists.newArrayList();
            bufferedreader = new BufferedReader(new InputStreamReader(
                Minecraft.getMinecraft().getResourceManager().getResource(splashTexts)
                    .getInputStream(), Charsets.UTF_8));
            String s;

            while ((s = bufferedreader.readLine()) != null) {
                s = s.trim();

                if (!s.isEmpty()) {
                    list.add(s);
                }
            }

            if (!list.isEmpty()) {
                do {
                    this.splashText = list.get(RANDOM.nextInt(list.size()));
                } while (this.splashText.hashCode() == 125780783);
            }
        } catch (IOException ignored) {
        } finally {
            if (bufferedreader != null) {
                try {
                    bufferedreader.close();
                } catch (IOException ignored) {

                }
            }
        }
        this.openGLWarning1 = "";

        if (!GLContext.getCapabilities().OpenGL20 && !OpenGlHelper.areShadersSupported()) {
            this.openGLWarning1 = I18n.format("title.oldgl1");
            this.openGLWarning2 = I18n.format("title.oldgl2");
            this.openGLWarningLink = "https://help.mojang.com/customer/portal/articles/325948?ref=game";
        }
    }

    private boolean func_183501_a() {
        return Minecraft.getMinecraft().gameSettings.getOptionOrdinalValue(GameSettings.Options.REALMS_NOTIFICATIONS) && this.field_183503_M != null;
    }

    /**
     * Called from the main game loop to update the screen.
     */
    public void updateScreen() {
        if (this.func_183501_a()) {
            this.field_183503_M.updateScreen();
        }
    }

    /**
     * Returns true if this GUI should pause the game when it is displayed in single-player
     */
    public boolean doesGuiPauseGame() {
        return false;
    }

    /**
     * Fired when a key is typed (except F11 which toggles full screen). This is the equivalent of
     * KeyListener.keyTyped(KeyEvent e). Args : character (character on the key), keyCode (lwjgl
     * Keyboard key code)
     */
    protected void keyTyped(char typedChar, int keyCode) {
    }

    /**
     * Adds the buttons (and other controls) to the screen in question. Called when the GUI is
     * displayed and when the window resizes, the buttonList is cleared beforehand.
     */
    public void initGui() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());

        if (calendar.get(Calendar.MONTH) + 1 == 12 && calendar.get(Calendar.DATE) == 24) {
            this.splashText = "Merry X-mas!";
        } else if (calendar.get(Calendar.MONTH) + 1 == 1
            && calendar.get(Calendar.DATE) == Calendar.SUNDAY) {
            this.splashText = "Happy new year!";
        } else if (calendar.get(Calendar.MONTH) + 1 == 10 && calendar.get(Calendar.DATE) == 31) {
            this.splashText = "OOoooOOOoooo! Spooky!";
        }

        int j = this.height / 3 + HydonFonts.PRODUCT_SANS_BOLD.getHeight() + 5;

        if (this.mc.isDemo()) {
            this.addDemoButtons(j);
        } else {
            this.addSingleplayerMultiplayerButtons(j);
        }

        this.buttonList.add(new GuiButton(10, this.width / 2 - 101, j + 22, 100, 20,
            "Hydon Settings"));
        this.buttonList.add(new GuiButton(0, this.width / 2 + 1, j + 22, 100, 20,
            "Minecraft Settings"));

        this.buttonList.add(new GuiButton(6, this.width / 2 - 101, j + 44, 100, 20,
            "Credits"));
        this.buttonList.add(new GuiButton(4, this.width / 2 + 1, j + 44, 100, 20,
            I18n.format("menu.quit")));

        this.buttonList.add(new GuiButtonLanguage(5, this.width / 2 - 123, j + 44));

        synchronized (this.threadLock) {
            int field_92023_s = this.fontRendererObj.getStringWidth(this.openGLWarning1);
            this.field_92024_r = this.fontRendererObj.getStringWidth(this.openGLWarning2);
            int k = Math.max(field_92023_s, this.field_92024_r);
            this.field_92022_t = (this.width - k) / 2;
            this.field_92021_u = this.buttonList.get(0).yPosition - 24;
            this.field_92020_v = this.field_92022_t + k;
            this.field_92019_w = this.field_92021_u + 24;
        }

        this.mc.setConnectedToRealms(false);

        if (Minecraft.getMinecraft().gameSettings
            .getOptionOrdinalValue(GameSettings.Options.REALMS_NOTIFICATIONS)
            && !this.field_183502_L) {
            RealmsBridge realmsbridge = new RealmsBridge();
            this.field_183503_M = realmsbridge.getNotificationScreen(this);
            this.field_183502_L = true;
        }

        if (this.func_183501_a()) {
            this.field_183503_M.setGuiSize(this.width, this.height);
            this.field_183503_M.initGui();
        }
    }

    /**
     * Adds Singleplayer and Multiplayer buttons on Main Menu for players who have bought the game.
     */
    private void addSingleplayerMultiplayerButtons(int p_73969_1_) {
        this.buttonList.add(new GuiButton(1, this.width / 2 - 101, p_73969_1_, 100, 20,
            I18n.format("menu.singleplayer")));
        this.buttonList.add(new GuiButton(2, this.width / 2 + 1, p_73969_1_, 100, 20,
            I18n.format("menu.multiplayer")));
    }

    /**
     * Adds Demo buttons on Main Menu for players who are playing Demo.
     */
    private void addDemoButtons(int p_73972_1_) {
        this.buttonList.add(new GuiButton(11, this.width / 2 - 100, p_73972_1_,
            I18n.format("menu.playdemo")));
        GuiButton buttonResetDemo;
        this.buttonList.add(buttonResetDemo = new GuiButton(12, this.width / 2 - 100,
            p_73972_1_ + 24, I18n.format("menu.resetdemo")));
        ISaveFormat isaveformat = this.mc.getSaveLoader();
        WorldInfo worldinfo = isaveformat.getWorldInfo("Demo_World");

        if (worldinfo == null) {
            buttonResetDemo.enabled = false;
        }
    }

    /**
     * Called by the controls from the buttonList when activated. (Mouse pressed for buttons)
     */
    protected void actionPerformed(GuiButton button) {
        if (button.id == 0) {
            this.mc.displayGuiScreen(new GuiOptions(this, this.mc.gameSettings));
        }

        if (button.id == 5) {
            this.mc.displayGuiScreen(
                new GuiLanguage(this, this.mc.gameSettings, this.mc.getLanguageManager()));
        }

        if (button.id == 1) {
            this.mc.displayGuiScreen(new GuiSelectWorld(this));
        }

        if (button.id == 2) {
            this.mc.displayGuiScreen(new GuiMultiplayer(this));
        }

        if (button.id == 4) {
            this.mc.shutdown();
        }

        if (button.id == 11) {
            this.mc.launchIntegratedServer("Demo_World", "Demo_World",
                DemoWorldServer.demoWorldSettings);
        }

        if (button.id == 6) {
            this.mc.displayGuiScreen(new GuiHydonCredits());
        }

        if (button.id == 10) {
            this.mc.displayGuiScreen(HydonMainGui.INSTANCE);
        }

        if (button.id == 12) {
            ISaveFormat isaveformat = this.mc.getSaveLoader();
            WorldInfo worldinfo = isaveformat.getWorldInfo("Demo_World");

            if (worldinfo != null) {
                GuiYesNo guiyesno = GuiSelectWorld
                    .makeDeleteWorldYesNo(this, worldinfo.getWorldName(), 12);
                this.mc.displayGuiScreen(guiyesno);
            }
        }
    }

    public void confirmClicked(boolean result, int id) {
        if (result && id == 12) {
            ISaveFormat isaveformat = this.mc.getSaveLoader();
            isaveformat.flushCache();
            isaveformat.deleteWorldDirectory("Demo_World");
            this.mc.displayGuiScreen(this);
        } else if (id == 13) {
            if (result) {
                try {
                    Class<?> oclass = Class.forName("java.awt.Desktop");
                    Object object = oclass.getMethod("getDesktop", new Class[0])
                        .invoke(null);
                    oclass.getMethod("browse", new Class[]{URI.class})
                        .invoke(object, new URI(this.openGLWarningLink));
                } catch (Throwable throwable) {
                    logger.error("Couldn\'t open link", throwable);
                }
            }

            this.mc.displayGuiScreen(this);
        }
    }

    private double time = 0;

    /**
     * Draws the screen and all the components in it. Args : mouseX, mouseY, renderPartialTicks
     */
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);

        ScaledResolution scaledResolution = new ScaledResolution(Minecraft.getMinecraft());
        MinecraftFontRenderer fontRenderer = HydonFonts.PRODUCT_SANS_REGULAR;

        this.mc.getTextureManager().bindTexture(Images.ALT_BG_1.getLocation());
        Gui.drawModalRectWithCustomSizedTexture(0, 0, 0, 0,
            ResolutionUtil.getCurrent().getScaledWidth(),
            ResolutionUtil.getCurrent().getScaledHeight(),
            ResolutionUtil.getCurrent().getScaledWidth(),
            ResolutionUtil.getCurrent().getScaledHeight());

        fontRenderer.drawString("Hydon (" + Hydon.VERSION + ")", 3,
            scaledResolution.getScaledHeight() - fontRenderer.getHeight() * 2, 0xffffff);
        fontRenderer.drawString("https://hydonclient.net", 3,
            scaledResolution.getScaledHeight() - fontRenderer.getHeight(), 0xffffff);

        fontRenderer.drawString("Not affiliated with",
            scaledResolution.getScaledWidth() - fontRenderer.getStringWidth("Not affiliated with")
                - 3, scaledResolution.getScaledHeight() - fontRenderer.getHeight() * 2, 0xffffff);
        fontRenderer.drawString("M O J A N G     A B",
            scaledResolution.getScaledWidth() - fontRenderer.getStringWidth("M O J A N G     A B")
                - 3, scaledResolution.getScaledHeight() - fontRenderer.getHeight(), 0xffffff);

        if (this.openGLWarning1 != null && this.openGLWarning1.length() > 0) {
            drawRect(this.field_92022_t - 2, this.field_92021_u - 2, this.field_92020_v + 2,
                this.field_92019_w - 1, 1428160512);
            this.drawString(this.fontRendererObj, this.openGLWarning1, this.field_92022_t,
                this.field_92021_u, -1);
            this.drawString(this.fontRendererObj, this.openGLWarning2,
                (this.width - this.field_92024_r) / 2,
                this.buttonList.get(0).yPosition - 12, -1);
        }

        GlStateManager.enableAlpha();
        GlStateManager.enableBlend();
        GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);

        super.drawScreen(mouseX, mouseY, partialTicks);

        if (this.func_183501_a()) {
            this.field_183503_M.drawScreen(mouseX, mouseY, partialTicks);
        }

        GlStateManager.enableAlpha();
        GlStateManager.enableBlend();
        GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);

        if (yMod < 100) {
            GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f - 1.0f / 60f * yMod);
            this.mc.getTextureManager().bindTexture(Images.ALT_BG_1.getLocation());
            Gui.drawModalRectWithCustomSizedTexture(0, 0, 0, 0,
                ResolutionUtil.getCurrent().getScaledWidth(),
                ResolutionUtil.getCurrent().getScaledHeight(),
                ResolutionUtil.getCurrent().getScaledWidth(),
                ResolutionUtil.getCurrent().getScaledHeight());
        }

        GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);

        if (time < 40) {
            yMod = (int) AnimationUtil.easeOut((float) time, 0, 100 - yMod, 40f);
            time++;
        }

//        GL11.glHint(GL11.GL_POLYGON_SMOOTH_HINT, GL11.GL_NICEST);
//        GL11.glEnable(GL11.GL_POLYGON_SMOOTH);
        GlStateManager.enableAlpha();
        this.mc.getTextureManager().bindTexture(Images.LOGO.getLocation());
        double logoScaleFactor = ResolutionUtil.getImageScaleFactor();
        int logoWidth = (int) (Images.LOGO.getWidth() * logoScaleFactor);
        int logoHeight = (int) (Images.LOGO.getHeight() * logoScaleFactor);
        int logoX = (ResolutionUtil.getCurrent().getScaledWidth() - logoWidth) / 2;
        int logoY = (scaledResolution.getScaledHeight() + logoHeight) / 4 - yMod;

        GlStateManager.color(0.0f, 0.0f, 0.0f, 0.2f);
        Gui.drawModalRectWithCustomSizedTexture(logoX + 2, logoY + 2, 0, 0, logoWidth, logoHeight,
            logoWidth, logoHeight);

        GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
        Gui.drawModalRectWithCustomSizedTexture(logoX, logoY, 0, 0, logoWidth, logoHeight,
            logoWidth, logoHeight);
    }

    /**
     * Called when the mouse is clicked. Args : mouseX, mouseY, clickedButton
     */
    protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
        super.mouseClicked(mouseX, mouseY, mouseButton);

        synchronized (this.threadLock) {
            if (this.openGLWarning1.length() > 0 && mouseX >= this.field_92022_t
                && mouseX <= this.field_92020_v && mouseY >= this.field_92021_u
                && mouseY <= this.field_92019_w) {
                GuiConfirmOpenLink guiconfirmopenlink = new GuiConfirmOpenLink(this,
                    this.openGLWarningLink, 13, true);
                guiconfirmopenlink.disableSecurityWarning();
                this.mc.displayGuiScreen(guiconfirmopenlink);
            }
        }
    }

    /**
     * Called when the screen is unloaded. Used to disable keyboard repeat events
     */
    public void onGuiClosed() {
        if (this.field_183503_M != null) {
            this.field_183503_M.onGuiClosed();
        }
    }
}