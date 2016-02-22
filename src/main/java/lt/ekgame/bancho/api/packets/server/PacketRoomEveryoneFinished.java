package lt.ekgame.bancho.api.packets.server;

import java.io.IOException;

import lt.ekgame.bancho.api.Bancho;
import lt.ekgame.bancho.api.packets.ByteDataInputStream;
import lt.ekgame.bancho.api.packets.ByteDataOutputStream;
import lt.ekgame.bancho.api.packets.Packet;

/**
 * Sent to the client when everyone in the multiplayer room (in progress)
 * have finished playing the map. A signal that the scoreboard can now be displayed.
 */
public class PacketRoomEveryoneFinished extends Packet {

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
