package net.hydonclient.mixins.client.entity;

import com.mojang.authlib.GameProfile;
import net.hydonclient.event.EventBus;
import net.hydonclient.event.events.game.ChatMessageSendEvent;
import net.hydonclient.event.events.game.UpdateEvent;
import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.potion.Potion;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.At.Shift;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(EntityPlayerSP.class)
public class MixinEntityPlayerSP extends AbstractClientPlayer {

    @Shadow
    public float prevTimeInPortal;

    @Shadow
    public float timeInPortal;

    public MixinEntityPlayerSP(World worldIn,
                               GameProfile playerProfile) {
        super(worldIn, playerProfile);
    }

    @Inject(method = "onUpdate", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/World;isBlockLoaded(Lnet/minecraft/util/BlockPos;)Z", shift = Shift.AFTER))
    private void onUpdate(CallbackInfo callbackInfo) {
        EventBus.call(new UpdateEvent());
    }

    @Inject(method = "sendChatMessage", at = @At("HEAD"), cancellable = true)
    private void sendChatMessage(String message, CallbackInfo callbackInfo) {
        ChatMessageSendEvent chatMessageSendEvent = new ChatMessageSendEvent(message);
        EventBus.call(chatMessageSendEvent);
        if (chatMessageSendEvent.isCancelled()) {
            callbackInfo.cancel();
        }
    }

    /**
     * @author asbyth
     * @reason Fixes MC-7519 - When you would clear an effect like Nausea, a portal animation would show up
     */
    @Override
    public void removePotionEffectClient(int potionID) {
        if (potionID == Potion.confusion.id) {
            prevTimeInPortal = 0.0F;
            timeInPortal = 0.0F;
        }
        removePotionEffect(potionID);
    }
}
