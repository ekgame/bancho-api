package lt.ekgame.bancho.api.packets.server;

import java.io.IOException;

import lt.ekgame.bancho.api.Bancho;
import lt.ekgame.bancho.api.packets.ByteDataInputStream;
import lt.ekgame.bancho.api.packets.ByteDataOutputStream;
import lt.ekgame.bancho.api.packets.Packet;
import lt.ekgame.bancho.api.utils.DataUtils;

public class PacketChat extends Packet {
	
	public String sender;
	public String message;
	public String channel;
	public int userId;
	
	public PacketChat() {}
	
	public PacketChat(String sender, String message, String channel, int userId) {
		this.sender = sender;
		this.message = message;
		this.channel = channel;
		this.userId = userId;
	}

	@Override
	public void read(ByteDataInputStream stream, int length) throws IOException {
		sender = stream.readString();
		message = stream.readString();
		channel = stream.readString();
		if (stream.getProtocolVersion() > 14)
			userId = stream.readInt();
	}

	@Override
	public void write(ByteDataOutputStream stream) throws IOException {
		stream.writeString(sender);
		stream.writeString(message);
		stream.writeString(channel);
		if (stream.getProtocolVersion() > 14)
			stream.writeInt(userId);
	}

	@Override
	public int size(Bancho bancho) {
		int size = DataUtils.stringLen(sender)
				 + DataUtils.stringLen(message)
				 + DataUtils.stringLen(channel);
		if (bancho.getProtocolVersion() > 14)
			size += 4;
		return size;
	}

	
}
