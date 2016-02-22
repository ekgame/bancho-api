package lt.ekgame.bancho.api.packets.server;

import java.io.IOException;

import lt.ekgame.bancho.api.packets.ByteDataInputStream;
import lt.ekgame.bancho.api.packets.Packet;

/**
 * Sent to the client when the server wants you to clear a certain user's
 * messages from public chat rooms (maybe even pms?). Can be interpreted
 * as a user being silenced or banned(?).
 */
public class PacketClearUserChat extends Packet {
	
	public int userId;

	@Override
	public void read(ByteDataInputStream stream, int length) throws IOException {
		userId = stream.readInt();
	}
}
