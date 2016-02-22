package lt.ekgame.bancho.api.packets.client;

import java.io.IOException;

import lt.ekgame.bancho.api.Bancho;
import lt.ekgame.bancho.api.packets.ByteDataInputStream;
import lt.ekgame.bancho.api.packets.ByteDataOutputStream;
import lt.ekgame.bancho.api.packets.Packet;

/**
 * Sent by the client to signal leaving a mulyiplayer room.
 */
public class PacketLeaveRoom extends Packet {

	@Override
	public void read(ByteDataInputStream stream, int length) throws IOException {
		// no data, just a signal
	}

	@Override
	public void write(ByteDataOutputStream stream) throws IOException {
		// no data, just a signal
	}

	@Override
	public int size(Bancho bancho) {
		return 0;
	}

}
