package net.hydonclient.mods.vanillaenhancements.renderer;

import net.hydonclient.Hydon;
import net.hydonclient.event.EventListener;
import net.hydonclient.event.events.gui.GuiDrawScreenEvent;
import net.hydonclient.mods.vanillaenhancements.VanillaEnhancements;
import net.minecraft.client.gui.inventory.GuiContainerCreative;
import net.minecraft.client.gui.inventory.GuiInventory;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;

public class ArmorPotential {

    private String lastMessage = "";

    private VanillaEnhancements core;

    public ArmorPotential(VanillaEnhancements core) {
        this.core = core;
    }

    @EventListener
    public void onRenderArmor(GuiDrawScreenEvent event) {
        if (Hydon.SETTINGS.PROTECTION_PREVIEW || Hydon.SETTINGS.PROJECTILE_PROT_PREVIEW) {
            if (event.getScreen() instanceof GuiInventory || event.getScreen() instanceof GuiContainerCreative) {
                String percentage = getArmorString();
                lastMessage = percentage;

                core.getMinecraft().currentScreen.drawString(
                        core.getMinecraft().fontRendererObj,
                        percentage,
                        10,
                        core.getResolution().getScaledHeight() - 16,
                        16777215);
            }

        }
    }

    private String getArmorString() {
        double ap = roundDecimals(getArmorPotentional(false));
        double app = roundDecimals(getArmorPotentional(true));

        if (Hydon.SETTINGS.PROTECTION_PREVIEW || Hydon.SETTINGS.PROJECTILE_PROT_PREVIEW) {
            String lastMessage;
            String percent = Hydon.SETTINGS.PROTECTION_PREVIEW ? (lastMessage = ap + "%") : (lastMessage = app + "%");
            this.lastMessage = lastMessage;
            return percent;
        }

        if (ap == app) {
            return this.lastMessage = ap + "%";
        }
        return this.lastMessage = ap + "% | " + app + "%";
    }

    private double roundDecimals(double num) {
        if (num == 0.0) {
            return num;
        }

        num = (int) (num * Math.pow(10.0, 2));
        num /= Math.pow(10.0, 2);

        return num;
    }

    private double getArmorPotentional(boolean getProj) {
        EntityPlayer player = core.getMinecraft().thePlayer;

        double armor = 0.0;
        int epf = 0;
        int resistance = 0;

        if (player.isPotionActive(Potion.resistance)) {
            resistance = player.getActivePotionEffect(Potion.resistance).getAmplifier() + 1;
        }

        for (ItemStack stack : player.inventory.armorInventory) {
            if (stack != null) {
                if (stack.getItem() instanceof ItemArmor) {
                    ItemArmor armorItem = (ItemArmor) stack.getItem();
                    armor += armorItem.damageReduceAmount * 0.04;
                }

                if (stack.isItemEnchanted()) {
                    epf += getEffProtPoints(EnchantmentHelper.getEnchantmentLevel(0, stack));
                }

                if (getProj && stack.isItemEnchanted()) {
                    epf += getEffProtPoints(EnchantmentHelper.getEnchantmentLevel(4, stack));
                }
            }
        }

        epf = ((epf < 25) ? epf : 25);

        double avgDef = addArmorProtResistance(armor, calcProtection(epf), resistance);
        return roundDouble(avgDef * 100.0);
    }

    private int getEffProtPoints(int level) {
        return (level != 0) ? ((int) Math.floor((6 + level * level) * 0.75 / 3.0)) : 0;
    }

    private double calcProtection(int armorEpf) {
        double protection = 0.0;

        for (int i = 50; i <= 100; i++) {
            protection += ((Math.ceil(armorEpf * i / 100.0) < 20.0) ? Math.ceil(armorEpf * i / 100.0) : 20.0);
        }
        return protection / 51.0;
    }

    private double addArmorProtResistance(double armor, double prot, int resistance) {
        double protTotal = armor + (1.0 - armor) * prot * 0.04;
        protTotal += (1.0 - protTotal) * resistance * 0.2;

        return (protTotal < 1.0) ? protTotal : 1.0;
    }

    private double roundDouble(double number) {
        double x = Math.round(number * 10000.0);
        return x / 10000.0;
    }
}
