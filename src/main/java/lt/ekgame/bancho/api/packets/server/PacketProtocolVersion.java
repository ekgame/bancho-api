package lt.ekgame.bancho.api.packets.server;

import java.io.IOException;

import lt.ekgame.bancho.api.Bancho;
import lt.ekgame.bancho.api.packets.ByteDataInputStream;
import lt.ekgame.bancho.api.packets.ByteDataOutputStream;
import lt.ekgame.bancho.api.packets.Packet;

/**
 * Protocol version.
 * One of the first packets sent by the server after auth.
 */
public class PacketProtocolVersion extends Packet {
	
	public int protocolVersion;
	
	public PacketProtocolVersion() {}
	
	public PacketProtocolVersion(int protocolVersion) {
		this.protocolVersion = protocolVersion;
	}

	@Override
	public void read(ByteDataInputStream stream, int length) throws IOException {
		protocolVersion = stream.readInt();
	}

	@Override
	public void write(ByteDataOutputStream stream) throws IOException {
		stream.writeInt(protocolVersion);
	}

	@Override
	public int size(Bancho bancho) {
		return 4;
	}
	
	

}
