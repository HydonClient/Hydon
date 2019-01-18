package net.hydonclient.mixinsimp.client.gui;

import net.hydonclient.event.EventBus;
import net.hydonclient.event.events.render.RenderGameOverlayEvent;
import net.hydonclient.managers.impl.config.SaveVal;
import net.hydonclient.mods.vanillaenhancements.config.VEConfiguration;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiIngame;
import net.minecraft.client.gui.ScaledResolution;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

public class HydonGuiIngame {

    private GuiIngame guiIngame;

    public HydonGuiIngame(GuiIngame guiIngame) {
        this.guiIngame = guiIngame;
    }

    public void renderGameOverlay(float partialTicks, CallbackInfo ci) {
        EventBus.call(new RenderGameOverlayEvent(new ScaledResolution(Minecraft.getMinecraft()), partialTicks));
    }

    public void showCrosshair(CallbackInfoReturnable<Boolean> cir) {
        if (!VEConfiguration.THIRD_PERSON_CROSSHAIR && Minecraft.getMinecraft().gameSettings.thirdPersonView > 0) {
            cir.setReturnValue(false);
        }
    }
}
