package net.hydonclient.mixins.client;

import net.hydonclient.Hydon;
import net.minecraft.client.ClientBrandRetriever;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

@Mixin(ClientBrandRetriever.class)
public class MixinClientBrandRetriever {

    /**
     * @author asbyth
     * @reason Client branding
     */
    @Overwrite
    public static String getClientModName() {
        return "Hydon " + Hydon.VERSION;
    }
}
