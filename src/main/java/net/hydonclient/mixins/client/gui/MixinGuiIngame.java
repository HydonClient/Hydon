package net.hydonclient.mixins.client.gui;

import net.hydonclient.mixinsimp.client.gui.HydonGuiIngame;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiIngame;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.boss.BossStatus;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(GuiIngame.class)
public abstract class MixinGuiIngame extends Gui {

    @Shadow @Final private Minecraft mc;

    @Shadow public abstract FontRenderer getFontRenderer();

    private HydonGuiIngame impl = new HydonGuiIngame((GuiIngame) (Object) this);

    @Inject(method = "renderGameOverlay", at = @At("RETURN"))
    private void renderGameOverlay(float partialTicks, CallbackInfo ci) {
        impl.renderGameOverlay(partialTicks, ci);
    }

    /**
     * @author asbyth
     * @reason Disable Crosshair in Third Person
     */
    @Inject(method = "showCrosshair", at = @At("HEAD"), cancellable = true)
    private void showCrosshair(CallbackInfoReturnable<Boolean> cir) {
        impl.showCrosshair(cir);
    }

    @Inject(method = "displayTitle", at = @At("HEAD"), cancellable = true)
    public void displayTitle(String title, String subTitle, int timeFadeIn, int displayTime, int timeFadeOut, CallbackInfo ci) {
        impl.displayTitle(title, subTitle, timeFadeIn, displayTime, timeFadeOut, ci);
    }

    /**
     * @author Koding
     */
    @Overwrite
    private void renderBossHealth() {
        impl.renderBossHealth();
    }
}
