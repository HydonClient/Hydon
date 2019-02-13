package net.hydonclient.gui.main;

import java.awt.*;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.Collection;

import me.aycy.blockoverlay.utils.BlockOverlayMode;
import net.hydonclient.Hydon;
import net.hydonclient.gui.enums.EnumBackground;
import net.hydonclient.gui.main.element.impl.SettingsButton;
import net.hydonclient.gui.main.element.impl.SettingsNote;
import net.hydonclient.gui.main.element.impl.SettingsSlider;
import net.hydonclient.gui.main.element.impl.SettingsToggle;
import net.hydonclient.gui.main.tab.SettingController;
import net.hydonclient.gui.main.tab.SettingGroup;
import net.hydonclient.gui.main.tab.SettingsDropdownElement;
import net.hydonclient.integrations.discord.DiscordPresence;
import net.hydonclient.managers.HydonManagers;
import net.hydonclient.mods.chatlocker.ChatLocker;
import net.hydonclient.mods.hydonhud.HydonHUD;
import net.hydonclient.mods.hydonhud.modules.display.MoveCoordsElement;
import net.hydonclient.mods.hydonhud.modules.display.MoveFPSElement;
import net.hydonclient.mods.hydonhud.modules.display.MovePotionStatusElement;
import net.hydonclient.mods.hydonhud.modules.display.MoveSprintingElement;
import net.hydonclient.packages.AbstractPackage;
import net.hydonclient.packages.PackageBootstrap;
import net.hydonclient.util.GuiUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.MathHelper;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;

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
     * The keystrokes elements
     */
    private SettingGroup keyStrokesElements;

    /**
     * The Hydon HUD elements
     */
    private SettingGroup coordsElements, fpsElements, sprintElements, potionDisplayElements;

    /**
     * The addons dropdown
     */
    private SettingsDropdownElement packageElements;

    /**
     * Buttons that are ran through enums and have multiple options
     */
    public static SettingsButton outlineModeButton, currentBackgroundButton, seperatorButton;

    /**
     * The Hydon hud to make things easy
     */
    private HydonHUD hud = HydonManagers.INSTANCE.getModManager().getHydonHUD();

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
                new SettingsToggle("Fast Chat", Hydon.SETTINGS.fastChat,
                        result -> Hydon.SETTINGS.fastChat = result));
        misc.addElements(
                new SettingsToggle("GUI Blur", Hydon.SETTINGS.blurEnabled,
                        result -> Hydon.SETTINGS.blurEnabled = result));
        misc.addElements(
                new SettingsToggle("Discord Rich Presence", Hydon.SETTINGS.discordRichPresence,
                        result -> {
                            Hydon.SETTINGS.discordRichPresence = result;
                            if (result) {
                                DiscordPresence.getInstance().load();
                            } else {
                                DiscordPresence.getInstance().shutdown();
                            }
                        }));
        misc.addElements(
                new SettingsToggle("Replace Font (WIP)", Hydon.SETTINGS.replaceDefaultFont,
                        result -> Hydon.SETTINGS.replaceDefaultFont = result));
        misc.addElements(
                new SettingsToggle("Replace Buttons", Hydon.SETTINGS.hydonButtons,
                        result -> Hydon.SETTINGS.hydonButtons = result));

        toggleSprint.addElements(
                new SettingsToggle("Togglesprint", Hydon.SETTINGS.togglesprintEnabled,
                        result -> Hydon.SETTINGS.togglesprintEnabled = result));
        toggleSprint.addElements(
                new SettingsToggle("Stop after released", Hydon.SETTINGS.stopSprintingAfterReleased,
                        result -> Hydon.SETTINGS.stopSprintingAfterReleased = result));

        generalElement.addElements(misc, toggleSprint);

        controller.addElements(generalElement);

        /* Old Animations Configuration */
        SettingsDropdownElement oldAnimationsElement = new SettingsDropdownElement("Animations");

        SettingGroup animationElements = new SettingGroup("Animation Items");

        animationElements.addElements(
                new SettingsToggle("1.7 Armor", Hydon.SETTINGS.oldArmor,
                        result -> Hydon.SETTINGS.oldArmor = result));
        animationElements.addElements(
                new SettingsToggle("1.7 Blocking", Hydon.SETTINGS.oldBlocking,
                        result -> Hydon.SETTINGS.oldBlocking = result));
        animationElements.addElements(
                new SettingsToggle("1.7 Damage", Hydon.SETTINGS.oldDamageFlash,
                        result -> Hydon.SETTINGS.oldDamageFlash = result));
        animationElements.addElements(
                new SettingsToggle("1.7 Item Holding", Hydon.SETTINGS.oldItemHolding,
                        result -> Hydon.SETTINGS.oldItemHolding = result));
        animationElements.addElements(
                new SettingsToggle("1.7 Sneaking", Hydon.SETTINGS.oldSneaking,
                        result -> Hydon.SETTINGS.oldSneaking = result));

        SettingGroup hudElements = new SettingGroup("HUD Items");

        hudElements.addElements(
                new SettingsToggle("1.7 Debug", Hydon.SETTINGS.oldDebugMenu,
                        result -> Hydon.SETTINGS.oldDebugMenu = result));

        oldAnimationsElement.addElements(animationElements, hudElements);

        controller.addElements(oldAnimationsElement);

        SettingsDropdownElement cosmeticElement = new SettingsDropdownElement("Cosmetics");
        SettingGroup wings = new SettingGroup("Wings");

        wings.addElements(
                new SettingsToggle("Wings", Hydon.SETTINGS.wingsEnabled,
                        result -> Hydon.SETTINGS.wingsEnabled = result));
        wings.addElements(
                new SettingsSlider("Scale: ", "",
                        60, 200, Hydon.SETTINGS.wingsScale, false,
                        value -> Hydon.SETTINGS.wingsScale = (int) value));

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
                new SettingsToggle("Hide Armorstands", Hydon.SETTINGS.disableArmorstands,
                        result -> Hydon.SETTINGS.disableArmorstands = result));
        framerateImprovements.addElements(
                new SettingsToggle("Hide Signs", Hydon.SETTINGS.disableSigns,
                        result -> Hydon.SETTINGS.disableSigns = result));
        framerateImprovements.addElements(
                new SettingsToggle("Hide Item Frames", Hydon.SETTINGS.disableItemFrames,
                        result -> Hydon.SETTINGS.disableItemFrames = result));
        framerateImprovements.addElements(
                new SettingsToggle("Hide Experience Orbs", Hydon.SETTINGS.disableXPOrbs,
                        result -> Hydon.SETTINGS.disableXPOrbs = result));
        framerateImprovements.addElements(
                new SettingsToggle("Hide All Particles", Hydon.SETTINGS.disableAllParticles,
                        result -> Hydon.SETTINGS.disableAllParticles = result));
        framerateImprovements.addElements(
                new SettingsToggle("Hide Thrown Projectiles", Hydon.SETTINGS.disableThrownProjectiles,
                        result -> Hydon.SETTINGS.disableThrownProjectiles = result));

        /*
         * General Improvements
         * Anything that would aid someone in ease of access should go here
         */
        generalImprovements.addElements(
                new SettingsToggle("Windowed Fullscreen", Hydon.SETTINGS.windowedFullscreen,
                        result -> Hydon.SETTINGS.windowedFullscreen = result));
        generalImprovements.addElements(
                new SettingsToggle("Disable Titles", Hydon.SETTINGS.disableTitles,
                        result -> Hydon.SETTINGS.disableTitles = result));
        generalImprovements.addElements(
                new SettingsToggle("Disable Boss Footer", Hydon.SETTINGS.disableBossFooter,
                        result -> Hydon.SETTINGS.disableBossFooter = result));
        generalImprovements.addElements(
                new SettingsToggle("Disable Boss Bar", Hydon.SETTINGS.disableBossBar,
                        result -> Hydon.SETTINGS.disableBossBar = result));
        generalImprovements.addElements(
                new SettingsToggle("Disable Scoreboard", Hydon.SETTINGS.disableScoreboard,
                        result -> Hydon.SETTINGS.disableScoreboard = result));
        generalImprovements.addElements(
                new SettingsToggle("Disable Enchantments", Hydon.SETTINGS.disableEnchantments,
                        result -> Hydon.SETTINGS.disableEnchantments = result));
        generalImprovements.addElements(
                new SettingsToggle("Fullbright", Hydon.SETTINGS.fullbright,
                        result -> Hydon.SETTINGS.fullbright = result));
        generalImprovements.addElements(
                new SettingsToggle("Numbered Ping", Hydon.SETTINGS.numberPing,
                        result -> Hydon.SETTINGS.numberPing = result));
        generalImprovements.addElements(
                new SettingsToggle("Framerate Limiter", Hydon.SETTINGS.limitFramerate,
                        result -> Hydon.SETTINGS.limitFramerate = result));
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
                new SettingsToggle("Amplifier Preview", Hydon.SETTINGS.ampPreview,
                        result -> Hydon.SETTINGS.ampPreview = result));
        hotBarElements.addElements(
                new SettingsToggle("Arrow Counter", Hydon.SETTINGS.arrowCounter,
                        result -> Hydon.SETTINGS.arrowCounter = result));
        hotBarElements.addElements(
                new SettingsToggle("Damage Preview", Hydon.SETTINGS.damagePreview,
                        result -> Hydon.SETTINGS.damagePreview = result));
        hotBarElements.addElements(
                new SettingsToggle("Hotbar Numbers", Hydon.SETTINGS.hotbarNumbers,
                        result -> Hydon.SETTINGS.hotbarNumbers = result));
        hotBarElements.addElements(
                new SettingsToggle("Number Shadow", Hydon.SETTINGS.hotbarNumberShadow,
                        result -> Hydon.SETTINGS.hotbarNumberShadow = result));

        /*
         * Inventory Elements
         * Anything that would show up in the inventory should show up here
         */
        SettingGroup inventoryElements = new SettingGroup("Inventory Elements");
        inventoryElements.addElements(
                new SettingsToggle("Protection Potential", Hydon.SETTINGS.protPotential,
                        result -> Hydon.SETTINGS.protPotential = result));
        inventoryElements.addElements(
                new SettingsToggle("Proj. Protection Potential",
                        Hydon.SETTINGS.projPotential,
                        result -> Hydon.SETTINGS.protPotential = result));

        /*
         * Other Elements
         * Anything that shows up outside of the inventory and isn't on the Hotbar should show up here
         */
        SettingGroup miscElements = new SettingGroup("Other Elements");
        miscElements.addElements(
                new SettingsToggle("Compact Chat", Hydon.SETTINGS.compactChat,
                        result -> Hydon.SETTINGS.compactChat = result));
        miscElements.addElements(
                new SettingsToggle("Confirm Disconnect", Hydon.SETTINGS.confirmDisconnect,
                        result -> Hydon.SETTINGS.confirmDisconnect = result));
        miscElements.addElements(
                new SettingsToggle("Confirm Quit Game", Hydon.SETTINGS.confirmQuitGame,
                        result -> Hydon.SETTINGS.confirmQuitGame = result));
        miscElements.addElements(
                new SettingsToggle("Third Person Crosshair", Hydon.SETTINGS.thirdPersonCrosshair,
                        result -> Hydon.SETTINGS.thirdPersonCrosshair = result));

        veElement.addElements(hotBarElements, inventoryElements, miscElements);
        controller.addElements(veElement);

        SettingsDropdownElement modElement = new SettingsDropdownElement("Mods");

        /*
         * Start the Mods section here
         */

        /*
         * AutoGG Mod
         */
        SettingGroup autoGG = new SettingGroup("AutoGG");

        autoGG.addElements(
                new SettingsToggle("AutoGG", Hydon.SETTINGS.autoGGEnabled,
                        result -> Hydon.SETTINGS.autoGGEnabled = result));
        autoGG.addElements(
                new SettingsSlider("Delay: ", "",
                        0, 10, Hydon.SETTINGS.autoGGDelay, false,
                        value -> Hydon.SETTINGS.autoGGDelay = (int) value));


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
                new SettingsToggle("Persistent", Hydon.SETTINGS.boPersistent,
                        result -> Hydon.SETTINGS.boPersistent = result));
        blockOverlayElements.addElements(
                new SettingsToggle("Ignore Depth", Hydon.SETTINGS.boIgnoreDepth,
                        result -> Hydon.SETTINGS.boIgnoreDepth = result));
        blockOverlayElements.addElements(
                new SettingsToggle("Chroma", Hydon.SETTINGS.boChroma,
                        result -> Hydon.SETTINGS.boChroma = result));

        blockOverlayElements.addElements(
                new SettingsSlider("Line Width: ", "",
                        0, 5, Hydon.SETTINGS.boLineWidth, false,
                        value -> Hydon.SETTINGS.boLineWidth = value));
        blockOverlayElements.addElements(
                new SettingsSlider("Red: ", "",
                        0, 255, Hydon.SETTINGS.boRed, false,
                        value -> Hydon.SETTINGS.boRed = (int) value));
        blockOverlayElements.addElements(
                new SettingsSlider("Green: ", "",
                        0, 255, Hydon.SETTINGS.boGreen, false,
                        value -> Hydon.SETTINGS.boGreen = (int) value));
        blockOverlayElements.addElements(
                new SettingsSlider("Blue: ", "",
                        0, 255, Hydon.SETTINGS.boBlue, false,
                        value -> Hydon.SETTINGS.boBlue = (int) value));
        blockOverlayElements.addElements(
                new SettingsSlider("Alpha: ", "",
                        0, 255, Hydon.SETTINGS.boAlpha, false,
                        value -> Hydon.SETTINGS.boAlpha = (int) value));
        blockOverlayElements.addElements(
                new SettingsSlider("Chroma Speed: ", "",
                        0, 5, Hydon.SETTINGS.boChromaSpeed, false,
                        value -> Hydon.SETTINGS.boChromaSpeed = (int) value));


        /*
         * Keystrokes Mod
         */
        keyStrokesElements = new SettingGroup("Key Strokes");
        keyStrokesElements.addElements(
                new SettingsToggle("Enabled", Hydon.SETTINGS.enableKeystrokes,
                        result -> Hydon.SETTINGS.enableKeystrokes = result));
        keyStrokesElements.addElements(
                new SettingsToggle("Chroma", Hydon.SETTINGS.keyStrokesChroma,
                        result -> Hydon.SETTINGS.keyStrokesChroma = result));
        keyStrokesElements.addElements(
                new SettingsToggle("Outline", Hydon.SETTINGS.keyStrokesOutline,
                        result -> Hydon.SETTINGS.keyStrokesOutline = result));


        /*
         * Perspective Mod
         */
        SettingGroup perspectiveElements = new SettingGroup("Perspective");
        perspectiveElements.addElements(
                new SettingsToggle("Held Keybind", Hydon.SETTINGS.heldPerspective,
                        result -> Hydon.SETTINGS.heldPerspective = result));


        /*
         * Item Physics Mod
         */
        SettingGroup itemPhysics = new SettingGroup("Item Physics");
        itemPhysics.addElements(
                new SettingsToggle("Item Physics", Hydon.SETTINGS.itemPhysics,
                        result -> Hydon.SETTINGS.itemPhysics = result));
        itemPhysics.addElements(
                new SettingsSlider("Rotation speed: ", "",
                        0, 5, Hydon.SETTINGS.rotateSpeed, false,
                        value -> Hydon.SETTINGS.rotateSpeed = (int) value));

        modElement.addElements(autoGG, blockOverlayElements, itemPhysics, keyStrokesElements, perspectiveElements);
        controller.addElements(modElement);

        /*
         * Hydon HUD
         * TODO: make it less retarded
         */
        SettingsDropdownElement hudItems = new SettingsDropdownElement("Hydon HUD");

        SettingGroup note = new SettingGroup("Note");
        note.addElements(
                new SettingsNote("This isnt ready in the slightest."));

        coordsElements = new SettingGroup("Coordinates");
        coordsElements.addElements(
                new SettingsToggle("Coordinates", hud.getConfig().COORDINATES,
                        result -> hud.getConfig().COORDINATES = result));
        coordsElements.addElements(
                new SettingsToggle("Shadow", hud.getConfig().COORDS_SHADOW,
                        result -> hud.getConfig().COORDS_SHADOW = result));
        coordsElements.addElements(
                new SettingsToggle("Show in Chat", hud.getConfig().SHOW_COORDS_IN_CHAT,
                        result -> hud.getConfig().SHOW_COORDS_IN_CHAT = result));
        coordsElements.addElements(
                new SettingsToggle("Parentheses", hud.getConfig().COORDS_PARENTHESES,
                        result -> hud.getConfig().COORDS_PARENTHESES = result));
        coordsElements.addElements(
                new SettingsButton("Position",
                        () -> Minecraft.getMinecraft().displayGuiScreen(new MoveCoordsElement(hud))));
        coordsElements.addElements(
                new SettingsSlider("Red: ", "",
                        0, 255, hud.getConfig().COORDS_RED, false,
                        value -> hud.getConfig().COORDS_RED = (int) value));
        coordsElements.addElements(
                new SettingsSlider("Green: ", "",
                        0, 255, hud.getConfig().COORDS_GREEN, false,
                        value -> hud.getConfig().COORDS_GREEN = (int) value));
        coordsElements.addElements(
                new SettingsSlider("Blue: ", "",
                        0, 255, hud.getConfig().COORDS_BLUE, false,
                        value -> hud.getConfig().COORDS_BLUE = (int) value));
        coordsElements.addElements(
                new SettingsSlider("Precision: ", "",
                        1, 5, hud.getConfig().PRECISION, false,
                        value -> hud.getConfig().PRECISION = (int) value));

        fpsElements = new SettingGroup("FPS");
        fpsElements.addElements(
                new SettingsToggle("FPS", hud.getConfig().FPS,
                        result -> hud.getConfig().FPS = result));
        fpsElements.addElements(
                new SettingsToggle("Shadow", hud.getConfig().FPS_SHADOW,
                        result -> hud.getConfig().FPS_SHADOW = result));
        fpsElements.addElements(
                new SettingsToggle("Show in Chat", hud.getConfig().SHOW_FPS_IN_CHAT,
                        result -> hud.getConfig().SHOW_FPS_IN_CHAT = result));
        fpsElements.addElements(
                new SettingsToggle("Parentheses", hud.getConfig().FPS_PARENTHESES,
                        result -> hud.getConfig().FPS_PARENTHESES = result));
        fpsElements.addElements(
                new SettingsButton("Position",
                        () -> Minecraft.getMinecraft().displayGuiScreen(new MoveFPSElement(hud))));
        fpsElements.addElements(
                new SettingsSlider("Red: ", "",
                        0, 255, hud.getConfig().FPS_RED, false,
                        value -> hud.getConfig().FPS_RED = (int) value));
        fpsElements.addElements(
                new SettingsSlider("Green: ", "",
                        0, 255, hud.getConfig().FPS_GREEN, false,
                        value -> hud.getConfig().FPS_GREEN = (int) value));
        fpsElements.addElements(
                new SettingsSlider("Blue: ", "",
                        0, 255, hud.getConfig().FPS_BLUE, false,
                        value -> hud.getConfig().FPS_BLUE = (int) value));

        sprintElements = new SettingGroup("Sprinting");
        sprintElements.addElements(
                new SettingsToggle("Sprint Status", hud.getConfig().SPRINT,
                        result -> hud.getConfig().SPRINT = result));
        sprintElements.addElements(
                new SettingsToggle("Shadow", hud.getConfig().SPRINT_SHADOW,
                        result -> hud.getConfig().SPRINT_SHADOW = result));
        sprintElements.addElements(
                new SettingsToggle("Show in Chat", hud.getConfig().SHOW_STATUS_IN_CHAT,
                        result -> hud.getConfig().SHOW_STATUS_IN_CHAT = result));
        sprintElements.addElements(
                new SettingsToggle("Parentheses", hud.getConfig().SPRINT_PARENTHESES,
                        result -> hud.getConfig().SPRINT_PARENTHESES = result));
        sprintElements.addElements(
                new SettingsSlider("Red: ", "",
                        0, 255, hud.getConfig().STATUS_RED, false,
                        value -> hud.getConfig().STATUS_RED = (int) value));
        sprintElements.addElements(
                new SettingsSlider("Green: ", "",
                        0, 255, hud.getConfig().STATUS_GREEN, false,
                        value -> hud.getConfig().STATUS_GREEN = (int) value));
        sprintElements.addElements(
                new SettingsSlider("Blue: ", "",
                        0, 255, hud.getConfig().STATUS_BLUE, false,
                        value -> hud.getConfig().STATUS_BLUE = (int) value));
        sprintElements.addElements(
                new SettingsButton("Position",
                        () -> Minecraft.getMinecraft().displayGuiScreen(new MoveSprintingElement(hud))));

        potionDisplayElements = new SettingGroup("Potion Status");
        potionDisplayElements.addElements(
                new SettingsToggle("Potion Status", hud.getConfig().POTIONSTATUS,
                        result -> hud.getConfig().POTIONSTATUS = result));
        potionDisplayElements.addElements(
                new SettingsToggle("Shadow", hud.getConfig().POTIONSTATUS_SHADOW,
                        result -> hud.getConfig().POTIONSTATUS_SHADOW = result));
        potionDisplayElements.addElements(
                new SettingsToggle("Show in Chat", hud.getConfig().SHOW_POTIONSTATUS_IN_CHAT,
                        result -> hud.getConfig().SHOW_POTIONSTATUS_IN_CHAT = result));
        potionDisplayElements.addElements(
                new SettingsToggle("Parentheses", hud.getConfig().POTIONSTATUS_PARENTHESES,
                        result -> hud.getConfig().POTIONSTATUS_PARENTHESES = result));
        /* seperatorButton = new SettingsButton(
                "Separators: " + hud.getConfig().getSeparator().getSeparatorOption(),
                EnumSeparators::cycleSeparators);
        potionDisplayElements.addElements(
                seperatorButton); */
        potionDisplayElements.addElements(
                new SettingsButton("Position",
                        () -> Minecraft.getMinecraft().displayGuiScreen(new MovePotionStatusElement(hud))));

        hudItems.addElements(note, coordsElements, fpsElements, sprintElements, potionDisplayElements);

        controller.addElements(hudItems);
    }

    public void loadPackageElements() {
        packageElements = new SettingsDropdownElement("Packages");

        for (AbstractPackage loadedPackage : PackageBootstrap.getLoadedPackages()) {
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
     * @param mouseX       the current mouse x location
     * @param mouseY       the current mouse y location
     * @param partialTicks the world tick
     */
    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        drawDefaultBackground();

        ScaledResolution scaledResolution = new ScaledResolution(Minecraft.getMinecraft());

        GuiUtils.drawBG();
        controller.draw();
        if (currentGroup != null) {
            currentGroup.draw();

            if (currentGroup == keyStrokesElements) {
                HydonManagers.INSTANCE.getModManager().getKeystrokesMod().getKeyHolder()
                        .draw(0, 0, scaledResolution.getScaledWidth() / 2 - 40,
                                scaledResolution.getScaledHeight() / 2 - 40);
            }

            if (currentGroup == fpsElements) {
                String fps;
                if (!hud.getConfig().FPS_PARENTHESES) {
                    fps = ("fps: " + Minecraft.getDebugFPS());
                } else {
                    fps = ("(fps: " + Minecraft.getDebugFPS() + ")");
                }

                hud.drawCenteredString(fps, this.width,
                        new Color(hud.getConfig().FPS_RED, hud.getConfig().FPS_GREEN, hud.getConfig().FPS_BLUE).
                                getRGB());
            }

            if (currentGroup == coordsElements) {
                EntityPlayer player = Minecraft.getMinecraft().thePlayer;
                String coords;
                if (player != null) {
                    StringBuilder expandedCoordinates = new StringBuilder("0");

                    if (hud.getConfig().PRECISION > 0) {
                        expandedCoordinates.append(".");

                        for (int i = 0; i < hud.getConfig().PRECISION; i++) {
                            expandedCoordinates.append("0");
                        }

                        DecimalFormat format = new DecimalFormat(expandedCoordinates.toString());

                        if (!hud.getConfig().COORDS_PARENTHESES) {
                            coords = ("x: " + format.format(player.posX) +
                                    ", y: " + format.format(player.posY) +
                                    ", z: " + format.format(player.posZ));
                        } else {
                            coords = ("(x: " + format.format(player.posX) +
                                    ", y: " + format.format(player.posY) +
                                    ", z: " + format.format(player.posZ) + ")");
                        }

                        hud.drawCenteredString(coords, this.width,
                                new Color(hud.getConfig().COORDS_RED, hud.getConfig().COORDS_GREEN, hud.getConfig().COORDS_BLUE).
                                        getRGB());
                    }
                }
            }

            if (currentGroup == sprintElements) {
                String status;
                if (!hud.getConfig().SPRINT_PARENTHESES) {
                    status = "sprinting";
                } else {
                    status = "(sprinting)";
                }

                hud.drawCenteredString(status, this.width,
                        new Color(hud.getConfig().STATUS_RED, hud.getConfig().STATUS_GREEN, hud.getConfig().STATUS_BLUE).
                                getRGB());
            }

            if (currentGroup == potionDisplayElements) {
                Collection<PotionEffect> effects;
                if (mc.theWorld != null) {
                    effects = hud.getMinecraft().thePlayer.getActivePotionEffects();

                    for (PotionEffect potionEffect : effects) {
                        Potion potion = Potion.potionTypes[potionEffect.getPotionID()];

                        StringBuilder effectName = new StringBuilder(I18n.format(potion.getName()));

                        if (potionEffect.getAmplifier() == 1) {
                            effectName.append(" ").append(
                                    I18n.format("enchantment.level.2"));

                        } else if (potionEffect.getAmplifier() == 2) {
                            effectName.append(" ").append(
                                    I18n.format("enchantment.level.3"));

                        } else if (potionEffect.getAmplifier() == 3) {
                            effectName.append(" ").append(
                                    I18n.format("enchantment.level.4"));
                        }

                        String duration = Potion.getDurationString(potionEffect);
                        String jointedText;

                        // TODO: fix the NPE when changing the separator (any reason why an npe is being thrown?), then replace " * " with the used separator
                        if (hud.getConfig().POTIONSTATUS) {
                            if (!hud.getConfig().POTIONSTATUS_PARENTHESES) {
                                jointedText = ("" + effectName + " * " + duration);
                            } else {
                                jointedText = ("(" + effectName + " * " + duration + ")");
                            }

                            hud.drawCenteredString(jointedText, this.width, 16777215);
                        }
                    }
                }
            }
        }

        super.drawScreen(mouseX, mouseY, partialTicks);
    }

    /**
     * When a menu is clicked, reveal the contents inside of them
     *
     * @param mouseX      the current mouse x location
     * @param mouseY      the current mouse y location
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
     * @param state  the state of the mouse button being clicked
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
     * @param mouseX             the current mouse x location
     * @param mouseY             the current mouse y location
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
     * Handles the input from the mouse for using the scroll wheel so the user can scroll up and down
     *
     * @throws IOException the exception that's thrown if something goes wrong
     */
    @Override
    public void handleMouseInput() throws IOException {
        ScaledResolution scaledResolution = new ScaledResolution(Minecraft.getMinecraft());
        int mouseOffset = MathHelper.clamp_int(Mouse.getEventDWheel(), -1, 1);
        int mouseX = (int) GuiUtils.getMouse().getX();
        if (mouseX <= 150) {
            leftSideOffset += mouseOffset * 10;
        } else if (mouseX >= scaledResolution.getScaledWidth() - 150) {
            rightSideOffset += mouseOffset * 10;
        }
        super.handleMouseInput();
    }

    /**
     * If the player presses escape, exit the menu
     *
     * @param typedChar the pressed character
     * @param keyCode   the key number of the pressed character
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