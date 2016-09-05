package lt.ekgame.bancho.client;

import lt.ekgame.bancho.api.units.Beatmap;
import lt.ekgame.bancho.api.units.Mods;
import lt.ekgame.bancho.api.units.Playmode;
import lt.ekgame.bancho.api.units.UserState;

public interface Status {
	
	public Beatmap getBeatmap();
	
	public UserState getUserState();
	
	public Playmode getPlaymode();
	
	public Mods getMods();
}
