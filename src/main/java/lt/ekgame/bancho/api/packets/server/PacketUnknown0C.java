package lt.ekgame.bancho.api.packets.server;

import java.io.IOException;

import lt.ekgame.bancho.api.packets.ByteDataInputStream;
import lt.ekgame.bancho.api.packets.Packet;

public class PacketUnknown0C extends Packet {
	
	public int integer1;
	public byte byte1;
	
	@Override
	public void read(ByteDataInputStream stream, int length) throws IOException {
		integer1 = stream.readInt();
		byte1 = stream.readByte();
		//System.out.printf("[0xOC] i: %d, b: %d\n", integer1, byte1);
	}
}
