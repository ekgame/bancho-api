package lt.ekgame.bancho.api.packets.client;

import java.io.IOException;

import lt.ekgame.bancho.api.Bancho;
import lt.ekgame.bancho.api.packets.ByteDataInputStream;
import lt.ekgame.bancho.api.packets.ByteDataOutputStream;
import lt.ekgame.bancho.api.packets.Packet;
import lt.ekgame.bancho.api.units.Mods;
import lt.ekgame.bancho.api.units.PlayMode;
import lt.ekgame.bancho.api.units.UserStatus;

/**
 * Signals to stop spectating.
 */
public class PacketStopSpectating extends Packet {
	
	@Override
	public void read(ByteDataInputStream stream, int length) throws IOException {
		// just a signal, no actual data
	}

	@Override
	public void write(ByteDataOutputStream stream) throws IOException {
		// just a signal, no actual data
	}

	@Override
	public int size(Bancho bancho) {
		return 0;
	}

}
