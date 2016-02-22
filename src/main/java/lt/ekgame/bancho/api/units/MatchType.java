package lt.ekgame.bancho.api.units;

public enum MatchType {
	
	STANDART(0),
	POWERPLAY(1); // wtf is this?
	
	public int id;
	MatchType(int id) {
		this.id = id;
	}
	
}
