package lt.ekgame.bancho.api.packets.server;

import java.io.IOException;

import lt.ekgame.bancho.api.Bancho;
import lt.ekgame.bancho.api.packets.ByteDataInputStream;
import lt.ekgame.bancho.api.packets.ByteDataOutputStream;
import lt.ekgame.bancho.api.packets.Packet;

/**
 * Has something to do with the client and it's concept of time.
 * I don't really know where it's used, but you receive it right after auth.
 */
public class PacketTimeSync extends Packet {
	
	public int timeDifference;
	
	public PacketTimeSync() {}
	
	public PacketTimeSync(int timeDifference) {
		this.timeDifference = timeDifference;
	}

	@Override
	public void read(ByteDataInputStream stream, int length) throws IOException {
		timeDifference = stream.readInt();
	}

	@Override
	public void write(ByteDataOutputStream stream) throws IOException {
		stream.writeInt(timeDifference);
	}

	@Override
	public int size(Bancho bancho) {
		return 4;
	}
	
	
	
}
