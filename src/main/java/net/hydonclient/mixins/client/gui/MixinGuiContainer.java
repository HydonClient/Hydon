package net.hydonclient.mixins.client.gui;

import net.minecraft.client.gui.inventory.GuiContainer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(GuiContainer.class)
public abstract class MixinGuiContainer {

    @Shadow
    protected abstract boolean checkHotbarKeys(int keyCode);

    /**
     * @author Sk1er
     * @reason Mousebind Fix
     */
    @Inject(method = "mouseClicked", at = @At("RETURN"))
    private void mouseClicked(int mouseX, int mouseY, int mouseButton, CallbackInfo ci) {
        checkHotbarKeys(mouseButton - 100);
    }
}
