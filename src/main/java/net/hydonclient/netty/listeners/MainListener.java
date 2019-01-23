package net.hydonclient.netty.listeners;

import com.kodingking.netty.universal.packet.IPacket;
import com.kodingking.netty.universal.packet.ListenerAdapter;
import com.kodingking.netty.universal.packet.impl.data.SPacketReloadUser;
import io.netty.channel.ChannelHandlerContext;
import net.hydonclient.api.UserManager;

public class MainListener extends ListenerAdapter {

    @Override
    public void onReceived(ChannelHandlerContext connection, IPacket packet) {
        if (packet instanceof SPacketReloadUser) {
            UserManager.getInstance().reprocess(((SPacketReloadUser) packet).getTarget());
        }
    }
}
