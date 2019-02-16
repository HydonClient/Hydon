package net.hydonclient.util.patches;

import net.minecraft.client.multiplayer.ServerData;

public interface IPatchedGuiMultiplayer {

    void makeDirectConnect();

    void setIp(ServerData ip);
}
