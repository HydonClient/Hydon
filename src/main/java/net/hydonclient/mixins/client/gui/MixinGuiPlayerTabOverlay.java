package net.hydonclient.mixins.client.gui;

import me.semx11.autotip.event.impl.EventClientConnection;
import net.hydonclient.mixinsimp.client.gui.HydonGuiPlayerTabOverlay;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiPlayerTabOverlay;
import net.minecraft.client.network.NetworkPlayerInfo;
import net.minecraft.util.IChatComponent;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(GuiPlayerTabOverlay.class)
public class MixinGuiPlayerTabOverlay extends Gui {

    @Shadow @Final private Minecraft mc;

    private HydonGuiPlayerTabOverlay impl = new HydonGuiPlayerTabOverlay((GuiPlayerTabOverlay) (Object) this);

    @Inject(method = "setHeader", at = @At("HEAD"))
    private void setHeader(IChatComponent headerIn, CallbackInfo ci) {
        EventClientConnection.header = headerIn;
    }

    /**
     * @author asbyth & Mojang
     * @reason ping number in tab
     */
    @Overwrite
    protected void drawPing(int textureX, int textureY, int yIn, NetworkPlayerInfo networkPlayerInfoIn) {
        impl.drawPing(textureX, textureY, yIn, networkPlayerInfoIn, zLevel, mc);
    }
}
