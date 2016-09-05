package lt.ekgame.bancho.api.packets.server;

import java.io.IOException;

import lt.ekgame.bancho.api.Bancho;
import lt.ekgame.bancho.api.packets.ByteDataInputStream;
import lt.ekgame.bancho.api.packets.ByteDataOutputStream;
import lt.ekgame.bancho.api.packets.Packet;

/**
 * Seems to be information about your silence.
 */
public class PacketSilenceInfo extends Packet {
	
	public int silenceLength;
	
	public PacketSilenceInfo() {}
	
	public PacketSilenceInfo(int timeDifference) {
		this.silenceLength = timeDifference;
	}

	@Override
	public void read(ByteDataInputStream stream, int length) throws IOException {
		silenceLength = stream.readInt();
	}

	@Override
	public void write(ByteDataOutputStream stream) throws IOException {
		stream.writeInt(silenceLength);
	}

	@Override
	public int size(Bancho bancho) {
		return 4;
	}
	
	
	
}
