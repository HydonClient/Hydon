package net.hydonclient.mixinsimp.client.renderer;

import net.hydonclient.mixins.client.renderer.IMixinGuiContainer;
import net.hydonclient.mixins.client.renderer.IMixinInventoryEffectRenderer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.InventoryEffectRenderer;

public class HydonInventoryEffectRenderer {

    private InventoryEffectRenderer inventoryEffectRenderer;

    public HydonInventoryEffectRenderer(InventoryEffectRenderer inventoryEffectRenderer) {
        this.inventoryEffectRenderer = inventoryEffectRenderer;
    }

    public void updateActivePotionEffects(int xSize) {
        ((IMixinInventoryEffectRenderer) inventoryEffectRenderer).setHasActivePotionEffects(!Minecraft.getMinecraft().thePlayer.getActivePotionEffects().isEmpty());
        ((IMixinGuiContainer) inventoryEffectRenderer).setGuiLeft((inventoryEffectRenderer.width - xSize) / 2);
    }
}
