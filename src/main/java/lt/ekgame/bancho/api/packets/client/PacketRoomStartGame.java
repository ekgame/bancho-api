package lt.ekgame.bancho.api.packets.client;

import java.io.IOException;

import lt.ekgame.bancho.api.Bancho;
import lt.ekgame.bancho.api.packets.ByteDataInputStream;
import lt.ekgame.bancho.api.packets.ByteDataOutputStream;
import lt.ekgame.bancho.api.packets.Packet;

/**
 * If the client is a host, it can send this packet to start the game.
 * It is not guaranteed that the game will actually start (not enought ready players)
 */
public class PacketRoomStartGame extends Packet {
	
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
