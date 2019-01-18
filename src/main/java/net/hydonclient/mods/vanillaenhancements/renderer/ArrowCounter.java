package net.hydonclient.mods.vanillaenhancements.renderer;

import net.hydonclient.Hydon;
import net.hydonclient.event.EventListener;
import net.hydonclient.event.events.render.RenderGameOverlayEvent;
import net.hydonclient.mods.vanillaenhancements.VanillaEnhancements;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.WorldSettings;

public class ArrowCounter {

    private VanillaEnhancements core;

    public ArrowCounter(VanillaEnhancements core) {
        this.core = core;
    }

    @EventListener
    public void renderArrowCount(RenderGameOverlayEvent event) {
        if (Hydon.SETTINGS.arrowCounter) {
            EntityPlayerSP player = core.getMinecraft().thePlayer;
            if (player != null) {
                ItemStack heldItem = player.getHeldItem();
                if (heldItem != null) {
                    if (heldItem.getUnlocalizedName().equalsIgnoreCase("item.bow")) {
                        int count = 0;
                        for (ItemStack is : player.inventory.mainInventory) {
                            if (is != null) {
                                if (is.getUnlocalizedName().equalsIgnoreCase("item.arrow"))
                                    count += is.stackSize;
                            }
                        }

                        FontRenderer font = core.getMinecraft().fontRendererObj;

                        int offset = (core.getMinecraft().playerController.getCurrentGameType() == WorldSettings.GameType.CREATIVE) ? 10 : 0;

                        font.drawString(
                                Integer.toString(count),
                                (float) (core.getResolution().getScaledWidth() - font.getStringWidth(Integer.toString(count)) >> 1),
                                (float) (core.getResolution().getScaledHeight() - 46 - offset),
                                16777215, true);
                    }
                }
            }
        }
    }
}
