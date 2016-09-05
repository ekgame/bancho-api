package lt.ekgame.bancho.client;

import lt.ekgame.bancho.api.packets.Packet;

public interface RateLimiter {
	
	public void sendPacket(Packet packet);
	
	public boolean hasQueuedPackets();
	
	public Packet getOutgoingPacket();

}
