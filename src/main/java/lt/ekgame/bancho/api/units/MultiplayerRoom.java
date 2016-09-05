package lt.ekgame.bancho.api.units;

import java.io.IOException;

import lt.ekgame.bancho.api.Bancho;
import lt.ekgame.bancho.api.packets.ByteDataInputStream;
import lt.ekgame.bancho.api.packets.ByteDataOutputStream;
import lt.ekgame.bancho.api.packets.Packet;
import lt.ekgame.bancho.api.utils.DataUtils;
import lt.ekgame.bancho.client.impl.BanchoClientManager;

public class MultiplayerRoom extends Packet {

	public short matchId = 0;
	public boolean inProgress = false; 
	
	public MatchType matchType;
	public MatchScoringType scoringType;
	public MatchTeamType teamType;
	public Playmode playmode;
	public String roomName, roomPassword;
	public int openSlots;
	public String beatmapName, beatmapChecksum;
	public int beatmapId;
	public Mods mods;
	public int hostId;
	public MatchSpecialMode specialMode;
	public int seed;
	
	public static final int MAX_SLOTS = 16; // may actually be 8 on older bancho protocol
	
	public byte[] slotStatus = new byte[MAX_SLOTS];
	public byte[] slotTeam = new byte[MAX_SLOTS];
	public int[] slotId = new int[MAX_SLOTS];
	public int[] slotMods = new int[MAX_SLOTS];
	
	public MultiplayerRoom() {}
	
	public MultiplayerRoom(String roomName, String roomPassword, int openSlots, BanchoClientManager manager) {
		this(roomName, roomPassword, openSlots, manager.getUserStatus().getBeatmap(), manager.getUserStatus().getMods(), manager.getUserId());
	}
	
	public MultiplayerRoom(String roomName, String roomPassword, int openSlots, Beatmap beatmap, Mods mods, int hostId) {
		this(MatchType.STANDART, MatchScoringType.SCORE, MatchTeamType.HEAT_TO_HEAD, Playmode.OSU, roomName, 
			roomPassword, openSlots, beatmap.getName(), beatmap.getChecksum(), beatmap.getId(), mods, hostId,
			MatchSpecialMode.NONE, 0);
	}
	
	public MultiplayerRoom(MatchType matchType, MatchScoringType scoringType, MatchTeamType teamType,
			Playmode playmode, MatchSpecialMode specialMode, String roomName, String roomPassword, 
			int openSlots, Beatmap beatmap, Mods mods, int hostId) {
		this(matchType, scoringType, teamType, playmode, roomName, roomPassword, openSlots, 
			beatmap.getName(), beatmap.getChecksum(), beatmap.getId(), mods, hostId, specialMode, 0);
	}
	
	public MultiplayerRoom(MatchType matchType, MatchScoringType scoringType, MatchTeamType teamType, Playmode playmode,
			String roomName, String roomPassword, int openSlots, String beatmapName, String beatmapChecksum, int beatmapId,
			Mods mods, int hostId, MatchSpecialMode specialMode, int seed) {
		this.matchType = matchType;
		this.scoringType = scoringType;
		this.teamType = teamType;
		this.playmode = playmode;
		this.roomName = roomName;
		this.roomPassword = roomPassword;
		this.openSlots = openSlots;
		this.beatmapName = beatmapName;
		this.beatmapChecksum = beatmapChecksum;
		this.beatmapId = beatmapId;
		this.mods = mods;
		this.hostId = hostId;
		this.specialMode = specialMode;
		this.seed = seed;
		
		for (int i = 0; i < MAX_SLOTS; i++) {
			slotStatus[i] = (byte) (i < openSlots ? 1 : 2);
			slotId[i] = -1;
		}
		
		if (roomName.length() > 50)
			roomName = roomName.substring(0, 50);
	}
	
	public void setBeatmap(Beatmap beatmap) {
		this.beatmapName = beatmap.getName();
		this.beatmapChecksum = beatmap.getChecksum();
		this.beatmapId = beatmap.getId();
	}

	@Override
	public void write(ByteDataOutputStream stream) throws IOException {
		stream.writeShort(matchId);
		stream.writeByte((byte) (inProgress ? 1 : 0));
		stream.writeByte((byte) matchType.id);
		stream.writeInt(mods.getFlags());
		
		stream.writeString(roomName);
		stream.writeString(roomPassword);
		stream.writeString(beatmapName);
		stream.writeInt(beatmapId);
		stream.writeString(beatmapChecksum);
		
		for (int i = 0; i < MAX_SLOTS; i++)
			stream.writeByte(slotStatus[i]);
		
		for (int i = 0; i < MAX_SLOTS; i++)
			stream.writeByte(slotTeam[i]);
		
		for (int i = 0; i < MAX_SLOTS; i++)
			if ((slotStatus[i] & 0x7C) > 0) // 0x7C = 0b01111100
				stream.writeInt(this.slotId[i]);
		
		stream.writeInt(hostId);
		stream.writeByte((byte) playmode.id);
		stream.writeByte((byte) scoringType.id);
		stream.writeByte((byte) teamType.id);
		stream.writeByte((byte) specialMode.id);
		
		if (specialMode == MatchSpecialMode.FREE_MOD)
			for (int i = 0; i < MAX_SLOTS; i++)
				stream.writeInt(slotMods[i]);
		
		stream.writeInt(seed);
	}

	@Override
	public void read(ByteDataInputStream stream, int length) throws IOException {
		matchId = stream.readShort();
		inProgress = stream.readByte() > 0;
		matchType = MatchType.values()[stream.readByte()];
		mods = new Mods(stream.readInt());
		
		roomName = stream.readString();
		roomPassword = stream.readString();
		beatmapName = stream.readString();
		beatmapId = stream.readInt();
		beatmapChecksum = stream.readString();
		
		for (int i = 0; i < MAX_SLOTS; i++)
			slotStatus[i] = stream.readByte();
		
		for (int i = 0; i < MAX_SLOTS; i++)
			slotTeam[i] = stream.readByte();
		
		for (int i = 0; i < MAX_SLOTS; i++)
			if ((slotStatus[i] & 0x7C) > 0)
				slotId[i] = stream.readInt();
		
		hostId = stream.readInt();
		playmode = Playmode.values()[stream.readByte()];
		scoringType = MatchScoringType.values()[stream.readByte()];
		teamType = MatchTeamType.values()[stream.readByte()];
		specialMode = MatchSpecialMode.values()[stream.readByte()];
		
		if (specialMode == MatchSpecialMode.FREE_MOD)
			for (int i = 0; i < MAX_SLOTS; i++)
				slotMods[i] = stream.readInt();
		
		seed = stream.readInt();
	}

	@Override
	public int size(Bancho bancho) {
		int size = 2 + 1 + 1 + 4
			+ DataUtils.stringLen(roomName)
			+ DataUtils.stringLen(roomPassword)
			+ DataUtils.stringLen(beatmapName)
			+ DataUtils.stringLen(beatmapChecksum)
			+ 4 + MAX_SLOTS*2 + 4 + 4 + 4;
		
		if (specialMode == MatchSpecialMode.FREE_MOD)
			size += MAX_SLOTS*4;
		
		for (int i = 0; i < MAX_SLOTS; i++)
			if ((slotStatus[i] & 0x7C) > 0)
				size+=4;
		
		return size;
	}
	
	public String toString() {
		String str = String.format("MP ROOM \"%s\", \"%s\" (%d)\n",
			roomName, roomPassword, matchId);
		str += String.format("%d %s\n", mods.getFlags(), matchType);
		for (int i = 0; i < MAX_SLOTS; i++)
			str+= String.format("%02x ", slotStatus[i]);
		str += "\n";
		
		for (int i = 0; i < MAX_SLOTS; i++)
			str+= String.format("%02x ", slotTeam[i]);
		str += "\n";
		
		for (int i = 0; i < MAX_SLOTS; i++)
			str+= String.format("%02x ", slotId[i]);
		str += "\n";
		return str;
	}
	
	public Beatmap getBeatmap() {
		return new Beatmap(beatmapName, beatmapChecksum, beatmapId);
	}
}
