package net.hydonclient.mixins.network;

import io.netty.channel.ChannelHandlerContext;
import net.hydonclient.event.EventBus;
import net.hydonclient.event.events.game.ChatMessageReceivedEvent;
import net.hydonclient.event.events.hypixel.GameEndEvent;
import net.hydonclient.event.events.hypixel.GameWinEvent;
import net.hydonclient.event.events.network.PacketReceivedEvent;
import net.hydonclient.event.events.network.PacketSendEvent;
import net.hydonclient.util.HypixelUtils;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S02PacketChat;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(NetworkManager.class)
public class MixinNetworkManager {

    @Inject(method = "channelRead0", at = @At("HEAD"), cancellable = true)
    private void channelRead0(ChannelHandlerContext channelHandlerContext, Packet packet, CallbackInfo callbackInfo) {
        if (packet instanceof S02PacketChat) {
            String message = ((S02PacketChat) packet).getChatComponent().getUnformattedText();

            ChatMessageReceivedEvent chatMessageReceivedEvent = new ChatMessageReceivedEvent(message, ((S02PacketChat) packet).getChatComponent());
            EventBus.call(chatMessageReceivedEvent);
            if (chatMessageReceivedEvent.isCancelled()) {
                callbackInfo.cancel();
            }

            if (HypixelUtils.hasGameEnded(message)) {
                EventBus.call(new GameEndEvent());
            }

            if (HypixelUtils.hasWonGame(message)) {
                EventBus.call(new GameWinEvent());
            }
        }

        PacketReceivedEvent packetReceivedEvent = new PacketReceivedEvent(packet);
        EventBus.call(packetReceivedEvent);
        if (packetReceivedEvent.isCancelled()) {
            callbackInfo.cancel();
        }
    }

    @Inject(method = "sendPacket(Lnet/minecraft/network/Packet;)V", at = @At("HEAD"), cancellable = true)
    private void sendPacket(Packet packet, CallbackInfo callbackInfo) {
        PacketSendEvent packetSendEvent = new PacketSendEvent(packet);
        EventBus.call(packetSendEvent);
        if (packetSendEvent.isCancelled()) {
            callbackInfo.cancel();
        }
    }
}