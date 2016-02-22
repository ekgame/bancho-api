package lt.ekgame.bancho.api.packets.server;

import java.io.IOException;

import lt.ekgame.bancho.api.Bancho;
import lt.ekgame.bancho.api.packets.ByteDataInputStream;
import lt.ekgame.bancho.api.packets.ByteDataOutputStream;
import lt.ekgame.bancho.api.packets.Packet;
import lt.ekgame.bancho.api.utils.DataUtils;

/**
 * Notifies the client about a public room's existence and updates (active users).
 */
public class PacketChatRoomInfo extends Packet {
	
	public String channelName;
	public String channelDescription;
	public int numUsers;
	
	public PacketChatRoomInfo() {}
	
	public PacketChatRoomInfo(String name, String description, int users) {
		channelName = name;
		channelDescription = description;
		numUsers = users;
	}

	@Override
	public void read(ByteDataInputStream stream, int length) throws IOException {
		channelName = stream.readString();
		channelDescription = stream.readString();
		numUsers = stream.readInt();
	}

	@Override
	public void write(ByteDataOutputStream stream) throws IOException {
		stream.writeString(channelName);
		stream.writeString(channelDescription);
		stream.writeInt(numUsers);
	}

	@Override
	public int size(Bancho bancho) {
		return DataUtils.stringLen(channelName) 
			 + DataUtils.stringLen(channelDescription)
			 + 4;
	}

}
