package net.hydonclient.mixinsimp.client.gui;

import net.hydonclient.event.EventBus;
import net.hydonclient.event.events.gui.GuiDrawScreenEvent;
import net.minecraft.client.gui.GuiScreen;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

public class HydonGuiScreen {

    private GuiScreen guiScreen;

    public HydonGuiScreen(GuiScreen guiScreen) {
        this.guiScreen = guiScreen;
    }

    public void drawScreen(int mouseX, int mouseY, float partialTicks, CallbackInfo ci) {
        EventBus.call(new GuiDrawScreenEvent(guiScreen, mouseX, mouseY, partialTicks));
    }
}
