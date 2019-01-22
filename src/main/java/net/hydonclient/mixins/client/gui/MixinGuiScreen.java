package net.hydonclient.mixins.client.gui;

import net.hydonclient.mixinsimp.client.gui.HydonGuiScreen;
import net.hydonclient.util.GuiUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiScreenResourcePacks;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
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

    /**
     * @author KodingKing
     */
    @Overwrite
    public void drawDefaultBackground() {
        renderBG();
    }

    /**
     * @author KodingKing
     */
    @Overwrite
    public void drawBackground(int tint) {
        renderBG();
    }

    private void renderBG() {
        GuiScreen screen = Minecraft.getMinecraft().currentScreen;
        if (screen instanceof GuiScreenResourcePacks) {
            GuiUtils.drawBG(true);
        } else if (Minecraft.getMinecraft().theWorld != null && Minecraft
                .getMinecraft().theWorld.playerEntities.contains(Minecraft.getMinecraft().thePlayer)) {
            GuiUtils.drawIngameGuiGradient();
        } else {
            GuiUtils.drawBG();
        }
    }
}
