package lt.ekgame.bancho.test;

import static org.junit.Assert.*;

import org.junit.Test;

import lt.ekgame.bancho.api.packets.Packet;
import lt.ekgame.bancho.client.RateLimiter;
import lt.ekgame.bancho.client.impl.RateLimiterImpl;

public class TestRateLimiter {

	@Test
	public void test() {
		int numPackets = 10;
		int delay = 1000;
		long startTime = System.currentTimeMillis();
		
		RateLimiter limiter = new RateLimiterImpl(delay);
		for (int i = 0; i < numPackets; i++)
			limiter.sendPacket(new PacketTest());
		
		Packet packet;
		while (limiter.hasQueuedPackets()) {
			packet = limiter.getOutgoingPacket();
			if (packet != null)
				System.out.println(System.currentTimeMillis());
		}
		
		if (System.currentTimeMillis() - startTime < delay*(numPackets-1))
			fail("Rate not limited enough.");
	}
	
	class PacketTest extends Packet {}
}
