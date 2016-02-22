package lt.ekgame.bancho.api.packets.client;

import java.io.IOException;

import lt.ekgame.bancho.api.Bancho;
import lt.ekgame.bancho.api.packets.ByteDataInputStream;
import lt.ekgame.bancho.api.packets.ByteDataOutputStream;
import lt.ekgame.bancho.api.packets.Packet;
import lt.ekgame.bancho.api.utils.DataUtils;

/**
 * Sends a private message to a user. 
 * To send message to a channel use PacketSendMessageChannel.
 */
public class PacketSendMessageUser extends Packet {
	
	public String message, user;
	
	public PacketSendMessageUser() {}
	
	public PacketSendMessageUser(String message, String user) {
		this.message = message;
		this.user = user;
	}

	@Override
	public void read(ByteDataInputStream stream, int length) throws IOException {
		stream.readString();
		message = stream.readString();
		user = stream.readString();
		stream.readInt();
	}

	@Override
	public void write(ByteDataOutputStream stream) throws IOException {
		stream.writeString("");
		stream.writeString(message);
		stream.writeString(user);
		stream.writeInt(0);
	}

	@Override
	public int size(Bancho bancho) {
		return 2 // empty string
			 + DataUtils.stringLen(message)
			 + DataUtils.stringLen(user)
			 + 4; // integer 0
	}
}
