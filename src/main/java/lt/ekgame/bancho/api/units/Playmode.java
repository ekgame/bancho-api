package lt.ekgame.bancho.api.units;

public enum Playmode {
	
	OSU(0),
	TAIKO(1),
	CTB(2),
	MANIA(3);
	
	public int id;
	
	Playmode(int id) {
		this.id = id;
	}
}
