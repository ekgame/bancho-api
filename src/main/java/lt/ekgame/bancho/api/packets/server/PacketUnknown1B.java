package lt.ekgame.bancho.api.packets.server;

import java.io.IOException;

import lt.ekgame.bancho.api.packets.ByteDataInputStream;
import lt.ekgame.bancho.api.packets.Packet;

/**
 * Something to do with multiplayer rooms.
 * Possible status update.
 */
public class PacketUnknown1B extends Packet {

	@Override
	public void read(ByteDataInputStream stream, int length) throws IOException {
		// TODO implement
	}
	
}
