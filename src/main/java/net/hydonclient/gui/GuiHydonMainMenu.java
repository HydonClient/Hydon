package net.hydonclient.gui;

import net.hydonclient.Hydon;
import net.hydonclient.gui.enums.EnumBackground;
import net.hydonclient.gui.main.HydonMainGui;
import net.hydonclient.gui.misc.GuiConfirmQuit;
import net.hydonclient.ttf.HydonFonts;
import net.hydonclient.ttf.MinecraftFontRenderer;
import net.hydonclient.util.AnimationUtil;
import net.hydonclient.util.maps.Images;
import net.hydonclient.util.ResolutionUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiButtonLanguage;
import net.minecraft.client.gui.GuiLanguage;
import net.minecraft.client.gui.GuiMultiplayer;
import net.minecraft.client.gui.GuiOptions;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiSelectWorld;
import net.minecraft.client.gui.GuiYesNo;
import net.minecraft.client.gui.GuiYesNoCallback;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.resources.I18n;
import net.minecraft.world.demo.DemoWorldServer;
import net.minecraft.world.storage.ISaveFormat;
import net.minecraft.world.storage.WorldInfo;

public class GuiHydonMainMenu extends GuiScreen implements GuiYesNoCallback {

    private static int yMod = 0;
    private static boolean doneOnce;

    /**
     * Adds the buttons (and other controls) to the screen in question. Called when the GUI is
     * displayed and when the window resizes, the buttonList is cleared beforehand.
     */
    public void initGui() {
        int j = this.height / 3 + HydonFonts.FONT_BOLD.getHeight() + 5;

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
        switch (button.id) {
            case 0:
                mc.displayGuiScreen(new GuiOptions(this, mc.gameSettings));
                break;

            case 1:
                mc.displayGuiScreen(new GuiSelectWorld(this));
                break;

            case 2:
                mc.displayGuiScreen(new GuiMultiplayer(this));
                break;

            case 4:
                if (Hydon.SETTINGS.confirmQuitGame) {
                    Minecraft.getMinecraft().displayGuiScreen(new GuiConfirmQuit());
                } else {
                    this.mc.shutdown();
                }
                break;

            case 5:
                mc.displayGuiScreen(new GuiLanguage(this, mc.gameSettings, mc.getLanguageManager()));
                break;

            case 6:
                mc.displayGuiScreen(new GuiHydonCredits());
                break;

            case 10:
                mc.displayGuiScreen(HydonMainGui.INSTANCE);
                break;

            case 11:
                mc.launchIntegratedServer("Demo_World", "Demo_World", DemoWorldServer.demoWorldSettings);
                break;

            case 12:
                ISaveFormat isaveformat = this.mc.getSaveLoader();
                WorldInfo worldinfo = isaveformat.getWorldInfo("Demo_World");

                if (worldinfo != null) {
                    GuiYesNo guiyesno = GuiSelectWorld
                            .makeDeleteWorldYesNo(this, worldinfo.getWorldName(), 12);
                    this.mc.displayGuiScreen(guiyesno);
                }
                break;
        }
    }

    public void confirmClicked(boolean demoUser, int button) {
        if (demoUser && button == 12) {
            ISaveFormat isaveformat = this.mc.getSaveLoader();
            isaveformat.flushCache();
            isaveformat.deleteWorldDirectory("Demo_World");
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
        MinecraftFontRenderer fontRenderer = HydonFonts.FONT_REGULAR;

        this.mc.getTextureManager().bindTexture(Hydon.SETTINGS.getCurrentBackground().getLocation());
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

        GlStateManager.enableAlpha();
        GlStateManager.enableBlend();
        GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);

        super.drawScreen(mouseX, mouseY, partialTicks);

        GlStateManager.enableAlpha();
        GlStateManager.enableBlend();
        GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);

        if (yMod == 50) {
            doneOnce = true;
        }
        if (yMod < 100 && !doneOnce) {
            GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f - (1.0f / 100 * yMod * 2));
            this.mc.getTextureManager().bindTexture(EnumBackground.BACKGROUND_1.getLocation());
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

        GlStateManager.enableAlpha();
        this.mc.getTextureManager().bindTexture(Images.LOGO_V2.getLocation());
        double logoScaleFactor = ResolutionUtil.getImageScaleFactor();
        int logoWidth = (int) (Images.LOGO_V2.getWidth() * logoScaleFactor);
        int logoHeight = (int) (Images.LOGO_V2.getHeight() * logoScaleFactor);
        int logoX = (ResolutionUtil.getCurrent().getScaledWidth() - logoWidth) / 2;
        int logoY = (scaledResolution.getScaledHeight() + logoHeight) / 4 - yMod;

        GlStateManager.color(0.0f, 0.0f, 0.0f, 0.2f);
        Gui.drawModalRectWithCustomSizedTexture(logoX + 2, logoY + 2, 0, 0, logoWidth, logoHeight,
                logoWidth, logoHeight);

        GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
        Gui.drawModalRectWithCustomSizedTexture(logoX, logoY, 0, 0, logoWidth, logoHeight,
                logoWidth, logoHeight);
    }
}