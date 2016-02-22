package lt.ekgame.bancho.api.packets.server;

import java.io.IOException;

import lt.ekgame.bancho.api.Bancho;
import lt.ekgame.bancho.api.packets.ByteDataInputStream;
import lt.ekgame.bancho.api.packets.ByteDataOutputStream;
import lt.ekgame.bancho.api.packets.Packet;

/**
 * Notifies the client of it's privileges.
 * Other packets suggest that there may be 5 bits that mean something.
 * 1st bit - unknown
 * 2nd bit - QAT/GMT
 * 3rd bit - supporter
 */
public class PacketUserType extends Packet {
	
	int flags;
	
	public PacketUserType() {}
	
	public PacketUserType(int flags) {
		this.flags = flags;
	}

	@Override
	public void read(ByteDataInputStream stream, int length) throws IOException {
		flags = stream.readInt();
	}

	@Override
	public void write(ByteDataOutputStream stream) throws IOException {
		stream.write(flags);
	}

	@Override
	public int size(Bancho bancho) {
		return 4;
	}
}
