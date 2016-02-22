package lt.ekgame.bancho.api.packets.client;

import java.io.IOException;

import lt.ekgame.bancho.api.Bancho;
import lt.ekgame.bancho.api.packets.ByteDataInputStream;
import lt.ekgame.bancho.api.packets.ByteDataOutputStream;
import lt.ekgame.bancho.api.packets.Packet;
import lt.ekgame.bancho.api.units.MultiplayerRoom;

/**
 * Updates a room with new data.
 * Client must have a host to take effect.
 */
public class PacketUpdateRoom extends Packet {

	public MultiplayerRoom room;
	
	public PacketUpdateRoom() {}
	
	public PacketUpdateRoom(MultiplayerRoom room) {
		this.room = room;
	}

	@Override
	public void read(ByteDataInputStream stream, int length) throws IOException {
		room = new MultiplayerRoom();
		room.read(stream, length);
	}

	@Override
	public void write(ByteDataOutputStream stream) throws IOException {
		room.write(stream);
	}

	@Override
	public int size(Bancho bancho) {
		return room.size(bancho);
	}
}
