package lt.ekgame.bancho.api.packets.server;

import java.io.IOException;

import lt.ekgame.bancho.api.packets.ByteDataInputStream;
import lt.ekgame.bancho.api.packets.Packet;

public class PacketUserLeft extends Packet {
	
	public int userId;
	public LeftState state;
	
	@Override
	public void read(ByteDataInputStream stream, int length) throws IOException {
		userId = stream.readInt();
		state = LeftState.values()[stream.readByte()];
		//System.out.printf("[0xOC] i: %d, b: %d\n", integer1, byte1);
	}
	
	public enum LeftState {
		GONE,
		OSU_REMAINING,
		IRC_REMAINING
	}
}
