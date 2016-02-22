package lt.ekgame.bancho.api.units;

public enum MatchScoringType {
	
	SCORE(0),
    ACCURACY(1),
    COMBO(2),
    SCOREV2(3);
	
	public int id;
	
	MatchScoringType(int id) {
		this.id = id;
	}

}
