package net.hydonclient.mixins.client.renderer;

import net.hydonclient.mixinsimp.client.renderer.HydonInventoryEffectRenderer;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.InventoryEffectRenderer;
import net.minecraft.inventory.Container;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

@Mixin(InventoryEffectRenderer.class)
public abstract class MixinInventoryEffectRenderer extends GuiContainer {

    private HydonInventoryEffectRenderer impl = new HydonInventoryEffectRenderer((InventoryEffectRenderer) (Object) this);

    public MixinInventoryEffectRenderer(Container inventorySlotsIn) {
        super(inventorySlotsIn);
    }

    /**
     * @author Mojang
     * @reason Stop the inventory from moving over to the right when receiving a potion effect
     */
    @Overwrite
    protected void updateActivePotionEffects() {
        impl.updateActivePotionEffects(xSize);
    }
}
