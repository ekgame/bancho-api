package lt.ekgame.bancho.api.packets.server;

import java.io.IOException;

import lt.ekgame.bancho.api.Bancho;
import lt.ekgame.bancho.api.packets.ByteDataInputStream;
import lt.ekgame.bancho.api.packets.ByteDataOutputStream;
import lt.ekgame.bancho.api.packets.Packet;
import lt.ekgame.bancho.api.utils.DataUtils;

/**
 * Strangely, the client doesn't actually handle this packet, but the structure
 * is easy enough to still understand it.
 */
public class PacketChannelInfo extends Packet {
	
	public String channel;
	public String description;
	public int integer;
	
	public PacketChannelInfo() {}
	
	public PacketChannelInfo(String channel, String description, int integer) {
		this.channel = channel;
		this.description = description;
		this.integer = integer;
	}

	@Override
	public void read(ByteDataInputStream stream, int length) throws IOException {
		channel = stream.readString();
		description = stream.readString();
		integer = stream.readInt();
	}

	@Override
	public void write(ByteDataOutputStream stream) throws IOException {
		stream.writeString(channel);
		stream.writeString(description);
		stream.writeInt(integer);
	}

	@Override
	public int size(Bancho bancho) {
		return DataUtils.stringLen(channel) + DataUtils.stringLen(description) + 4;
	}

	
}
