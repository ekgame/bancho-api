package lt.ekgame.bancho.api.packets.server;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import lt.ekgame.bancho.api.Bancho;
import lt.ekgame.bancho.api.packets.ByteDataInputStream;
import lt.ekgame.bancho.api.packets.ByteDataOutputStream;
import lt.ekgame.bancho.api.packets.Packet;

/**
 * Likely to be a list of online users ids.
 */
public class PacketUsersOnline extends Packet {
	
	public List<Integer> userIds = new ArrayList<>();
	
	public PacketUsersOnline() {}
	
	public PacketUsersOnline(int... integers) {
		for (int i : integers)
			userIds.add(i);
	}
	
	public PacketUsersOnline(List<Integer> integers) {
		for (int i : integers)
			userIds.add(i);
	}

	@Override
	public void write(ByteDataOutputStream stream) throws IOException {
		stream.writeShort((short) userIds.size());
		for (int i : userIds)
			stream.writeInt(i);
	}
	
	@Override
	public void read(ByteDataInputStream stream, int length) throws IOException {
		int num = stream.readShort();
		for (int i = 0; i < num; i++)
			userIds.add(stream.readInt());
	}

	@Override
	public int size(Bancho bancho) {
		return 2 + 4*userIds.size();
	}
	
}
