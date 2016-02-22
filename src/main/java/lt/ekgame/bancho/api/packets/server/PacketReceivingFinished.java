package lt.ekgame.bancho.api.packets.server;

import java.io.IOException;

import lt.ekgame.bancho.api.Bancho;
import lt.ekgame.bancho.api.packets.ByteDataInputStream;
import lt.ekgame.bancho.api.packets.ByteDataOutputStream;
import lt.ekgame.bancho.api.packets.Packet;

/**
 * Signals that the server finished sending data.
 * Client should consider all relavent startup data received.
 */
public class PacketReceivingFinished extends Packet {

	@Override
	public void read(ByteDataInputStream stream, int length) throws IOException {
		// no data
	}

	@Override
	public void write(ByteDataOutputStream stream) throws IOException {
		// no data
	}

	@Override
	public int size(Bancho bancho) {
		return 0;
	}

}
