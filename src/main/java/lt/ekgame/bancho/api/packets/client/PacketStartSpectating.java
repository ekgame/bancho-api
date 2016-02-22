package lt.ekgame.bancho.api.packets.client;

import java.io.IOException;

import lt.ekgame.bancho.api.Bancho;
import lt.ekgame.bancho.api.packets.ByteDataOutputStream;
import lt.ekgame.bancho.api.packets.Packet;

/**
 * Starts spectating a user.
 */
public class PacketStartSpectating extends Packet {
	
	private int userId;
	
	public PacketStartSpectating(int userId) {
		this.userId = userId;
	}

	@Override
	public void write(ByteDataOutputStream stream) throws IOException {
		stream.writeInt(userId);
	}

	@Override
	public int size(Bancho bancho) {
		return 4;
	}
}
