package lt.ekgame.bancho.client.impl;

import java.util.LinkedList;
import java.util.Queue;

import lt.ekgame.bancho.api.packets.Packet;
import lt.ekgame.bancho.client.RateLimiter;

public class RateLimiterImpl implements RateLimiter {
	
	private int delay;
	private Queue<Packet> outgoing = new LinkedList<>();
	private long lastSentTime = 0;
	
	public RateLimiterImpl(int delay) {
		this.delay = delay;
	}

	@Override
	public void sendPacket(Packet packet) {
		synchronized (outgoing) {
			outgoing.add(packet);
		}
	}

	@Override
	public Packet getOutgoingPacket() {
		if (System.currentTimeMillis() - lastSentTime > delay) {
			synchronized (outgoing) {
				Packet packet = outgoing.poll();
				if (packet != null)
					lastSentTime = System.currentTimeMillis();
				return packet;
			}
		}
		return null;
	}

	@Override
	public boolean hasQueuedPackets() {
		synchronized (outgoing) {
			return !outgoing.isEmpty();
		}
	}
}
