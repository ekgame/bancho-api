package lt.ekgame.bancho.api.packets.server;

import java.io.IOException;

import lt.ekgame.bancho.api.Bancho;
import lt.ekgame.bancho.api.packets.ByteDataInputStream;
import lt.ekgame.bancho.api.packets.ByteDataOutputStream;
import lt.ekgame.bancho.api.packets.Packet;
import lt.ekgame.bancho.api.units.Beatmap;
import lt.ekgame.bancho.api.utils.DataUtils;
import lt.ekgame.bancho.client.Status;
import lt.ekgame.bancho.client.impl.StatusImpl;

public class PacketUserStats extends Packet {

	public int userId;
	
	// Status object start
	public byte status;
	public String statusText;
	public String beatmapHash;
	public int modFlags;
	public byte playmode;
	public int beatmapId;
	// Status object end
	
	public long rankedScore;
	public float accuracy;
	public int playCount;
	public long totalScore;
	public int rank;
	public short performance;
	
	@Override
	public void read(ByteDataInputStream stream, int length) throws IOException {
		//System.out.printf("[0xOB] ");
		//for (int i = 0; i < length; i++)
		//	System.out.printf("%02x ", stream.readByte());
		//System.out.printf("\n");
		
		userId = stream.readInt();
		
		// unknown object start
		status = stream.readByte(); // user status
		statusText = stream.readString();
		beatmapHash = stream.readString();
		modFlags = stream.getProtocolVersion() <= 10 ? stream.readShort() : stream.readInt();
		playmode = stream.readByte();
		beatmapId = stream.readInt();
		// unknown object end
		
		rankedScore = stream.readLong();
		accuracy = stream.readFloat();
		playCount = stream.readInt();
		totalScore = stream.readLong();
		rank = stream.readInt();
		performance = stream.readShort();
		
		//System.out.printf("[0xOB] i1: %d, b1: %d, s1: \"%s\", s2: \"%s\", modFlags: %d, playmode: %d, i2: %d, l1: %d, f1: %f, i3: %d, l2: %d, i4: %d, sh1: %d\n",
		//		integer1, byte1, string1, string2, modFlags, playmode, integer2, long1, float1, integer3, long2, integer4, short1);
		
	}

	@Override
	public void write(ByteDataOutputStream stream) throws IOException {
		stream.writeInt(userId);
		stream.writeByte(status);
		stream.writeString(statusText);
		stream.writeString(beatmapHash);
		if (stream.getProtocolVersion() <= 10)
			stream.writeShort((short)modFlags);
		else
			stream.writeInt(modFlags);
		stream.writeByte(playmode);
		stream.writeInt(beatmapId);
		
		stream.writeLong(rankedScore);
		stream.writeFloat(accuracy);
		stream.writeInt(playCount);
		stream.writeLong(totalScore);
		stream.writeInt(rank);
		stream.writeShort(performance);
	}

	@Override
	public int size(Bancho bancho) {
		return 4 + 1 + DataUtils.stringLen(statusText) + DataUtils.stringLen(beatmapHash) 
			     + (bancho.getProtocolVersion() <= 10 ? 2 : 4) + 1 + 4 + 8 + 4 + 4 + 8 + 4 + 2;
	}
	
	

}
