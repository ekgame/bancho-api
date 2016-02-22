package lt.ekgame.bancho.api.packets.client;

import java.io.IOException;

import lt.ekgame.bancho.api.Bancho;
import lt.ekgame.bancho.api.packets.ByteDataInputStream;
import lt.ekgame.bancho.api.packets.ByteDataOutputStream;
import lt.ekgame.bancho.api.packets.Packet;
import lt.ekgame.bancho.api.utils.DataUtils;

/**
 * Sent to the server when the client tries to join a channel.
 */
public class PacketJoinChannel extends Packet {
	
	public String channel;
	
	public PacketJoinChannel() {}
	
	public PacketJoinChannel(String channel) {
		this.channel = "#" + channel;
	}

	@Override
	public void write(ByteDataOutputStream stream) throws IOException {
		stream.writeString(channel);
	}
	
	@Override
	public void read(ByteDataInputStream stream, int length) throws IOException {
		channel = stream.readString();
	}

	@Override
	public int size(Bancho bancho) {
		return DataUtils.stringLen(channel);
	}
	

}
