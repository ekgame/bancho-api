package lt.ekgame.bancho.api.packets.client;

import java.io.IOException;

import lt.ekgame.bancho.api.Bancho;
import lt.ekgame.bancho.api.packets.ByteDataInputStream;
import lt.ekgame.bancho.api.packets.ByteDataOutputStream;
import lt.ekgame.bancho.api.packets.Packet;

/**
 * Sends a list of strings and a list of integers.
 * Max of 80 for both.
 */
public class PacketUnknown44 extends Packet {

	@Override
	public void write(ByteDataOutputStream stream) throws IOException {
		// TODO IMPLEMENT
	}
	
	@Override
	public void read(ByteDataInputStream stream, int length) throws IOException {
		// TODO IMPLEMENT
	}

	@Override
	public int size(Bancho bancho) {
		return 0; // TODO IMPLEMENT
	}
}
