package lt.ekgame.bancho.api.packets.client;

import java.io.IOException;

import lt.ekgame.bancho.api.Bancho;
import lt.ekgame.bancho.api.packets.ByteDataInputStream;
import lt.ekgame.bancho.api.packets.ByteDataOutputStream;
import lt.ekgame.bancho.api.packets.Packet;
import lt.ekgame.bancho.api.units.MultiplayerRoom;

/**
 * Sent to the server when a client tries to create a multiplayer room.
 * 
 * Before actually creating a room, the client must send:
 *   a) PacketStatusUpdate
 *   b) PacketSignalMultiplayer
 *   
 * Why the status update? Because Bancho disconnects the client otherwise.
 * I've learned this the hard way.
 */
public class PacketCreateRoom extends Packet {
	
	public MultiplayerRoom room;
	
	public PacketCreateRoom() {}
	
	public PacketCreateRoom(MultiplayerRoom room) {
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
