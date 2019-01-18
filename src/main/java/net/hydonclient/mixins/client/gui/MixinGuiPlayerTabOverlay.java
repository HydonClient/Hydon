package net.hydonclient.mixins.client.gui;

import me.semx11.autotip.Autotip;
import me.semx11.autotip.event.impl.EventClientConnection;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiPlayerTabOverlay;
import net.minecraft.util.IChatComponent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(GuiPlayerTabOverlay.class)
public class MixinGuiPlayerTabOverlay extends Gui {

    @Inject(method = "setHeader", at = @At("HEAD"))
    private void setHeader(IChatComponent headerIn, CallbackInfo ci) {
        EventClientConnection.header = headerIn;
    }
}
