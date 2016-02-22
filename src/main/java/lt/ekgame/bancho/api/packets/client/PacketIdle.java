package lt.ekgame.bancho.api.packets.client;

import java.io.IOException;

import lt.ekgame.bancho.api.Bancho;
import lt.ekgame.bancho.api.packets.ByteDataInputStream;
import lt.ekgame.bancho.api.packets.ByteDataOutputStream;
import lt.ekgame.bancho.api.packets.Packet;

/**
 * Sent when there is nothing else to send.
 */
public class PacketIdle extends Packet {
	
	@Override
	public void read(ByteDataInputStream stream, int length) throws IOException {
		// This is supposed to be empty
	}

	@Override
	public void write(ByteDataOutputStream stream) throws IOException {
		// This is supposed to be empty
	}
	
	@Override
	public int size(Bancho bancho) {
		return 0;
	}
}
