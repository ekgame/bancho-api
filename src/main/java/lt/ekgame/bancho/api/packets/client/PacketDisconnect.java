package lt.ekgame.bancho.api.packets.client;

import java.io.IOException;

import lt.ekgame.bancho.api.Bancho;
import lt.ekgame.bancho.api.packets.ByteDataInputStream;
import lt.ekgame.bancho.api.packets.ByteDataOutputStream;
import lt.ekgame.bancho.api.packets.Packet;

/**
 * Sent to the server to declare that the client is signing out.
 */
public class PacketDisconnect extends Packet {
	
	public int integer;
	
	public PacketDisconnect() {}
	
	public PacketDisconnect(int integer) {
		this.integer = integer;
	}

	@Override
	public void read(ByteDataInputStream stream, int length) throws IOException {
		integer = stream.readInt();
	}

	@Override
	public void write(ByteDataOutputStream stream) throws IOException {
		stream.writeInt(integer);
	}

	@Override
	public int size(Bancho bancho) {
		return 4;
	}

}
