package net.hydonclient.gui.main;

import java.awt.Color;
import java.io.IOException;
import me.aycy.blockoverlay.utils.BlockOverlayMode;
import net.hydonclient.Hydon;
import net.hydonclient.gui.enums.EnumBackground;
import net.hydonclient.gui.main.element.impl.SettingsButton;
import net.hydonclient.gui.main.element.impl.SettingsSlider;
import net.hydonclient.gui.main.element.impl.SettingsToggle;
import net.hydonclient.gui.main.tab.SettingController;
import net.hydonclient.gui.main.tab.SettingGroup;
import net.hydonclient.gui.main.tab.SettingsDropdownElement;
import net.hydonclient.integrations.discord.DiscordPresence;
import net.hydonclient.packages.AbstractPackage;
import net.hydonclient.packages.MinecraftPackageBootstrap;
import net.hydonclient.util.GuiUtils;
import net.hydonclient.util.maps.Images;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.MathHelper;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;

public class HydonMainGui extends GuiScreen {

    /**
     * The instance of the main gui
     */
    public static final HydonMainGui INSTANCE = new HydonMainGui();

    /**
     * The current group that's shown
     */
    public SettingGroup currentGroup = null;

    /**
     * The offset of the boxes
     */
    public int leftSideOffset, rightSideOffset = 0;

    /**
     * The controller for what elements are added to a menu
     */
    private SettingController controller = new SettingController();

    /**
     * Buttons that are ran through enums and have multiple options
     */
    public static SettingsButton outlineModeButton, currentBackgroundButton;

    /**
     * All the Hydon Settings will go here Categories are placed as the code is Never make any
     * category higher than General
     */
    public HydonMainGui() {
        SettingsDropdownElement generalElement = new SettingsDropdownElement("General");
        SettingGroup misc = new SettingGroup("Miscellaneous");
        SettingGroup toggleSprint = new SettingGroup("Togglesprint");

        currentBackgroundButton = new SettingsButton(
            "Current Background: " + (Hydon.SETTINGS.getCurrentBackground().ordinal() + 1),
            EnumBackground::cycleBackground);
        misc.addElements(currentBackgroundButton);
        misc.addElements(
            new SettingsToggle("Fast Chat", Hydon.SETTINGS.FAST_CHAT,
                result -> Hydon.SETTINGS.FAST_CHAT = result));
        misc.addElements(
            new SettingsToggle("GUI Blur", Hydon.SETTINGS.GUI_BLUR,
                result -> Hydon.SETTINGS.GUI_BLUR = result));
        misc.addElements(
            new SettingsToggle("Discord Rich Presence", Hydon.SETTINGS.DISCORD_RICH_PRESENCE,
                result -> {
                    Hydon.SETTINGS.DISCORD_RICH_PRESENCE = result;
                    if (result) {
                        DiscordPresence.getInstance().load();
                    } else {
                        DiscordPresence.getInstance().shutdown();
                    }
                }));
        misc.addElements(
            new SettingsToggle("Replace Font (WIP)", Hydon.SETTINGS.REPLACE_DEFAULT_FONT,
                result -> Hydon.SETTINGS.REPLACE_DEFAULT_FONT = result));
        misc.addElements(
            new SettingsToggle("Replace Buttons", Hydon.SETTINGS.HYDON_BUTTONS,
                result -> Hydon.SETTINGS.HYDON_BUTTONS = result));

        toggleSprint.addElements(
            new SettingsToggle("Togglesprint", Hydon.SETTINGS.TOGGLESPRINT,
                result -> Hydon.SETTINGS.TOGGLESPRINT = result));
        toggleSprint.addElements(
            new SettingsToggle("Stop after released", Hydon.SETTINGS.STOP_SPRINTING_WHEN_RELEASED,
                result -> Hydon.SETTINGS.STOP_SPRINTING_WHEN_RELEASED = result));

        generalElement.addElements(misc, toggleSprint);

        controller.addElements(generalElement);

        /* Old Animations Configuration */
        SettingsDropdownElement oldAnimationsElement = new SettingsDropdownElement("Animations");

        SettingGroup animationElements = new SettingGroup("Animation Items");

        animationElements.addElements(
            new SettingsToggle("1.7 Armor", Hydon.SETTINGS.OLD_ARMOR,
                result -> Hydon.SETTINGS.OLD_ARMOR = result));
        animationElements.addElements(
            new SettingsToggle("1.7 Blocking", Hydon.SETTINGS.OLD_BLOCKING,
                result -> Hydon.SETTINGS.OLD_BLOCKING = result));
        animationElements.addElements(
                new SettingsToggle("1.7 Block Hitting", Hydon.SETTINGS.OLD_BLOCK_HITTING,
                        result -> Hydon.SETTINGS.OLD_BLOCK_HITTING = result));
        animationElements.addElements(
                new SettingsToggle("1.7 Bow", Hydon.SETTINGS.OLD_BOW,
                        result -> Hydon.SETTINGS.OLD_BOW = result));
        animationElements.addElements(
                new SettingsToggle("1.7 Rod", Hydon.SETTINGS.OLD_ROD,
                        result -> Hydon.SETTINGS.OLD_ROD = result));
        animationElements.addElements(
                new SettingsToggle("1.7 Eating", Hydon.SETTINGS.OLD_EATING,
                        result -> Hydon.SETTINGS.OLD_EATING = result));
        animationElements.addElements(
            new SettingsToggle("1.7 Damage", Hydon.SETTINGS.OLD_DAMAGE_FLASH,
                result -> Hydon.SETTINGS.OLD_DAMAGE_FLASH = result));
        animationElements.addElements(
            new SettingsToggle("1.7 Item Holding", Hydon.SETTINGS.OLD_ITEM_HOLDING,
                result -> Hydon.SETTINGS.OLD_ITEM_HOLDING = result));
        animationElements.addElements(
            new SettingsToggle("1.7 Sneaking", Hydon.SETTINGS.OLD_SNEAKING,
                result -> Hydon.SETTINGS.OLD_SNEAKING = result));

        SettingGroup hudElements = new SettingGroup("HUD Items");

        hudElements.addElements(
            new SettingsToggle("1.7 Debug", Hydon.SETTINGS.OLD_DEBUG_MENU,
                result -> Hydon.SETTINGS.OLD_DEBUG_MENU = result));

        oldAnimationsElement.addElements(animationElements, hudElements);

        controller.addElements(oldAnimationsElement);

        SettingsDropdownElement cosmeticElement = new SettingsDropdownElement("Cosmetics");
        SettingGroup wings = new SettingGroup("Wings");

        wings.addElements(
            new SettingsToggle("Wings", Hydon.SETTINGS.WINGS,
                result -> Hydon.SETTINGS.WINGS = result));
        wings.addElements(
            new SettingsSlider("Scale: ", "",
                60, 200, Hydon.SETTINGS.WINGS_SCALE, false,
                value -> Hydon.SETTINGS.WINGS_SCALE = (int) value));

        cosmeticElement.addElements(wings);

        controller.addElements(cosmeticElement);


        /* Improvements Configuration */
        SettingsDropdownElement improvements = new SettingsDropdownElement("Improvements");
        SettingGroup framerateImprovements = new SettingGroup("Framerate");
        SettingGroup generalImprovements = new SettingGroup("General");

        /*
         * Framerate Improvements
         * Anything that would generally improve framerate should go here
         */
        framerateImprovements.addElements(
            new SettingsToggle("Hide Armorstands", Hydon.SETTINGS.DISABLE_ARMORSTANDS,
                result -> Hydon.SETTINGS.DISABLE_ARMORSTANDS = result));
        framerateImprovements.addElements(
            new SettingsToggle("Hide Signs", Hydon.SETTINGS.DISABLE_SIGNS,
                result -> Hydon.SETTINGS.DISABLE_SIGNS = result));
        framerateImprovements.addElements(
            new SettingsToggle("Hide Item Frames", Hydon.SETTINGS.DISABLE_ITEMFRAMES,
                result -> Hydon.SETTINGS.DISABLE_ITEMFRAMES = result));
        framerateImprovements.addElements(
            new SettingsToggle("Hide Experience Orbs", Hydon.SETTINGS.DISABLE_XPORBS,
                result -> Hydon.SETTINGS.DISABLE_XPORBS = result));
        framerateImprovements.addElements(
            new SettingsToggle("Hide All Particles", Hydon.SETTINGS.DISABLE_ALL_PARTICLES,
                result -> Hydon.SETTINGS.DISABLE_ALL_PARTICLES = result));
        framerateImprovements.addElements(
            new SettingsToggle("Hide Thrown Projectiles", Hydon.SETTINGS.DISABLE_THROWN_PROJECTILES,
                result -> Hydon.SETTINGS.DISABLE_THROWN_PROJECTILES = result));

        /*
         * General Improvements
         * Anything that would aid someone in ease of access should go here
         */
        generalImprovements.addElements(
            new SettingsToggle("Windowed Fullscreen", Hydon.SETTINGS.WINDOWED_FULLSCREEN,
                result -> Hydon.SETTINGS.WINDOWED_FULLSCREEN = result));
        generalImprovements.addElements(
            new SettingsToggle("Disable Titles", Hydon.SETTINGS.DISABLE_TITLES,
                result -> Hydon.SETTINGS.DISABLE_TITLES = result));
        generalImprovements.addElements(
            new SettingsToggle("Disable Boss Footer", Hydon.SETTINGS.DISABLE_BOSS_FOOTER,
                result -> Hydon.SETTINGS.DISABLE_BOSS_FOOTER = result));
        generalImprovements.addElements(
            new SettingsToggle("Disable Boss Bar", Hydon.SETTINGS.DISABLE_BOSS_BAR,
                result -> Hydon.SETTINGS.DISABLE_BOSS_BAR = result));
        generalImprovements.addElements(
            new SettingsToggle("Disable Scoreboard", Hydon.SETTINGS.DISABLE_SCOREBOARD,
                result -> Hydon.SETTINGS.DISABLE_SCOREBOARD = result));
        generalImprovements.addElements(
            new SettingsToggle("Disable Enchantments", Hydon.SETTINGS.DISABLE_ENCHANTMENTS,
                result -> Hydon.SETTINGS.DISABLE_ENCHANTMENTS = result));
        generalImprovements.addElements(
            new SettingsToggle("Fullbright", Hydon.SETTINGS.FULLBRIGHT,
                result -> Hydon.SETTINGS.FULLBRIGHT = result));
        generalImprovements.addElements(
            new SettingsToggle("Numbered Ping", Hydon.SETTINGS.NUMBER_PING,
                result -> Hydon.SETTINGS.NUMBER_PING = result));
        generalImprovements.addElements(
            new SettingsToggle("Framerate Limiter", Hydon.SETTINGS.LIMIT_FRAMERATE,
                result -> Hydon.SETTINGS.LIMIT_FRAMERATE = result));
//        generalImprovements.addElements(
//                new SettingsToggle("Chat Locker", ChatLocker.CHATLOCKER,
//                        result -> ChatLocker.CHATLOCKER = result));

        improvements.addElements(framerateImprovements, generalImprovements);
        controller.addElements(improvements);


        /* Vanilla Enhancements Configuration */
        SettingsDropdownElement veElement = new SettingsDropdownElement("Vanilla Enhancements");

        /*
         * Hotbar Elements
         * Anything that would show up on the Hotbar should show up here
         */
        SettingGroup hotBarElements = new SettingGroup("Hotbar Elements");

        hotBarElements.addElements(
            new SettingsToggle("Amplifier Preview", Hydon.SETTINGS.AMPLIFIER_PREVIEW,
                result -> Hydon.SETTINGS.AMPLIFIER_PREVIEW = result));
        hotBarElements.addElements(
            new SettingsToggle("Arrow Counter", Hydon.SETTINGS.ARROW_COUNTER,
                result -> Hydon.SETTINGS.ARROW_COUNTER = result));
        hotBarElements.addElements(
            new SettingsToggle("Damage Preview", Hydon.SETTINGS.DAMAGE_PREVIEW,
                result -> Hydon.SETTINGS.DAMAGE_PREVIEW = result));
        hotBarElements.addElements(
            new SettingsToggle("Hotbar Numbers", Hydon.SETTINGS.HOTBAR_NUMBERS,
                result -> Hydon.SETTINGS.HOTBAR_NUMBERS = result));
        hotBarElements.addElements(
            new SettingsToggle("Number Shadow", Hydon.SETTINGS.HOTBAR_NUMBER_SHADOW,
                result -> Hydon.SETTINGS.HOTBAR_NUMBER_SHADOW = result));

        /*
         * Inventory Elements
         * Anything that would show up in the inventory should show up here
         */
        SettingGroup inventoryElements = new SettingGroup("Inventory Elements");
        inventoryElements.addElements(
            new SettingsToggle("Protection Potential", Hydon.SETTINGS.PROTECTION_PREVIEW,
                result -> Hydon.SETTINGS.PROTECTION_PREVIEW = result));
        inventoryElements.addElements(
            new SettingsToggle("Proj. Protection Potential",
                Hydon.SETTINGS.PROJECTILE_PROT_PREVIEW,
                result -> Hydon.SETTINGS.PROJECTILE_PROT_PREVIEW = result));

        /*
         * Other Elements
         * Anything that shows up outside of the inventory and isn't on the Hotbar should show up here
         */
        SettingGroup miscElements = new SettingGroup("Other Elements");
        miscElements.addElements(
            new SettingsToggle("Compact Chat", Hydon.SETTINGS.COMPACT_CHAT,
                result -> Hydon.SETTINGS.COMPACT_CHAT = result));
        miscElements.addElements(
            new SettingsToggle("Confirm Disconnect", Hydon.SETTINGS.CONFIRM_DISCONNECT,
                result -> Hydon.SETTINGS.CONFIRM_DISCONNECT = result));
        miscElements.addElements(
            new SettingsToggle("Confirm Quit Game", Hydon.SETTINGS.CONFIRM_QUIT,
                result -> Hydon.SETTINGS.CONFIRM_QUIT = result));
        miscElements.addElements(
            new SettingsToggle("Third Person Crosshair", Hydon.SETTINGS.THIRD_PERSON_CROSSHAIR,
                result -> Hydon.SETTINGS.THIRD_PERSON_CROSSHAIR = result));

        veElement.addElements(hotBarElements, inventoryElements, miscElements);
        controller.addElements(veElement);

        SettingsDropdownElement modElement = new SettingsDropdownElement("Mods");

        /*
         * Start the Mods section here
         */


        /*
         * BlockOverlay Mod
         */
        SettingGroup blockOverlayElements = new SettingGroup("Block Overlay");
        outlineModeButton = new SettingsButton(
            "Outline Mode: " + Hydon.SETTINGS.getBoMode().getName(),
            BlockOverlayMode::cycleNextMode);
        blockOverlayElements.addElements(
            outlineModeButton);
        blockOverlayElements.addElements(
            new SettingsToggle("Persistent", Hydon.SETTINGS.BLOCKOVERLAY_PERSISTENT,
                result -> Hydon.SETTINGS.BLOCKOVERLAY_PERSISTENT = result));
        blockOverlayElements.addElements(
            new SettingsToggle("Ignore Depth", Hydon.SETTINGS.BLOCKOVERLAY_IGNORE_DEPTH,
                result -> Hydon.SETTINGS.BLOCKOVERLAY_IGNORE_DEPTH = result));
        blockOverlayElements.addElements(
            new SettingsToggle("Chroma", Hydon.SETTINGS.BLOCKOVERLAY_CHROMA,
                result -> Hydon.SETTINGS.BLOCKOVERLAY_CHROMA = result));

        blockOverlayElements.addElements(
            new SettingsSlider("Line Width: ", "",
                0, 5, Hydon.SETTINGS.BLOCKOVERLAY_LINE_WIDTH, false,
                value -> Hydon.SETTINGS.BLOCKOVERLAY_LINE_WIDTH = value));
        blockOverlayElements.addElements(
            new SettingsSlider("Red: ", "",
                0, 255, Hydon.SETTINGS.BLOCKOVERLAY_RED, false,
                value -> Hydon.SETTINGS.BLOCKOVERLAY_RED = (int) value));
        blockOverlayElements.addElements(
            new SettingsSlider("Green: ", "",
                0, 255, Hydon.SETTINGS.BLOCKOVERLAY_GREEN, false,
                value -> Hydon.SETTINGS.BLOCKOVERLAY_GREEN = (int) value));
        blockOverlayElements.addElements(
            new SettingsSlider("Blue: ", "",
                0, 255, Hydon.SETTINGS.BLOCKOVERLAY_BLUE, false,
                value -> Hydon.SETTINGS.BLOCKOVERLAY_BLUE = (int) value));
        blockOverlayElements.addElements(
            new SettingsSlider("Alpha: ", "",
                0, 255, Hydon.SETTINGS.BLOCKOVERLAY_ALPHA, false,
                value -> Hydon.SETTINGS.BLOCKOVERLAY_ALPHA = (int) value));
        blockOverlayElements.addElements(
            new SettingsSlider("Chroma Speed: ", "",
                0, 5, Hydon.SETTINGS.BLOCKOVERLAY_CHROMA_SPEED, false,
                value -> Hydon.SETTINGS.BLOCKOVERLAY_CHROMA_SPEED = (int) value));


        /*
         * Keystrokes Mod
         */
        SettingGroup keyStrokesElements = new SettingGroup("Key Strokes");
        keyStrokesElements.addElements(
            new SettingsToggle("Enabled", Hydon.SETTINGS.ENABLE_KEYSTROKES,
                result -> Hydon.SETTINGS.ENABLE_KEYSTROKES = result));
        keyStrokesElements.addElements(
            new SettingsToggle("Chroma", Hydon.SETTINGS.KEYSTROKES_CHROMA,
                result -> Hydon.SETTINGS.KEYSTROKES_CHROMA = result));
        keyStrokesElements.addElements(
            new SettingsToggle("Outline", Hydon.SETTINGS.KEYSTROKES_OUTLINE,
                result -> Hydon.SETTINGS.KEYSTROKES_OUTLINE = result));


        /*
         * Perspective Mod
         */
        SettingGroup perspectiveElements = new SettingGroup("Perspective");
        perspectiveElements.addElements(
            new SettingsToggle("Held Keybind", Hydon.SETTINGS.HELD_PERSPECTIVE,
                result -> Hydon.SETTINGS.HELD_PERSPECTIVE = result));

        modElement.addElements(blockOverlayElements, keyStrokesElements,
            perspectiveElements);
        controller.addElements(modElement);
    }

    public void loadPackageElements() {
        SettingsDropdownElement packageElements = new SettingsDropdownElement("Packages");

        for (AbstractPackage loadedPackage : MinecraftPackageBootstrap.getLoadedPackages()) {
            loadedPackage.loadSettingsElements(packageElements);
        }

        controller.addElements(packageElements);
    }

    /**
     * Initialize the Hydon GUI
     */
    @Override
    public void initGui() {
        ScaledResolution scaledResolution = new ScaledResolution(Minecraft.getMinecraft());

        this.buttonList.add(new GuiButton(1, scaledResolution.getScaledWidth() / 2 - 40,
            scaledResolution.getScaledHeight() - 20, 80, 20, "Back"));
    }

    /**
     * Draw everything to the screen
     *
     * @param mouseX the current mouse x location
     * @param mouseY the current mouse y location
     * @param partialTicks the world tick
     */
    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        drawDefaultBackground();

        ScaledResolution scaledResolution = new ScaledResolution(Minecraft.getMinecraft());
        int padding = Minecraft.getMinecraft().displayHeight / 8;

        GuiUtils.drawBG();

        GL11.glScissor(padding - 10, padding, Minecraft.getMinecraft().displayWidth + 20 - padding * 2, Minecraft.getMinecraft().displayHeight - padding * 2);
        GL11.glEnable(GL11.GL_SCISSOR_TEST);

        controller.draw();
        if (currentGroup != null) {
            currentGroup.draw();
        }

        GL11.glDisable(GL11.GL_SCISSOR_TEST);

        int scaledPadding = scaledResolution.getScaledHeight() / 8;
        int width = scaledResolution.getScaledWidth() - scaledPadding;
        float logoFactor = 2.5f;
        int startHeight = (int) (scaledPadding + Images.LOGO_V2_DOWNSCALED.getHeight() / logoFactor);

        Gui.drawRect(scaledPadding - 1, scaledPadding - 1, width + 1, startHeight + 1, new Color(10, 10, 10).getRGB());
        Gui.drawRect(1 + scaledPadding, scaledPadding, width, startHeight, new Color(5, 5, 5).getRGB());

        GlStateManager.enableBlend();
        Images.LOGO_V2_DOWNSCALED.bind();
        GlStateManager.color(1, 1, 1, 1);
        Gui.drawModalRectWithCustomSizedTexture(scaledPadding, scaledPadding, 0, 0,
            (int) (Images.LOGO_V2_DOWNSCALED.getWidth() / logoFactor), (int) (Images.LOGO_V2_DOWNSCALED.getHeight() / logoFactor),
            Images.LOGO_V2_DOWNSCALED.getWidth() / logoFactor, Images.LOGO_V2_DOWNSCALED.getHeight() / logoFactor);

        super.drawScreen(mouseX, mouseY, partialTicks);
    }

    /**
     * When a menu is clicked, reveal the contents inside of them
     *
     * @param mouseX the current mouse x location
     * @param mouseY the current mouse y location
     * @param mouseButton the button being clicked
     * @throws IOException the exception that's thrown if something goes wrong
     */
    @Override
    protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
        controller.mouseClicked(mouseButton, mouseX, mouseY);
        super.mouseClicked(mouseX, mouseY, mouseButton);
    }

    /**
     * When the mouse button being held is released, reveal the contents inside of the clicked menu
     *
     * @param mouseX the current mouse x location
     * @param mouseY the current mouse y location
     * @param state the state of the mouse button being clicked
     */
    @Override
    protected void mouseReleased(int mouseX, int mouseY, int state) {
        if (currentGroup != null) {
            currentGroup.mouseReleased(mouseX, mouseY, state);
        }
        super.mouseReleased(mouseX, mouseY, state);
    }

    /**
     * When the mouse button being held is dragged, reveal the contents inside of the clicked menu
     *
     * @param mouseX the current mouse x location
     * @param mouseY the current mouse y location
     * @param clickedMouseButton the mouse button being clicked
     * @param timeSinceLastClick the time since the mouse button was last clicked
     */
    @Override
    protected void mouseClickMove(int mouseX, int mouseY, int clickedMouseButton,
        long timeSinceLastClick) {
        if (currentGroup != null) {
            currentGroup.mouseDragged(mouseX, mouseY, clickedMouseButton, timeSinceLastClick);
        }
        super.mouseClickMove(mouseX, mouseY, clickedMouseButton, timeSinceLastClick);
    }

    /**
     * Handles the input from the mouse for using the scroll wheel so the user can scroll up and
     * down
     *
     * @throws IOException the exception that's thrown if something goes wrong
     */
    @Override
    public void handleMouseInput() throws IOException {
        ScaledResolution scaledResolution = new ScaledResolution(Minecraft.getMinecraft());
        int mouseOffset = MathHelper.clamp_int(Mouse.getEventDWheel(), -1, 1);

        int padding = scaledResolution.getScaledHeight() / 8;
        int height = scaledResolution.getScaledHeight() - padding;
        int sidebarWidth = scaledResolution.getScaledWidth() / 4;

        if (GuiUtils.isHovered(padding, padding, sidebarWidth, height - padding)) {
            leftSideOffset += mouseOffset * 10;
//            leftSideOffset = Math.max(0, leftSideOffset);
        } else if (GuiUtils.isHovered(sidebarWidth, padding, scaledResolution.getScaledWidth() - sidebarWidth - padding, height - padding)) {
            rightSideOffset += mouseOffset * 10;
//            rightSideOffset = Math.max(0, rightSideOffset);
        }
        super.handleMouseInput();
    }

    /**
     * If the player presses escape, exit the menu
     *
     * @param typedChar the pressed character
     * @param keyCode the key number of the pressed character
     */
    @Override
    protected void keyTyped(char typedChar, int keyCode) {
        if (keyCode == Keyboard.KEY_ESCAPE) {
            Minecraft.getMinecraft().displayGuiScreen(null);
        }
    }

    /**
     * When the player clicks a button, what will it do
     *
     * @param button the button being clicked
     * @throws IOException the exception that's thrown if something goes wrong
     */
    @Override
    protected void actionPerformed(GuiButton button) throws IOException {
        if (button.id == 1) {
            Minecraft.getMinecraft().displayGuiScreen(null);
        }
        super.actionPerformed(button);
    }

    public SettingController getController() {
        return controller;
    }
}