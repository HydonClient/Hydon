package net.hydonclient.mixins.client.gui;

import net.hydonclient.mixinsimp.client.gui.HydonGuiIngameMenu;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiIngameMenu;
import net.minecraft.client.gui.GuiScreen;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(GuiIngameMenu.class)
public class MixinGuiIngameMenu extends GuiScreen {

    private HydonGuiIngameMenu impl = new HydonGuiIngameMenu((GuiIngameMenu) (Object) this);

    @Inject(method = "initGui", at = @At("RETURN"))
    private void initGui(CallbackInfo ci) {
        impl.initGui(buttonList);
    }

    @Inject(method = "actionPerformed", at = @At("RETURN"))
    private void actionPerformed(GuiButton button, CallbackInfo ci) {
        impl.actionPerformed(button);
    }
}
