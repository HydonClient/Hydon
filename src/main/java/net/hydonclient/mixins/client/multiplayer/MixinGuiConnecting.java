package net.hydonclient.mixins.client.multiplayer;

import net.hydonclient.mixinsimp.client.multiplayer.HydonGuiConnecting;
import net.minecraft.client.multiplayer.GuiConnecting;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(GuiConnecting.class)
public class MixinGuiConnecting {

    private HydonGuiConnecting impl = new HydonGuiConnecting();

    @Inject(method = "connect", at = @At("HEAD"))
    private void connect(String ip, int port, CallbackInfo ci) {
        impl.connect(ip, port);
    }
}
