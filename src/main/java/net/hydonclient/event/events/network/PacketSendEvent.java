package net.hydonclient.event.events.network;

import net.hydonclient.event.CancellableEvent;
import net.minecraft.network.Packet;

public class PacketSendEvent extends CancellableEvent {

    private Packet packet;

    /**
     * Fired when the client sends a packet
     *
     * @param packet the packet sent
     */
    public PacketSendEvent(Packet packet) {
        this.packet = packet;
    }

    public Packet getPacket() {
        return packet;
    }
}
