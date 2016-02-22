package lt.ekgame.bancho.api.packets.server;

import java.io.IOException;

import lt.ekgame.bancho.api.Bancho;
import lt.ekgame.bancho.api.packets.ByteDataInputStream;
import lt.ekgame.bancho.api.packets.ByteDataOutputStream;
import lt.ekgame.bancho.api.packets.Packet;
import lt.ekgame.bancho.api.units.MultiplayerRoom;

/**
 * Might also signal a room being created.
 * 
 * This packet contains the latest info about any room that's currently active.
 * May even be the room you're in. What a waste of bandwidth. And my time. 
 * While analyzing rooms, I was thinking "why isn't the server sending me anything about this room's changes?"
 * And I filtered this packet out, because it was received so frequently whether or not I was in a room.
 * I thought it was irrelavent. Turns out that no, you get every single update of every room. 
 * Because why not..?
 */
public class PacketRoomUpdate extends Packet {

	public MultiplayerRoom room;
	
	public PacketRoomUpdate() {}
	
	public PacketRoomUpdate(MultiplayerRoom room) {
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
