package net.hydonclient.managers.impl.command.impl;

import com.kodingking.netty.client.ClientData;
import com.kodingking.netty.universal.UniversalNetty;
import com.kodingking.netty.universal.packet.impl.data.CPacketSetCosmeticEnabled;
import java.util.function.Predicate;
import net.hydonclient.api.UserManager;
import net.hydonclient.api.objects.Cosmetic;
import net.hydonclient.api.objects.User;
import net.hydonclient.managers.impl.command.Command;
import net.minecraft.client.Minecraft;

public class CommandToggleCosmetic extends Command {

    @Override
    public String getName() {
        return "togglecosmetic";
    }

    @Override
    public String getUsage() {
        return "togglecosmetic <WINGS|CAPE>";
    }

    @Override
    public void onCommand(String[] args) {
        if (args.length == 0) {
            sendMsg("Usage: " + getUsage());
        } else {
            String cosmetic = args[0];
            User user = UserManager.getInstance()
                .getUser(Minecraft.getMinecraft().getSession().getProfile().getId());
            Predicate<Cosmetic> predicate = cosmetic1 -> cosmetic1.getName()
                .equalsIgnoreCase(cosmetic);
            if (user.getCosmetics().stream().anyMatch(predicate)) {
                boolean enabled = user.getCosmetics().stream().filter(predicate).findFirst().get()
                    .isEnabled();
                UniversalNetty.getCurrentBootstrap().sendPacket(new CPacketSetCosmeticEnabled(
                    ClientData.INSTANCE.getClientUuid(), cosmetic.toUpperCase(), !enabled));
                sendMsg("Set cosmetic to: " + (!enabled ? "enabled" : "disabled"));
            } else {
                sendMsg("You do not have this cosmetic unlocked.");
            }
        }
    }
}
