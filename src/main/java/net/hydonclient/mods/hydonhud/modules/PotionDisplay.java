package net.hydonclient.mods.hydonhud.modules;

import net.hydonclient.event.EventListener;
import net.hydonclient.event.events.render.RenderGameOverlayEvent;
import net.hydonclient.mods.hydonhud.HydonHUD;
import net.hydonclient.mods.hydonhud.builder.ModuleBuilder;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiChat;
import net.minecraft.client.resources.I18n;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;

import java.util.Collection;

public class PotionDisplay implements ModuleBuilder {

    private HydonHUD core;

    public PotionDisplay(HydonHUD core) {
        this.core = core;
    }

    @EventListener
    public void display(RenderGameOverlayEvent event) {
        if (!core.getConfig().SHOW_POTIONSTATUS_IN_CHAT && Minecraft.getMinecraft().currentScreen instanceof GuiChat) {
            return;
        }

        Collection<PotionEffect> effects;
        effects = core.getMinecraft().thePlayer.getActivePotionEffects();

        for (PotionEffect potionEffect : effects) {
            Potion potion = Potion.potionTypes[potionEffect.getPotionID()];

            StringBuilder effectName = new StringBuilder(I18n.format(potion.getName()));

            if (potionEffect.getAmplifier() == 1) {
                effectName.append(" ").append(
                        I18n.format("enchantment.level.2"));

            } else if (potionEffect.getAmplifier() == 2) {
                effectName.append(" ").append(
                        I18n.format("enchantment.level.3"));

            } else if (potionEffect.getAmplifier() == 3) {
                effectName.append(" ").append(
                        I18n.format("enchantment.level.4"));
            }

            String duration = Potion.getDurationString(potionEffect);
            String jointedText;

            // TODO: fix the NPE when changing the separator (any reason why an npe is being thrown?), then replace " * " with the used separator
            if (core.getConfig().POTIONSTATUS && !core.getMinecraft().gameSettings.showDebugInfo) {
                if (!core.getConfig().POTIONSTATUS_PARENTHESES) {
                    jointedText = ("" + effectName + " * " + duration);
                } else {
                    jointedText = ("(" + effectName + " * " + duration + ")");
                }

                if (core.getConfig().POTIONSTATUS_SHADOW) {
                    core.drawStringWithShadow(jointedText, core.getConfig().potionStatusX, core.getConfig().potionStatusY,
                            16777215);
                } else {
                    core.drawString(jointedText, core.getConfig().potionStatusX, core.getConfig().potionStatusY,
                            16777215);
                }
            }
        }
    }
}
