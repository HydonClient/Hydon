package net.hydonclient.netty.commands;

import com.kodingking.netty.client.ClientData;
import com.kodingking.netty.universal.UniversalNetty;
import com.kodingking.netty.universal.packet.impl.data.CPacketAnnounce;
import net.hydonclient.managers.impl.command.Command;

public class CommandHydonAnnounce extends Command {

    @Override
    public String getName() {
        return "hydonannounce";
    }

    @Override
    public String getUsage() {
        return "hydonannounce";
    }

    @Override
    public void onCommand(String[] args) {
        if (args.length == 0) {
            sendMsg("Specify a message.");
        } else {
            String message = String.join(" ", args);
            UniversalNetty.getCurrentBootstrap().sendPacket(new CPacketAnnounce(ClientData.INSTANCE.getClientUuid(), message));
        }
    }
}
