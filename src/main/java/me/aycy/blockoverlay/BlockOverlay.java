package me.aycy.blockoverlay;

import net.hydonclient.event.EventBus;
import net.hydonclient.mods.Mod;
import net.hydonclient.mods.Mod.Info;
import me.aycy.blockoverlay.renderer.BlockOverlayRenderer;
import net.minecraft.client.Minecraft;

@Info(name = "Block Overlay", author = "aycy", version = "3.0")
public class BlockOverlay extends Mod {

    public static final Minecraft mc = Minecraft.getMinecraft();

    @Override
    public void load() {
        EventBus.register(new BlockOverlayRenderer());
    }
}
