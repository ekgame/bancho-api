package lt.ekgame.bancho.api.packets.server;

import java.io.IOException;

import lt.ekgame.bancho.api.packets.ByteDataInputStream;
import lt.ekgame.bancho.api.packets.Packet;

public class PacketUserJoined extends Packet {
	
	public int userId;

	@Override
	public void read(ByteDataInputStream stream, int length) throws IOException {
		userId = stream.readInt();
	}
}
