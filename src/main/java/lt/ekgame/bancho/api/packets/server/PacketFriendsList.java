package lt.ekgame.bancho.api.packets.server;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import lt.ekgame.bancho.api.Bancho;
import lt.ekgame.bancho.api.packets.ByteDataInputStream;
import lt.ekgame.bancho.api.packets.ByteDataOutputStream;
import lt.ekgame.bancho.api.packets.Packet;


public class PacketFriendsList extends Packet {
	
	List<Integer> list = new ArrayList<>();

	@Override
	public void read(ByteDataInputStream stream, int length) throws IOException {
		int num = stream.readShort();
		for (int i = 0; i < num; i++)
			list.add(stream.readInt());
	}
	
	@Override
	public void write(ByteDataOutputStream stream) throws IOException {
		stream.writeShort((short) list.size());
		for (int number : list)
			stream.writeInt(number);
	}

	@Override
	public int size(Bancho bancho) {
		return 2 + 4*list.size();
	}
	
	

}
