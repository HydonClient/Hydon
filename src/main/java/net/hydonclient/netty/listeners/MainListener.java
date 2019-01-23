package net.hydonclient.netty.listeners;

import com.kodingking.netty.universal.packet.IPacket;
import com.kodingking.netty.universal.packet.ListenerAdapter;
import com.kodingking.netty.universal.packet.impl.data.CPacketAnnounce;
import com.kodingking.netty.universal.packet.impl.data.SPacketReloadUser;
import com.kodingking.netty.universal.packet.impl.handshake.minecraft.SPacketLoginSuccess;
import io.netty.channel.ChannelHandlerContext;
import net.hydonclient.api.UserManager;
import net.hydonclient.api.objects.User;
import net.hydonclient.managers.HydonManagers;
import net.hydonclient.netty.commands.CommandHydonAnnounce;
import net.hydonclient.util.ChatUtils;
import net.minecraft.client.Minecraft;

public class MainListener extends ListenerAdapter {

    @Override
    public void onReceived(ChannelHandlerContext connection, IPacket packet) {
        if (packet instanceof SPacketLoginSuccess) {
            User user = UserManager.getInstance()
                .getUser(Minecraft.getMinecraft().getSession().getProfile().getId());
            if (user.isAdmin()) {
                HydonManagers.INSTANCE.getCommandManager().register(new CommandHydonAnnounce());
            }
        } else if (packet instanceof SPacketReloadUser) {
            UserManager.getInstance().reprocess(((SPacketReloadUser) packet).getTarget());
        } else if (packet instanceof CPacketAnnounce) {
            ChatUtils.addChatMessage(((CPacketAnnounce) packet).getMessage());
        }
    }
}
