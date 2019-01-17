package net.hydonclient.mixins.client.gui;

import net.minecraft.client.gui.GuiGameOver;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(GuiGameOver.class)
public class MixinGuiGameOver {

    @Shadow
    private int enableButtonsTimer;

    /**
     * @author Mojang
     * @reason Button locks itself if you toggle fullscreen after the GUI pops up
     */
    @Inject(method = "initGui", at = @At("HEAD"))
    private void initGui(CallbackInfo ci) {
        enableButtonsTimer = 0;
    }
}
