package lt.ekgame.bancho.api.packets.server;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import lt.ekgame.bancho.api.Bancho;
import lt.ekgame.bancho.api.packets.ByteDataInputStream;
import lt.ekgame.bancho.api.packets.ByteDataOutputStream;
import lt.ekgame.bancho.api.packets.Packet;

/**
 * sends a list of integers
 */
public class PacketUnknown61 extends Packet {
	
	protected List<Integer> ints = new ArrayList<>();
	
	public PacketUnknown61() {}
	
	public PacketUnknown61(int... integers) {
		for (int i : integers)
			ints.add(i);
	}
	
	public PacketUnknown61(List<Integer> integers) {
		for (int i : integers)
			ints.add(i);
	}

	@Override
	public void write(ByteDataOutputStream stream) throws IOException {
		stream.writeShort((short) ints.size());
		for (int i : ints)
			stream.writeInt(i);
	}
	
	@Override
	public void read(ByteDataInputStream stream, int length) throws IOException {
		int num = stream.readShort();
		for (int i = 0; i < num; i++)
			ints.add(stream.readInt());
	}

	@Override
	public int size(Bancho bancho) {
		return 2 + 4*ints.size();
	}
	
	
	

}
