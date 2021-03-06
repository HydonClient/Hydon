package net.hydonclient.mixins;

import net.hydonclient.Hydon;
import net.hydonclient.event.EventBus;
import net.hydonclient.event.events.game.WorldChangedEvent;
import net.hydonclient.event.events.gui.GuiDisplayEvent;
import net.hydonclient.event.events.gui.MouseInputEvent;
import net.hydonclient.event.events.render.RenderTickEvent;
import net.hydonclient.gui.GuiHydonMainMenu;
import net.hydonclient.gui.SplashScreen;
import net.hydonclient.mixinsimp.HydonMinecraft;
import net.hydonclient.packages.MinecraftPackageBootstrap;
import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.PositionedSoundRecord;
import net.minecraft.client.audio.SoundHandler;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.gui.GuiGameOver;
import net.minecraft.client.gui.GuiIngame;
import net.minecraft.client.gui.GuiMainMenu;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.profiler.IPlayerUsage;
import net.minecraft.profiler.Profiler;
import net.minecraft.util.IThreadListener;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.Display;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.At.Shift;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Minecraft.class)
public abstract class MixinMinecraft implements IThreadListener, IPlayerUsage {

    @Shadow
    public GuiScreen currentScreen;
    @Shadow
    public WorldClient theWorld;
    @Shadow
    public EntityPlayerSP thePlayer;
    @Shadow
    public GameSettings gameSettings;
    @Shadow
    public GuiIngame ingameGUI;

    @Shadow
    public int displayWidth;
    @Shadow
    public int displayHeight;
    @Shadow
    private boolean fullscreen;

    @Shadow
    public abstract void setIngameNotInFocus();

    @Shadow
    public boolean skipRenderWorld;
    @Shadow
    private SoundHandler mcSoundHandler;

    @Shadow
    public abstract void setIngameFocus();

    @Shadow
    @Final
    public Profiler mcProfiler;

    @Shadow long systemTime;

    private HydonMinecraft impl = new HydonMinecraft((Minecraft) (Object) this);

    @Inject(method = "createDisplay", at = @At(value = "INVOKE", target = "Lorg/lwjgl/opengl/Display;setTitle(Ljava/lang/String;)V", shift = At.Shift.AFTER))
    private void createDisplay(CallbackInfo callbackInfo) {
        Display.setTitle("Hydon | [STARTING]");
    }

    @Inject(method = "startGame", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/Minecraft;drawSplashScreen(Lnet/minecraft/client/renderer/texture/TextureManager;)V", shift = At.Shift.AFTER))
    private void loadingStartGame1(CallbackInfo callbackInfo) {
        SplashScreen.advanceProgress("Starting to load...");
    }

    @Inject(method = "startGame", at = @At(value = "NEW", target = "net/minecraft/client/audio/SoundHandler", shift = At.Shift.AFTER))
    private void loadingStartGame2(CallbackInfo callbackInfo) {
        SplashScreen.advanceProgress("Initializing sound handler...");
    }

    @Inject(method = "startGame", at = @At(value = "NEW", target = "net/minecraft/client/gui/FontRenderer", shift = At.Shift.AFTER))
    private void loadingStartGame3(CallbackInfo callbackInfo) {
        SplashScreen.advanceProgress("Initializing font renderer...");
    }

    @Inject(method = "startGame", at = @At(value = "NEW", target = "net/minecraft/client/resources/model/ModelManager", shift = At.Shift.AFTER))
    private void loadingStartGame4(CallbackInfo callbackInfo) {
        SplashScreen.advanceProgress("Loading model core...");
    }

    @Inject(method = "startGame", at = @At(value = "NEW", target = "net/minecraft/client/renderer/entity/RenderManager", shift = At.Shift.AFTER))
    private void loadingStartGame5(CallbackInfo callbackInfo) {
        SplashScreen.advanceProgress("Loading render core...");
    }

    @Inject(method = "startGame", at = @At(value = "NEW", target = "net/minecraft/client/renderer/EntityRenderer", shift = At.Shift.AFTER))
    private void loadingStartGame6(CallbackInfo callbackInfo) {
        SplashScreen.advanceProgress("Loading entity renderer...");
    }

    @Inject(method = "startGame", at = @At(value = "NEW", target = "net/minecraft/client/renderer/RenderGlobal", shift = At.Shift.AFTER))
    private void loadingStartGame7(CallbackInfo callbackInfo) {
        SplashScreen.advanceProgress("Loading render global...");
    }

    @Inject(method = "startGame", at = @At(value = "NEW", target = "net/minecraft/client/particle/EffectRenderer", shift = At.Shift.AFTER))
    private void loadingStartGame8(CallbackInfo callbackInfo) {
        SplashScreen.advanceProgress("Loading effect renderer...");
    }

    @Inject(method = "runGameLoop", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/EntityRenderer;updateCameraAndRender(FJ)V", shift = Shift.AFTER))
    private void runGameLoop(CallbackInfo callbackInfo) {
        EventBus.call(new RenderTickEvent(new ScaledResolution(Minecraft.getMinecraft())));
    }

    @Inject(method = "setInitialDisplayMode", at = @At("HEAD"), cancellable = true)
    private void setInitialDisplayMode(CallbackInfo ci) throws LWJGLException {
        impl.setInitialDisplayMode(fullscreen, displayWidth, displayHeight, ci);
    }

    @Inject(method = "toggleFullscreen", at = @At(value = "INVOKE", target = "Lorg/lwjgl/opengl/Display;setVSyncEnabled(Z)V", shift = Shift.AFTER))
    private void toggleFullscreen(CallbackInfo ci) throws LWJGLException {
        impl.toggleFullscreen(fullscreen, displayWidth, displayHeight, ci);
    }

    @Inject(method = "loadWorld(Lnet/minecraft/client/multiplayer/WorldClient;Ljava/lang/String;)V", at = @At(
            value = "INVOKE", target = "Ljava/lang/System;gc()V"), cancellable = true)
    private void loadWorld(WorldClient worldClient, String loadingMessage, CallbackInfo callbackInfo) {
        EventBus.call(new WorldChangedEvent());
        systemTime = 0;
        callbackInfo.cancel();
    }

    /**
     * @author Koding
     * @reason Create the main menu
     */
    @Overwrite
    public void displayGuiScreen(GuiScreen guiScreenIn) {
        if (guiScreenIn instanceof GuiMainMenu) {
            guiScreenIn = new GuiHydonMainMenu();
        }

        if (this.currentScreen != null) {
            this.currentScreen.onGuiClosed();
        }

        if (guiScreenIn == null && this.theWorld == null) {
            guiScreenIn = new GuiHydonMainMenu();
        } else if (guiScreenIn == null && this.thePlayer.getHealth() <= 0.0F) {
            guiScreenIn = new GuiGameOver();
        }

        if (guiScreenIn instanceof GuiMainMenu) {
            this.gameSettings.showDebugInfo = false;
            this.ingameGUI.getChatGUI().clearChatMessages();
        }

        EventBus.call(new GuiDisplayEvent(guiScreenIn));

        this.currentScreen = guiScreenIn;

        if (guiScreenIn != null) {
            this.setIngameNotInFocus();
            ScaledResolution scaledresolution = new ScaledResolution(Minecraft.getMinecraft());
            int i = scaledresolution.getScaledWidth();
            int j = scaledresolution.getScaledHeight();
            guiScreenIn.setWorldAndResolution(Minecraft.getMinecraft(), i, j);
            this.skipRenderWorld = false;
        } else {
            this.mcSoundHandler.resumeSounds();
            this.setIngameFocus();
        }
    }

    @Inject(method = "startGame", at = @At(value = "NEW", target = "net/minecraft/client/gui/GuiIngame", shift = Shift.AFTER))
    private void startGame(CallbackInfo callbackInfo) {
        Hydon.INSTANCE.start();
    }

    @Inject(method = "startGame", at = @At("RETURN"))
    private void startGame2(CallbackInfo callbackInfo) {
        Display.setTitle("Hydon | " + Hydon.VERSION);
        SoundHandler handler = Minecraft.getMinecraft().getSoundHandler();
        handler.playSound(PositionedSoundRecord.create(new ResourceLocation("note.pling"), 1.0f));
    }

    @Inject(method = "shutdown", at = @At("HEAD"))
    private void shutdown(CallbackInfo callbackInfo) {
        Hydon.INSTANCE.stop();

        MinecraftPackageBootstrap.shutdown();
    }

    @Inject(method = "runTick", at = @At(value = "INVOKE", target = "Lorg/lwjgl/input/Mouse;next()Z", shift = Shift.BEFORE))
    private void runTick(CallbackInfo callbackInfo) {
        EventBus.call(new MouseInputEvent());
    }

    /**
     * @author Koding
     * @reason Custom splash screen
     */
    @Overwrite
    private void drawSplashScreen(TextureManager textureManagerInstance) {
        SplashScreen.render(textureManagerInstance);
    }

    @Redirect(method = "runTick", at = @At(value = "INVOKE", target = "Lnet/minecraft/profiler/Profiler;endStartSection(Ljava/lang/String;)V", ordinal = 0))
    private void endStartGUISection(Profiler profiler, String name) {
        profiler.endStartSection("gui");
    }

    @Redirect(method = "runTick", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/texture/TextureManager;tick()V", ordinal = 0))
    private void tickTextureManagerWithProperProfiler(TextureManager textureManager) {
        mcProfiler.endStartSection("textures");
        textureManager.tick();
        mcProfiler.endStartSection("gui");
    }

    @Inject(method = "getLimitFramerate", at = @At("HEAD"), cancellable = true)
    private void getLimitFramerate(CallbackInfoReturnable<Integer> cir) {
        if (!Display.isActive() && Hydon.SETTINGS.LIMIT_FRAMERATE && gameSettings.limitFramerate > 30) {
            cir.setReturnValue(30);
        } else if (gameSettings.limitFramerate < 30) {
            cir.setReturnValue(gameSettings.limitFramerate);
        }
    }
}