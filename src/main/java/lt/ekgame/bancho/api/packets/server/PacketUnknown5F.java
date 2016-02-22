package lt.ekgame.bancho.api.packets.server;

import java.io.IOException;

import lt.ekgame.bancho.api.packets.ByteDataInputStream;
import lt.ekgame.bancho.api.packets.Packet;

public class PacketUnknown5F extends Packet {
	
	int integer;

	@Override
	public void read(ByteDataInputStream stream, int length) throws IOException {
		integer = stream.readInt();
		
		//System.out.println("[0x5F] " + integer);
	}
}
