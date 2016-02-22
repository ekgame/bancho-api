package lt.ekgame.bancho.api.packets.server;

import java.io.IOException;

import lt.ekgame.bancho.api.Bancho;
import lt.ekgame.bancho.api.packets.ByteDataInputStream;
import lt.ekgame.bancho.api.packets.ByteDataOutputStream;
import lt.ekgame.bancho.api.packets.Packet;
import lt.ekgame.bancho.api.utils.DataUtils;

/**
 * Received constantly, but values don't seem to change.
 * Very peculiar thing.
 */
public class PacketUnknown0B extends Packet {

	int integer1;
	
	// unknown object start
	byte byte1; // status?
	String string1;
	String string2;
	int modFlags; // may be read as short or integer
	byte playmode;
	int integer2;
	// unknown object end
	
	long long1;
	float float1;
	int integer3;
	long long2;
	int integer4;
	short short1;
	
	@Override
	public void read(ByteDataInputStream stream, int length) throws IOException {
		//System.out.printf("[0xOB] ");
		//for (int i = 0; i < length; i++)
		//	System.out.printf("%02x ", stream.readByte());
		//System.out.printf("\n");
		
		integer1 = stream.readInt();
		
		// unknown object start
		byte1 = stream.readByte(); // user status
		string1 = stream.readString();
		string2 = stream.readString();
		modFlags = stream.getProtocolVersion() <= 10 ? stream.readShort() : stream.readInt();
		playmode = stream.readByte();
		integer2 = stream.readInt();
		// unknown object end
		
		long1 = stream.readLong();
		float1 = stream.readFloat();
		integer3 = stream.readInt();
		long2 = stream.readLong();
		integer4 = stream.readInt();
		short1 = stream.readShort();
		
		//System.out.printf("[0xOB] i1: %d, b1: %d, s1: \"%s\", s2: \"%s\", modFlags: %d, playmode: %d, i2: %d, l1: %d, f1: %f, i3: %d, l2: %d, i4: %d, sh1: %d\n",
		//		integer1, byte1, string1, string2, modFlags, playmode, integer2, long1, float1, integer3, long2, integer4, short1);
		
	}

	@Override
	public void write(ByteDataOutputStream stream) throws IOException {
		stream.writeInt(integer1);
		stream.writeByte(byte1);
		stream.writeString(string1);
		stream.writeString(string2);
		if (stream.getProtocolVersion() <= 10)
			stream.writeShort((short)modFlags);
		else
			stream.writeInt(modFlags);
		stream.writeByte(playmode);
		stream.writeInt(integer2);
		
		stream.writeLong(long1);
		stream.writeFloat(float1);
		stream.writeInt(integer3);
		stream.writeLong(long2);
		stream.writeInt(integer4);
		stream.writeShort(short1);
	}

	@Override
	public int size(Bancho bancho) {
		return 4 + 1 + DataUtils.stringLen(string1) + DataUtils.stringLen(string2) 
			     + (bancho.getProtocolVersion() <= 10 ? 2 : 4) + 1 + 4 + 8 + 4 + 4 + 8 + 4 + 2;
	}
	
	

}
