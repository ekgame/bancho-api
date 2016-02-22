package lt.ekgame.bancho.api.packets;

import java.io.IOException;

import lt.ekgame.bancho.api.Bancho;

public abstract class Packet {

	public void read(ByteDataInputStream stream, int length) throws IOException {
		throw new UnsupportedOperationException(this.getClass().toString());
	}
	
	public void write(ByteDataOutputStream stream) throws IOException {
		throw new UnsupportedOperationException(this.getClass().toString());
	}
	
	public int size(Bancho bancho) {
		throw new UnsupportedOperationException(this.getClass().toString());
	}
}
