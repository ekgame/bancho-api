package lt.ekgame.bancho.api.packets.client;

import java.io.IOException;

import lt.ekgame.bancho.api.Bancho;
import lt.ekgame.bancho.api.packets.ByteDataInputStream;
import lt.ekgame.bancho.api.packets.ByteDataOutputStream;
import lt.ekgame.bancho.api.packets.Packet;
import lt.ekgame.bancho.api.units.Beatmap;
import lt.ekgame.bancho.api.units.Mods;
import lt.ekgame.bancho.api.units.Playmode;
import lt.ekgame.bancho.api.units.UserState;
import lt.ekgame.bancho.api.utils.DataUtils;

/**
 * Sends an update about what you're doing.
 */
public class PacketStatusUpdate extends Packet {
	
	public UserState status;
	public String beatmapName, beatmapChecksum;
	public Mods mods;
	public Playmode playmode;
	public int integer;
	
	public PacketStatusUpdate() {}
	
	public PacketStatusUpdate(UserState status, String beatmapName, String beatmapChecksum, Mods mods, Playmode playmode, int integer) {
		this.status = status;
		this.beatmapName = beatmapName;
		this.beatmapChecksum = beatmapChecksum;
		this.mods = mods;
		this.playmode = playmode;
		this.integer = integer;
	}
	
	public PacketStatusUpdate(UserState status, Beatmap beatmap, Mods mods, Playmode playmode) {
		this(status, beatmap.getName(), beatmap.getChecksum(), mods, playmode, 0);
	}

	@Override
	public void read(ByteDataInputStream stream, int length) throws IOException {
		status = UserState.values()[stream.readByte()];
		beatmapName = stream.readString();
		beatmapChecksum = stream.readString();
		mods = new Mods(stream.readInt());
		playmode = Playmode.values()[stream.readByte()];
		integer = stream.readInt();
	}

	@Override
	public void write(ByteDataOutputStream stream) throws IOException {
		stream.writeByte((byte) status.id);
		stream.writeString(beatmapName);
		stream.writeString(beatmapChecksum);
		stream.writeInt(mods.getFlags());
		stream.writeByte((byte) playmode.id);
		stream.writeInt(integer);
	}

	@Override
	public int size(Bancho bancho) {
		return 1 + DataUtils.stringLen(beatmapName) + DataUtils.stringLen(beatmapChecksum) + 4*2 + 1;
	}

}
