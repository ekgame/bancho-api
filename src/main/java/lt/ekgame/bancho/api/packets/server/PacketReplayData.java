package lt.ekgame.bancho.api.packets.server;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import lt.ekgame.bancho.api.packets.ByteDataInputStream;
import lt.ekgame.bancho.api.packets.Packet;

public class PacketReplayData extends Packet {
	// Weird number that goes into millions seems to increment with every new game
	public int integer; // Some kind of gameplay id?
	public List<Frame> replayFrames = new ArrayList<Frame>();
	public ReplayStatus status;

	@Override
	public void read(ByteDataInputStream stream, int length) throws IOException {
		if (stream.getProtocolVersion() >= 18)
			integer = stream.readInt();
		
		int len = stream.readShort();
		for (int i = 0; i < len; i++)
			replayFrames.add(new Frame(stream));
		
		status = ReplayStatus.values()[stream.readByte()];
	}
	
	public class Frame {
		
		public boolean btnMouseLeft, btnMouseRight, btnKbPrimary, btnKbSecondary, btnSmoke;
		public boolean btnNone;
		public boolean btnPrimary, btnSecondary;
		
		public float cursorX, cursorY;
		public int timestamp; // Relative to the first note in the beatmap.

		public Frame(ByteDataInputStream stream) throws IOException {
			byte btnStates = stream.readByte();
			
			btnKbPrimary = (btnStates & 4) > 0;
			btnKbSecondary = (btnStates & 8) > 0;
			btnMouseLeft = (btnStates & 1) > 0 && !btnKbPrimary;
			btnMouseRight = (btnStates & 2) > 0 && !btnKbSecondary;
			
			btnSmoke = (btnStates & 16) > 0;
			btnNone = (btnStates & 31) == 0;
			btnPrimary = btnMouseLeft || btnKbPrimary;
			btnSecondary = btnMouseRight || btnKbSecondary;
			
			if (stream.readByte() > 0) {
				// do something with right mouse? idk
			} 
			cursorX = stream.readFloat();
			cursorY = stream.readFloat();
			timestamp = stream.readInt();
		}
		
	}
	
	public enum ReplayStatus {
		PLAYING,
		STARTING,
		UNKNOWN_3,
		PASSED,
		FAILED,
		PAUSED,
		UNKNOWN_7,
		SELECTING,
		UNKNOWN_9;
	}
}
