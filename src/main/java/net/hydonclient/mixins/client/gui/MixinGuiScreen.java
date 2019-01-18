package net.hydonclient.mixins.client.gui;

import net.hydonclient.mixinsimp.client.gui.HydonGuiScreen;
import net.minecraft.client.gui.GuiScreen;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(GuiScreen.class)
public abstract class MixinGuiScreen {

    private HydonGuiScreen impl = new HydonGuiScreen((GuiScreen) (Object) this);

    @Inject(method = "drawScreen", at = @At("HEAD"), cancellable = true)
    private void drawScreen(int mouseX, int mouseY, float partialTicks, CallbackInfo ci) {
        impl.drawScreen(mouseX, mouseY, partialTicks, ci);
    }
}
