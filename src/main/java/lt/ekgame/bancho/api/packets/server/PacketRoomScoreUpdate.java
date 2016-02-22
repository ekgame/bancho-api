package lt.ekgame.bancho.api.packets.server;

import java.io.IOException;

import lt.ekgame.bancho.api.Bancho;
import lt.ekgame.bancho.api.packets.ByteDataInputStream;
import lt.ekgame.bancho.api.packets.ByteDataOutputStream;
import lt.ekgame.bancho.api.packets.Packet;

/**
 * If a client is in a multiplayer room which is in progress,
 * it will receive other players' score updates.
 */
public class PacketRoomScoreUpdate extends Packet {
	
	public int integer1;
	public byte byte1;
	public short short1;
	public short short2;
	public short short3;
	public short short4;
	public short short5;
	public short short6;
	public int integer2;
	public short short7;
	public short short8;
	public boolean boolean1;
	public int byte2;
	public int byte3;
	
	public boolean boolean2;

	@Override
	public void read(ByteDataInputStream stream, int length) throws IOException {
		integer1 = stream.readInt();
		byte1 = stream.readByte();
		short1 = stream.readShort();
		short2 = stream.readShort();
		short3 = stream.readShort();
		short4 = stream.readShort();
		short5 = stream.readShort();
		short6 = stream.readShort();
		integer2 = stream.readInt();
		short7 = stream.readShort();
		short8 = stream.readShort();
		boolean1 = stream.readByte() > 0;
		byte2 = stream.readByte() & 0xff;
		byte3 = stream.readByte() & 0xff;
		
		if ((byte2 & 0xFF) == 254) {
			byte2 = 0;
			boolean2 = false;
		}
		else boolean2 = true;
		
		//System.out.printf("%d %d %d %d %d %d %d %d %d %d %d %s %d %d %s\n", 
		//		integer1, byte1, short1, short2, short3, short4, short5, short6, integer2, short7, short8, boolean1?"t":"f", byte2, byte3, boolean2?"t":"f");
	}

	@Override
	public void write(ByteDataOutputStream stream) throws IOException {
		stream.writeInt(integer1);
		stream.writeByte(byte1);
		stream.writeShort(short1);
		stream.writeShort(short2);
		stream.writeShort(short3);
		stream.writeShort(short4);
		stream.writeShort(short5);
		stream.writeShort(short6);
		stream.writeInt(integer2);
		stream.writeShort(short7);
		stream.writeShort(short8);
		stream.writeByte((byte) (boolean1 ? 1 : 0));
		stream.writeByte((byte) byte2);
		stream.writeByte((byte) byte3);
	}

	@Override
	public int size(Bancho bancho) {
		return 4 + 1 + 2*8 + 4 + 3; 
	}
	
	

}
