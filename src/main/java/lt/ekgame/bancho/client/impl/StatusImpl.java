package lt.ekgame.bancho.client.impl;

import lt.ekgame.bancho.api.units.Beatmap;
import lt.ekgame.bancho.api.units.Mods;
import lt.ekgame.bancho.api.units.Playmode;
import lt.ekgame.bancho.api.units.UserState;
import lt.ekgame.bancho.client.Status;

public class StatusImpl implements Status {
	
	public static final Status DEFAULT = new StatusImpl(Beatmap.DEFAULT, UserState.IDLE, Playmode.OSU, new Mods(0));
	
	private Beatmap beatmap;
	private UserState state;
	private Playmode playmode;
	private Mods mods;
	
	public StatusImpl(Beatmap beatmap, UserState state, Playmode playmode, Mods mods) {
		this.beatmap = beatmap;
		this.state = state;
		this.playmode = playmode;
		this.mods = mods;
	}

	@Override
	public Beatmap getBeatmap() {
		return beatmap;
	}

	@Override
	public UserState getUserState() {
		return state;
	}

	@Override
	public Playmode getPlaymode() {
		return playmode;
	}

	@Override
	public Mods getMods() {
		return mods;
	}
}
