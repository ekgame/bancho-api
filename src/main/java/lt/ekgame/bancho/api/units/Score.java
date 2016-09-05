package lt.ekgame.bancho.api.units;

import java.io.IOException;

import lt.ekgame.bancho.api.packets.ByteDataInputStream;

public class Score {
	
	public int timestamp;
	public byte id;
	public int num300, num100, num50, numGeki, numKatu, numMiss; // unsigned short
	public int score;
	public int maxCombo, currentCombo; // unsigned short
	public boolean isPerfect;
	public short health;
	public byte tag;
	public boolean isScoreV2;
	public double comboPortion, bonusPortion;
	
	public boolean pass;
	
	public Score(ByteDataInputStream stream) throws IOException {
		timestamp = stream.readInt();
		id = stream.readByte();
		num300  = stream.readUShort();
		num100  = stream.readUShort();
		num50   = stream.readUShort();
		numGeki = stream.readUShort();
		numKatu = stream.readUShort();
		numMiss = stream.readUShort();
		score = stream.readInt();
		maxCombo = stream.readUShort();
		currentCombo = stream.readUShort();
		isPerfect = stream.readBoolean();
		health = stream.readUByte();
		tag = stream.readByte();
		isScoreV2 = stream.readBoolean();
		if (isScoreV2) {
			comboPortion = stream.readDouble();
			bonusPortion = stream.readDouble();
		}
		
		if (health == 254) {
			health = 0;
			pass = false;
		}
		else {
			pass = true;
		}
	}
}
