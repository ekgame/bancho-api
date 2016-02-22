package lt.ekgame.bancho.api.packets.server;

import java.io.IOException;

import lt.ekgame.bancho.api.Bancho;
import lt.ekgame.bancho.api.packets.ByteDataInputStream;
import lt.ekgame.bancho.api.packets.ByteDataOutputStream;
import lt.ekgame.bancho.api.packets.Packet;
import lt.ekgame.bancho.api.utils.DataUtils;

/**
 * Sent to a client when they leave a channel
 */
public class PacketChannelLeft extends Packet {
	
	public String channel;
	
	public PacketChannelLeft() {}
	
	public PacketChannelLeft(String channel) {
		this.channel = channel;
	}

	@Override
	public void read(ByteDataInputStream stream, int length) throws IOException {
		channel = stream.readString();
	}

	@Override
	public void write(ByteDataOutputStream stream) throws IOException {
		stream.writeString(channel);
	}

	@Override
	public int size(Bancho bancho) {
		return DataUtils.stringLen(channel);
	}

}
