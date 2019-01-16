package net.hydonclient.event.events.network;

import net.hydonclient.event.Event;
import net.minecraft.network.Packet;

public class PacketReceivedEvent extends Event {

    private Packet packet;

    public PacketReceivedEvent(Packet packet) {
        this.packet = packet;
    }

    public Packet getPacket() {
        return packet;
    }
}
