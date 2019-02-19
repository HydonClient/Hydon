package net.hydonclient.mods.vanillaenhancements.renderer;

import net.hydonclient.Hydon;
import net.hydonclient.event.EventListener;
import net.hydonclient.event.events.render.RenderGameOverlayEvent;
import net.hydonclient.mods.vanillaenhancements.VanillaEnhancements;
import net.hydonclient.util.maps.ChatColor;
import net.hydonclient.util.maps.ItemMaps;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.item.ItemPotion;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.StatCollector;
import org.lwjgl.opengl.GL11;

import java.util.List;
import java.util.Map;

public class AmplifierPreview {

    private VanillaEnhancements core;

    public AmplifierPreview(VanillaEnhancements core) {
        this.core = core;
    }

    @EventListener
    public void renderEnchantments(RenderGameOverlayEvent event) {
        if (Hydon.SETTINGS.AMPLIFIER_PREVIEW) {
            ItemStack heldItem = core.getMinecraft().thePlayer.inventory.getCurrentItem();

            if (heldItem != null) {
                String amplifier;

                if (heldItem.getItem() instanceof ItemPotion) {
                    amplifier = getPotionEffectString(heldItem);
                } else {
                    amplifier = getEnchantmentString(heldItem);
                }

                GL11.glPushMatrix();
                GL11.glScalef(0.5f, 0.5f, 0.5f);

                int width = core.getResolution().getScaledWidth() - (core.getMinecraft().fontRendererObj.getStringWidth(amplifier) >> 1);

                int height = core.getResolution().getScaledHeight() - 59;

                height += (core.getMinecraft().playerController.shouldDrawHUD() ? -2 : 14);
                height = height + core.getMinecraft().fontRendererObj.FONT_HEIGHT;
                height <<= 1;

                core.getMinecraft().fontRendererObj.drawString(
                        amplifier,
                        width,
                        height,
                        13421772);

                GL11.glScalef(2.0f, 2.0f, 2.0f);
                GL11.glPopMatrix();
            }
        }
    }

    private String getPotionEffectString(ItemStack stack) {
        ItemPotion potion = (ItemPotion) stack.getItem();
        List<PotionEffect> effects = potion.getEffects(stack);
        if (effects == null) {
            return "";
        }

        StringBuilder potionBuilder = new StringBuilder();

        for (final PotionEffect entry : effects) {
            final int duration = entry.getDuration() / 20;

            potionBuilder.append(ChatColor.BOLD.toString());
            potionBuilder.append(StatCollector.translateToLocal(entry.getEffectName()));

            potionBuilder.append(" ");
            potionBuilder.append(entry.getAmplifier() + 1);
            potionBuilder.append(" ");

            potionBuilder.append("(");
            potionBuilder.append(duration / 60).append(String.format(":%02d", duration % 60));
            potionBuilder.append(") ");
        }

        return potionBuilder.toString().trim();
    }

    private String getEnchantmentString(ItemStack stack) {
        StringBuilder enchantBuilder = new StringBuilder();

        Map<Integer, Integer> enchantments = EnchantmentHelper.getEnchantments(stack);

        for (Map.Entry<Integer, Integer> entry : enchantments.entrySet()) {
            enchantBuilder.append(ChatColor.BOLD.toString());
            enchantBuilder.append(ItemMaps.shortEnchantmentName.get(entry.getKey()));

            enchantBuilder.append(" ");
            enchantBuilder.append(entry.getValue());
            enchantBuilder.append(" ");
        }

        return enchantBuilder.toString().trim();
    }
}
