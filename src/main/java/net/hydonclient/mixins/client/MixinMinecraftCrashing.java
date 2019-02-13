package net.hydonclient.mixins.client;

import com.google.common.util.concurrent.ListenableFutureTask;
import net.hydonclient.Hydon;
import net.hydonclient.gui.misc.crash.GuiCrashScreen;
import net.hydonclient.gui.misc.crash.GuiInitErrorScreen;
import net.hydonclient.util.CrashUtils;
import net.hydonclient.util.patches.IPatchedMinecraft;
import net.hydonclient.util.patches.StateManager;
import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.SoundHandler;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiIngame;
import net.minecraft.client.gui.GuiMainMenu;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.client.network.NetHandlerPlayClient;
import net.minecraft.client.renderer.EntityRenderer;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.client.resources.IReloadableResourceManager;
import net.minecraft.client.resources.LanguageManager;
import net.minecraft.client.resources.SimpleReloadableResourceManager;
import net.minecraft.client.resources.data.IMetadataSerializer;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.crash.CrashReport;
import net.minecraft.profiler.IPlayerUsage;
import net.minecraft.server.integrated.IntegratedServer;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.IThreadListener;
import net.minecraft.util.MinecraftError;
import net.minecraft.util.ReportedException;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.LWJGLException;
import org.lwjgl.input.Keyboard;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import java.io.IOException;
import java.util.Queue;
import java.util.concurrent.Callable;
import java.util.concurrent.FutureTask;

@Mixin(Minecraft.class)
public abstract class MixinMinecraftCrashing implements IThreadListener, IPlayerUsage, IPatchedMinecraft {

    /*
     * MixinMinecraft has too many methods that wants to mess with the methods we need to use so i've moved it here
     */

    @Shadow
    volatile boolean running;

    @Shadow
    @SuppressWarnings("RedundantThrows")
    private void startGame() throws LWJGLException, IOException {
    }

    @Shadow
    public abstract CrashReport addGraphicsAndWorldToCrashReport(CrashReport theCrash);

    @Shadow
    private boolean hasCrashed;

    @Shadow
    private CrashReport crashReporter;

    @Shadow
    @SuppressWarnings("RedundantThrows")
    private void runGameLoop() throws IOException {
    }

    @Shadow
    public abstract void shutdownMinecraftApplet();

    @Shadow
    private IReloadableResourceManager mcResourceManager;
    @Shadow
    @Final
    private IMetadataSerializer metadataSerializer_;
    @Shadow
    private TextureManager renderEngine;
    @Shadow
    private LanguageManager mcLanguageManager;
    @Shadow
    public GameSettings gameSettings;

    @Shadow
    public abstract void refreshResources();

    @Shadow
    public FontRenderer fontRendererObj;
    @Shadow
    private SoundHandler mcSoundHandler;

    @Shadow
    public abstract void displayGuiScreen(GuiScreen guiScreenIn);

    @Shadow
    public GuiScreen currentScreen;

    @Shadow
    private long debugCrashKeyPressTime;
    @Shadow
    public GuiIngame ingameGUI;
    @Shadow
    public static byte[] memoryReserve;

    @Shadow
    public abstract NetHandlerPlayClient getNetHandler();

    @Shadow
    public abstract void loadWorld(WorldClient worldClientIn);

    @Shadow
    public EntityRenderer entityRenderer;
    @Shadow
    @Final
    private Queue<FutureTask<?>> scheduledTasks;

    @Shadow
    public static long getSystemTime() {
        return 0;
    }

    @Shadow
    private boolean integratedServerIsRunning;
    @Shadow
    private IntegratedServer theIntegratedServer;

    private boolean crashIntegratedServerNextTick;
    private int clientCrashCount = 0;
    private int serverCrashCount = 0;

    /**
     * @author Runemoro
     * @reason Allows the player to choose to return to the title screen after a crash, or get
     * a pasteable link to the crash report on paste.dimdev.org.
     */
    @Overwrite
    public void run() {
        running = true;

        try {
            startGame();
        } catch (Throwable t) {
            CrashReport report = CrashReport.makeCrashReport(t, "Initializing game");
            displayInitErrorScreen(addGraphicsAndWorldToCrashReport(report));
            return;
        }

        try {
            while (running) {
                if (!hasCrashed || crashReporter == null) {
                    try {
                        runGameLoop();
                    } catch (ReportedException e) {
                        clientCrashCount++;
                        addGraphicsAndWorldToCrashReport(e.getCrashReport());
                        addInfoToCrash(e.getCrashReport());
                        resetGameState();
                        Hydon.LOGGER.error("Reported exception thrown", e);
                        displayCrashScreen(e.getCrashReport());
                    } catch (Throwable t) {
                        clientCrashCount++;
                        CrashReport report = new CrashReport("Unexpected error", t);
                        addGraphicsAndWorldToCrashReport(report);
                        addInfoToCrash(report);
                        resetGameState();
                        Hydon.LOGGER.error("Unreported exception thrown", t);
                        displayCrashScreen(report);
                    }
                } else {
                    serverCrashCount++;
                    addInfoToCrash(crashReporter);
                    freeMemory();
                    displayCrashScreen(crashReporter);
                    hasCrashed = false;
                    crashReporter = null;
                }
            }
        } catch (MinecraftError ignored) {
        } finally {
            shutdownMinecraftApplet();
        }
    }

    private void addInfoToCrash(CrashReport report) {
        report.getCategory().addCrashSectionCallable("Client crashes since restart", () -> String.valueOf(clientCrashCount));
        report.getCategory().addCrashSectionCallable("Integrated server crashes since restart", () -> String.valueOf(serverCrashCount));
    }

    private void displayInitErrorScreen(CrashReport report) {
        CrashUtils.outputReport(report);
        try {
            mcResourceManager = new SimpleReloadableResourceManager(metadataSerializer_);
            renderEngine = new TextureManager(mcResourceManager);
            mcResourceManager.registerReloadListener(renderEngine);

            mcLanguageManager = new LanguageManager(metadataSerializer_, gameSettings.language);
            mcResourceManager.registerReloadListener(mcLanguageManager);

            refreshResources();
            fontRendererObj = new FontRenderer(gameSettings, new ResourceLocation("textures/font/ascii.png"), renderEngine, false);
            mcResourceManager.registerReloadListener(fontRendererObj);

            mcSoundHandler = new SoundHandler(mcResourceManager, gameSettings);
            mcResourceManager.registerReloadListener(mcSoundHandler);

            running = true;
            Minecraft.getMinecraft().displayGuiScreen(new GuiInitErrorScreen(report));
        } catch (Throwable t) {
            Hydon.LOGGER.error("An uncaught exception occurred while displaying the init error screen, making normal report instead.", t);
            displayCrashReport(report);
            System.exit(report.getFile() != null ? -1 : -2);
        }
    }

    private void displayCrashScreen(CrashReport report) {
        try {
            CrashUtils.outputReport(report);

            hasCrashed = false;
            debugCrashKeyPressTime = -1;
            crashIntegratedServerNextTick = false;

            gameSettings.showDebugInfo = false;
            ingameGUI.getChatGUI().clearChatMessages();

            Minecraft.getMinecraft().displayGuiScreen(new GuiCrashScreen(report));
        } catch (Throwable t) {
            Hydon.LOGGER.error("An uncaught exception occured while displaying the crash screen, making normal report instead.", t);
            displayCrashReport(report);
            System.exit(report.getFile() != null ? -1 : -2);
        }
    }

    /**
     * @author Runemoro
     * @reason Custom crash reports
     */
    @Overwrite
    public void displayCrashReport(CrashReport report) {
        CrashUtils.outputReport(report);
    }

    private void resetGameState() {
        try {
            int originalMemoryReserverSize = -1;
            try {
                if (memoryReserve != null) {
                    originalMemoryReserverSize = memoryReserve.length;
                    memoryReserve = new byte[0];
                }
            } catch (Throwable ignored) {
            }

            StateManager.resetStates();

            if (getNetHandler() != null) {
                getNetHandler().getNetworkManager().closeChannel(new ChatComponentText("[Hydon] Client crashed"));
            }

            loadWorld(null);
            if (entityRenderer.isShaderActive()) entityRenderer.stopUseShader();
            scheduledTasks.clear();

            if (originalMemoryReserverSize != -1) {
                try {
                    memoryReserve = new byte[originalMemoryReserverSize];
                } catch (Throwable ignored) {
                }
            }

            System.gc();
        } catch (Throwable t) {
            Hydon.LOGGER.error("Failed to reset state after a crash", t);
            try {
                StateManager.resetStates();
            } catch (Throwable ignored) {
            }
        }
    }

    /**
     * @author Runemoro
     * @author Disconnect from the current world and free memory using a memory reserve
     */
    @Overwrite
    public void freeMemory() {
        resetGameState();
    }

    @Redirect(method = "runTick", at = @At(value = "FIELD", target = "Lnet/minecraft/client/Minecraft;debugCrashKeyPressTime:J", ordinal = 0))
    private long runTick(Minecraft mc) {
        if (Keyboard.isKeyDown(Keyboard.KEY_F3) && Keyboard.isKeyDown(Keyboard.KEY_C)) {
            debugCrashKeyPressTime = getSystemTime();
        } else {
            debugCrashKeyPressTime = -1L;
        }

        if (debugCrashKeyPressTime > 0L) {
            if (getSystemTime() - debugCrashKeyPressTime >= 0) {
                if (GuiScreen.isShiftKeyDown()) {
                    if (GuiScreen.isAltKeyDown()) {
                        if (integratedServerIsRunning) theIntegratedServer.addScheduledTask(() -> {
                            throw new ReportedException(new CrashReport("Manually triggered server-side scheduled task exception", new Throwable()));
                        });
                    } else {
                        scheduledTasks.add(ListenableFutureTask.create(() -> {
                            throw new ReportedException(new CrashReport("Manually triggered client-side scheduled task exception", new Throwable()));
                        }));
                    }
                } else {
                    if (GuiScreen.isAltKeyDown()) {
                        if (integratedServerIsRunning) crashIntegratedServerNextTick = true;
                    } else {
                        throw new ReportedException(new CrashReport("Manually triggered client-side debug crash", new Throwable()));
                    }
                }
            }
        }

        return -1;
    }

    @Redirect(method = "runTick", at = @At(value = "INVOKE", target = "Lorg/lwjgl/input/Keyboard;isKeyDown(I)Z", ordinal = 0))
    private boolean isKeyDownF3(int key) {
        return false;
    }

    @Override
    public boolean shouldCrashIntegratedServerNextTick() {
        return crashIntegratedServerNextTick;
    }
}