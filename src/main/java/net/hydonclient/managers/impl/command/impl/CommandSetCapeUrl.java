package net.hydonclient.managers.impl.command.impl;

import com.kodingking.netty.client.ClientData;
import com.kodingking.netty.universal.UniversalNetty;
import com.kodingking.netty.universal.packet.impl.data.CPacketSetCapeUrl;
import java.util.function.Predicate;
import net.hydonclient.api.UserManager;
import net.hydonclient.api.objects.Cosmetic;
import net.hydonclient.api.objects.User;
import net.hydonclient.managers.impl.command.Command;
import net.minecraft.client.Minecraft;

public class CommandSetCapeUrl extends Command {

    @Override
    public String getName() {
        return "setcapeurl";
    }

    @Override
    public String getUsage() {
        return "setcapeurl <URL>";
    }

    @Override
    public void onCommand(String[] args) {
        if (args.length == 0) {
            sendMsg("Usage: " + getUsage());
        } else {
            String url = args[0];
            if (!url.toLowerCase().endsWith(".png") && !url.toLowerCase().endsWith(".jpg") && !url.toLowerCase().endsWith(".jpeg")) {
                sendMsg("Enter a valid image URL.");
                return;
            }
            User user = UserManager.getInstance()
                .getUser(Minecraft.getMinecraft().getSession().getProfile().getId());
            Predicate<Cosmetic> predicate = cosmetic1 -> cosmetic1.getName()
                .equalsIgnoreCase("CAPE");
            if (user.getCosmetics().stream().anyMatch(predicate)) {
                UniversalNetty.getCurrentBootstrap()
                    .sendPacket(new CPacketSetCapeUrl(ClientData.INSTANCE.getClientUuid(), url));
                sendMsg("Successfully updated cape URL.");
            } else {
                sendMsg("You do not have this cosmetic unlocked.");
            }
        }
    }
}
