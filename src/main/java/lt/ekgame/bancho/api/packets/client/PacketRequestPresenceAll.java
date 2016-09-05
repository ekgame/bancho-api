package lt.ekgame.bancho.api.packets.client;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import lt.ekgame.bancho.api.Bancho;
import lt.ekgame.bancho.api.packets.ByteDataInputStream;
import lt.ekgame.bancho.api.packets.ByteDataOutputStream;
import lt.ekgame.bancho.api.packets.Packet;

public class PacketRequestPresenceAll extends Packet {
	
	protected int gametime;
	
	public PacketRequestPresenceAll() {}
	
	public PacketRequestPresenceAll(int gametime) {
		this.gametime = gametime;
	}

	@Override
	public void write(ByteDataOutputStream stream) throws IOException {
		stream.writeInt(gametime);
	}
	
	@Override
	public void read(ByteDataInputStream stream, int length) throws IOException {
		gametime = stream.readInt();
	}

	@Override
	public int size(Bancho bancho) {
		return 4;
	}
}
