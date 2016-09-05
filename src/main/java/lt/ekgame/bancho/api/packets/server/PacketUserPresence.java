package lt.ekgame.bancho.api.packets.server;

import java.io.IOException;

import lt.ekgame.bancho.api.Bancho;
import lt.ekgame.bancho.api.packets.ByteDataInputStream;
import lt.ekgame.bancho.api.packets.ByteDataOutputStream;
import lt.ekgame.bancho.api.packets.Packet;
import lt.ekgame.bancho.api.units.Playmode;
import lt.ekgame.bancho.api.utils.DataUtils;

/**
 * Provides general information about a particular user.
 * Probably. 
 */
public class PacketUserPresence extends Packet {
	
	public boolean bool;
	
	public int userId;
	public String username;
	public int timezone;
	public byte byte1;
	public float longitude;
	public float latitude;
	public int rank;
	
	public int userFlags;
	public Playmode playmode;
	
	public PacketUserPresence() {}
	
	public PacketUserPresence(boolean bool, int userId, String username, int integer2, byte byte1, float float1, float float2, int rank, int flags, Playmode playmode) {
		this.bool = bool;
		this.userId = userId;
		this.username = username;
		this.timezone = integer2;
		this.byte1 = byte1;
		this.longitude = float1;
		this.latitude = float2;
		this.rank = rank;
		
		this.userFlags = flags;
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
		timezone = stream.readByte() - 24;
		byte1 = stream.readByte();
		byte num = stream.readByte();
		userFlags = num & 31;
		playmode = Playmode.values()[(num >> 5) & 7]; 
		longitude = stream.readFloat();
		latitude = stream.readFloat();
		rank = stream.readInt();
		//System.out.printf("[0x5C UI] id: %d, user: %s, flags: %05s, playmode: %d, rank: %d\n",
		//		userId, username, Integer.toBinaryString(flags), playmode, rank);
	}

	@Override
	public void write(ByteDataOutputStream stream) throws IOException {
		stream.writeInt(bool ? userId : -userId);
		stream.writeString(username);
		stream.writeByte((byte)(timezone + 24));
		stream.writeByte(byte1);
		stream.writeByte((byte) (userFlags | (playmode.id << 5)));
		stream.writeFloat(longitude);
		stream.writeFloat(latitude);
		stream.writeInt(rank);
	}

	@Override
	public int size(Bancho bancho) {
		return 4*4 + 3 + DataUtils.stringLen(username);
	}
	
	

}
