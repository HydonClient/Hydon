package net.hydonclient.event.events.network;

import net.hydonclient.event.CancellableEvent;
import net.minecraft.network.Packet;

public class PacketSendEvent extends CancellableEvent {

    private Packet packet;

    public PacketSendEvent(Packet packet) {
        this.packet = packet;
    }

    public Packet getPacket() {
        return packet;
    }
}
