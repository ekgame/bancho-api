package lt.ekgame.bancho.api.packets.client;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import lt.ekgame.bancho.api.Bancho;
import lt.ekgame.bancho.api.packets.ByteDataInputStream;
import lt.ekgame.bancho.api.packets.ByteDataOutputStream;
import lt.ekgame.bancho.api.packets.Packet;

/**
 * Sent by the client to request information about users.
 */
public class PacketRequestUserInfo extends Packet {
	
	public List<Integer> userIds = new ArrayList<>();
	
	public PacketRequestUserInfo() {}
	
	public PacketRequestUserInfo(List<Integer> userIds) {
		this.userIds.addAll(userIds);
	}
	
	public PacketRequestUserInfo(int... userIds) {
		for (int id : userIds)
			this.userIds.add(id);
	}

	@Override
	public void read(ByteDataInputStream stream, int length) throws IOException {
		short num = stream.readShort();
		for (int i = 0; i < num; i++)
			this.userIds.add(stream.readInt());
	}

	@Override
	public void write(ByteDataOutputStream stream) throws IOException {
		stream.writeInt(userIds.size());
		for (int id : userIds)
			stream.writeInt(id);
	}

	@Override
	public int size(Bancho bancho) {
		return 2 + userIds.size()*4;
	}

}
