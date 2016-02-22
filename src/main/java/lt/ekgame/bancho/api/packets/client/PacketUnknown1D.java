package lt.ekgame.bancho.api.packets.client;

import java.io.IOException;

import lt.ekgame.bancho.api.Bancho;
import lt.ekgame.bancho.api.packets.ByteDataInputStream;
import lt.ekgame.bancho.api.packets.ByteDataOutputStream;
import lt.ekgame.bancho.api.packets.Packet;

public class PacketUnknown1D extends Packet {

	@Override
	public void write(ByteDataOutputStream stream) throws IOException {
		// no data
	}
	
	@Override
	public void read(ByteDataInputStream stream, int length) throws IOException {
		// no data
	}

	@Override
	public int size(Bancho bancho) {
		return 0;
	}

}
