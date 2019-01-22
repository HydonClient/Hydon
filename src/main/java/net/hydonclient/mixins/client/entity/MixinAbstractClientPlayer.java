package net.hydonclient.mixins.client.entity;

import com.mojang.authlib.GameProfile;
import net.hydonclient.cosmetics.CosmeticData;
import net.hydonclient.cosmetics.CosmeticManager;
import net.hydonclient.cosmetics.capes.Capes;
import net.hydonclient.util.Multithreading;
import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.client.network.NetworkPlayerInfo;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(AbstractClientPlayer.class)
public abstract class MixinAbstractClientPlayer {

    @Inject(method = "<init>", at = @At("RETURN"))
    private void init(World worldIn, GameProfile playerProfile, CallbackInfo callbackInfo) {
        Multithreading.run(() -> {
            CosmeticData cosmeticData = CosmeticManager.getInstance().getData(playerProfile.getId());
            Capes.loadCape(playerProfile.getId(), cosmeticData.hasCape() ? cosmeticData.getCapeUrl()
                    : String.format("http://s.optifine.net/capes/%s.png", playerProfile.getName()));
        });
    }

    @Shadow
    protected abstract NetworkPlayerInfo getPlayerInfo();

    /**
     * @author Koding
     */
    @Overwrite
    public ResourceLocation getLocationCape() {
        NetworkPlayerInfo networkplayerinfo = this.getPlayerInfo();
        if (Capes.getCapes().containsKey(networkplayerinfo.getGameProfile().getId())) {
            return Capes.getCapes().get(networkplayerinfo.getGameProfile().getId());
        } else {
            return networkplayerinfo.getLocationCape();
        }
    }
}
