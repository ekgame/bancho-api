package lt.ekgame.bancho.api.packets.server;

import java.io.IOException;

import lt.ekgame.bancho.api.Bancho;
import lt.ekgame.bancho.api.packets.ByteDataInputStream;
import lt.ekgame.bancho.api.packets.ByteDataOutputStream;
import lt.ekgame.bancho.api.packets.Packet;
import lt.ekgame.bancho.api.units.MultiplayerRoom;

public class PacketRoomJoined extends Packet {

	public MultiplayerRoom room;
	
	public PacketRoomJoined() {}
	
	public PacketRoomJoined(MultiplayerRoom room) {
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
