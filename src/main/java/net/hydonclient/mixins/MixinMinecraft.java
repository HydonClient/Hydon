package net.hydonclient.mixins;

import net.hydonclient.Hydon;
import net.hydonclient.SplashScreen;
import net.hydonclient.event.EventBus;
import net.hydonclient.event.events.gui.GuiDisplayEvent;
import net.hydonclient.event.events.render.RenderTickEvent;
import net.hydonclient.gui.GuiHydonMainMenu;
import net.hydonclient.mixinsimp.HydonMinecraft;
import net.hydonclient.util.GuiUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.SoundHandler;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.gui.GuiChat;
import net.minecraft.client.gui.GuiGameOver;
import net.minecraft.client.gui.GuiIngame;
import net.minecraft.client.gui.GuiMainMenu;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.Display;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.At.Shift;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Minecraft.class)
public abstract class MixinMinecraft {

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

    private HydonMinecraft impl = new HydonMinecraft((Minecraft) (Object) this);

    @Inject(method = "createDisplay", at = @At(value = "INVOKE", target = "Lorg/lwjgl/opengl/Display;setTitle(Ljava/lang/String;)V", shift = At.Shift.AFTER))
    private void createDisplay(CallbackInfo callbackInfo) {
        Display.setTitle("[STARTING] Hydon // " + Hydon.VERSION);
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
        SplashScreen.advanceProgress("Loading model manager...");
    }

    @Inject(method = "startGame", at = @At(value = "NEW", target = "net/minecraft/client/renderer/entity/RenderManager", shift = At.Shift.AFTER))
    private void loadingStartGame5(CallbackInfo callbackInfo) {
        SplashScreen.advanceProgress("Loading render manager...");
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

    /**
     * @author Koding
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
            guiScreenIn = new GuiMainMenu();
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
        Display.setTitle("Hydon // " + Hydon.VERSION);
    }

    @Inject(method = "shutdown", at = @At("HEAD"))
    private void shutdown(CallbackInfo callbackInfo) {
        Hydon.INSTANCE.stop();
    }

    /**
     * @author Koding
     */
    @Overwrite
    private void drawSplashScreen(TextureManager textureManagerInstance) {
        SplashScreen.render(textureManagerInstance);
    }
}
