package net.hydonclient.mods.vanillaenhancements;

import net.hydonclient.event.EventBus;
import net.hydonclient.mods.Mod;
import net.hydonclient.mods.Mod.Info;
import net.hydonclient.mods.vanillaenhancements.renderer.ArmorPotential;
import net.hydonclient.mods.vanillaenhancements.renderer.ArrowCounter;
import net.hydonclient.mods.vanillaenhancements.renderer.AmplifierPreview;
import net.hydonclient.mods.vanillaenhancements.renderer.DamagePreview;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;

@Info(name = "Vanilla Enhancements", author = "OrangeMarshall", version = "1.0")
public class VanillaEnhancements extends Mod {

    // TODO: fix the masking issue when opening inventories with an enchanted item

    @Override
    public void load() {
        EventBus.register(new ArmorPotential(this));
        EventBus.register(new ArrowCounter(this));
        EventBus.register(new AmplifierPreview(this));
        EventBus.register(new DamagePreview(this));
    }

    public Minecraft getMinecraft() {
        return Minecraft.getMinecraft();
    }

    public ScaledResolution getResolution() {
        return new ScaledResolution(getMinecraft());
    }
}
