package net.hydonclient.mixins.client.gui;

import net.hydonclient.mixinsimp.client.gui.HydonGuiIngame;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiIngame;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(GuiIngame.class)
public class MixinGuiIngame extends Gui {

    private HydonGuiIngame impl = new HydonGuiIngame((GuiIngame) (Object) this);

    @Inject(method = "renderGameOverlay", at = @At("RETURN"))
    private void renderGameOverlay(float partialTicks, CallbackInfo ci) {
        impl.renderGameOverlay(partialTicks, ci);
    }

    /**
     * @author asbyth
     * @reason Disable Crosshair in Third Person
     * TODO: Add an option for this (Vanilla Enhancements?)
     */
    @Inject(method = "showCrosshair", at = @At("HEAD"), cancellable = true)
    private void showCrosshair(CallbackInfoReturnable<Boolean> cir) {
        impl.showCrosshair(cir);
    }
}
