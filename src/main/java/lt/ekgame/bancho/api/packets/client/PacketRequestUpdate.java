package lt.ekgame.bancho.api.packets.client;

import java.io.IOException;

import lt.ekgame.bancho.api.Bancho;
import lt.ekgame.bancho.api.packets.ByteDataInputStream;
import lt.ekgame.bancho.api.packets.ByteDataOutputStream;
import lt.ekgame.bancho.api.packets.Packet;

public class PacketRequestUpdate extends Packet {

	public void read(ByteDataInputStream stream, int length) throws IOException {
		// no data
	}
	
	public void write(ByteDataOutputStream stream) throws IOException {
		// no data
	}
	
	public int size(Bancho bancho) {
		return 0;
	}
}
