package net.hydonclient.mixins.entity;

import com.google.common.base.Throwables;
import com.mojang.authlib.GameProfile;
import java.util.List;
import java.util.Objects;
import net.hydonclient.event.EventBus;
import net.hydonclient.event.events.game.ChatMessageSendEvent;
import net.hydonclient.event.events.game.UpdateEvent;
import net.hydonclient.util.ReflectionUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.shader.Shader;
import net.minecraft.client.shader.ShaderGroup;
import net.minecraft.client.shader.ShaderUniform;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.At.Shift;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(EntityPlayerSP.class)
public class MixinEntityPlayerSP extends AbstractClientPlayer {

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
}
