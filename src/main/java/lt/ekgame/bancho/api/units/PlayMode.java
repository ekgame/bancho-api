package lt.ekgame.bancho.api.units;

public enum PlayMode {
	
	OSU(0),
	TAIKO(1),
	CTB(2),
	MANIA(3);
	
	public int id;
	
	PlayMode(int id) {
		this.id = id;
	}
}
