package lt.ekgame.bancho.api.units;

public enum MatchTeamType {
	
	HEAT_TO_HEAD(0),
	TAG_COOP(1),
	TEAM_VS(2),
	TAG_TEAM_VS(3);
	
	public int id;
	
	MatchTeamType(int id) {
		this.id = id;
	}
	
}
