package lt.ekgame.bancho.api.packets.server;

import java.io.IOException;

import lt.ekgame.bancho.api.Bancho;
import lt.ekgame.bancho.api.packets.ByteDataInputStream;
import lt.ekgame.bancho.api.packets.ByteDataOutputStream;
import lt.ekgame.bancho.api.packets.Packet;
import lt.ekgame.bancho.api.units.PlayMode;
import lt.ekgame.bancho.api.utils.DataUtils;

/**
 * Provides general information about a particular user.
 * Probably. 
 */
public class PacketUserInfo extends Packet {
	
	boolean bool;
	
	int userId;
	String username;
	int integer2;
	byte byte1;
	float float1; //  These floats don't really make any sense. You would assume they would
	float float2; //  something useful such as PP or overall accuracy, but no.
	int rank;
	
	int flags;
	PlayMode playmode;
	
	public PacketUserInfo() {}
	
	public PacketUserInfo(boolean bool, int userId, String username, int integer2, byte byte1, float float1, float float2, int rank, int flags, PlayMode playmode) {
		this.bool = bool;
		this.userId = userId;
		this.username = username;
		this.integer2 = integer2;
		this.byte1 = byte1;
		this.float1 = float1;
		this.float2 = float2;
		this.rank = rank;
		
		this.flags = flags;
		this.playmode = playmode;
	}

	@Override
	public void read(ByteDataInputStream stream, int length) throws IOException {
		userId = stream.readInt();
		if (userId < 0)
			userId = -userId;
		else
			bool = true;
		
		username = stream.readString();
		integer2 = stream.readByte() - 24; // time difference?
		byte1 = stream.readByte();
		byte num = stream.readByte();
		flags = num & 31;
		playmode = PlayMode.values()[(num >> 5) & 7]; 
		float1 = stream.readFloat();
		float2 = stream.readFloat();
		rank = stream.readInt();
		//System.out.printf("[0x5C UI] id: %d, user: %s, flags: %05s, playmode: %d, rank: %d\n",
		//		userId, username, Integer.toBinaryString(flags), playmode, rank);
	}

	@Override
	public void write(ByteDataOutputStream stream) throws IOException {
		stream.writeInt(bool ? userId : -userId);
		stream.writeString(username);
		stream.writeByte((byte)(integer2 + 24));
		stream.writeByte(byte1);
		stream.writeByte((byte) (flags | (playmode.id << 5)));
		stream.writeFloat(float1);
		stream.writeFloat(float2);
		stream.writeInt(rank);
	}

	@Override
	public int size(Bancho bancho) {
		return 4*4 + 3 + DataUtils.stringLen(username);
	}
	
	

}
