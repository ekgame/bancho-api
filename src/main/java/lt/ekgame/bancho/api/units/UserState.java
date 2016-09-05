package lt.ekgame.bancho.api.units;

public enum UserState {

	IDLE(0),
	AFK(1),
	PLAYING(2),
	EDITING(3),
	MODDING(4),
	MULTIPLAYER(5),
	WATCHING(6),
	UNKNOWN(7), // Literally, used when the game doesn't know what it's doing.
	TESTING(8),
	SUBMITTING(9),
	PAUSED(10),
	LOBBY(11),
	MULTIPLAYING(12),
	OSU_DIRECT(13);
	
	public int id;
	UserState(int id) {
		this.id = id;
	}
}
