package lt.ekgame.bancho.api.packets.client;

import java.io.IOException;

import lt.ekgame.bancho.api.Bancho;
import lt.ekgame.bancho.api.packets.ByteDataInputStream;
import lt.ekgame.bancho.api.packets.ByteDataOutputStream;
import lt.ekgame.bancho.api.packets.Packet;
import lt.ekgame.bancho.api.utils.DataUtils;

/**
 * Sends a message to a channel. 
 * To send message to a user use PacketSendMessageUser.
 */
public class PacketSendMessageChannel extends Packet {
	
	public String message, channel;
	
	public PacketSendMessageChannel() {}
	
	public PacketSendMessageChannel(String message, String channel) {
		this.message = message;
		this.channel = channel;
	}

	@Override
	public void read(ByteDataInputStream stream, int length) throws IOException {
		stream.readString();
		message = stream.readString();
		channel = stream.readString();
		stream.readInt();
	}

	@Override
	public void write(ByteDataOutputStream stream) throws IOException {
		stream.writeString("");
		stream.writeString(message);
		stream.writeString(channel);
		stream.writeInt(0);
	}

	@Override
	public int size(Bancho bancho) {
		return 2 // empty string
			 + DataUtils.stringLen(message)
			 + DataUtils.stringLen(channel)
			 + 4; // integer 0
	}
}
