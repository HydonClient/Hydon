package net.hydonclient.mods.vanillaenhancements.renderer;

import net.hydonclient.Hydon;
import net.hydonclient.event.EventListener;
import net.hydonclient.event.events.render.RenderGameOverlayEvent;
import net.hydonclient.mods.vanillaenhancements.VanillaEnhancements;
import net.minecraft.item.ItemStack;
import org.lwjgl.opengl.GL11;

public class DamagePreview {

    private VanillaEnhancements core;

    public DamagePreview(VanillaEnhancements core) {
        this.core = core;
    }

    @EventListener
    public void renderDamage(RenderGameOverlayEvent event) {
        if (Hydon.SETTINGS.DAMAGE_PREVIEW) {
            ItemStack stack = core.getMinecraft().thePlayer.inventory.getCurrentItem();

            if (stack != null) {
                GL11.glPushMatrix();
                GL11.glScalef(0.5f, 0.5f, 0.5f);

                String attackDamage = getAttackDamageString(stack);

                int width = core.getResolution().getScaledWidth() - (core.getMinecraft().fontRendererObj.getStringWidth(attackDamage) >> 1);

                int height = core.getResolution().getScaledHeight() - 59;

                height += (core.getMinecraft().playerController.shouldDrawHUD() ? -1 : 14);
                height = height + core.getMinecraft().fontRendererObj.FONT_HEIGHT;
                height <<= 1;
                height += core.getMinecraft().fontRendererObj.FONT_HEIGHT;

                core.getMinecraft().fontRendererObj.drawString(
                        attackDamage,
                        width,
                        height,
                        13421772);

                GL11.glScalef(2.0f, 2.0f, 2.0f);
                GL11.glPopMatrix();
            }
        }
    }

    private String getAttackDamageString(ItemStack stack) {
        for (String itemLore : stack.getTooltip(core.getMinecraft().thePlayer, true)) {
            if (itemLore.endsWith("Attack Damage")) {
                return itemLore.split(" ", 2)[0].substring(2);
            }
        }
        return "";
    }
}
