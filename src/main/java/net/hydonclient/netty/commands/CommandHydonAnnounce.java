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

    /**
     * Used to announce things to players currently using Hydon
     * ex: An update has been released! Head over to the Discord to download the latest update
     *
     * @param args arguments after the usage
     */
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
