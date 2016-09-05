package lt.ekgame.bancho.client.impl;

import lt.ekgame.bancho.api.units.Beatmap;
import lt.ekgame.bancho.api.units.Mods;
import lt.ekgame.bancho.api.units.Playmode;
import lt.ekgame.bancho.api.units.UserState;
import lt.ekgame.bancho.client.Status;

public class StatusBuilder {
	
	private BanchoClientManager manager;
	
	private Beatmap beatmap;
	private UserState state;
	private Playmode playmode;
	private Mods mods;
	
	public StatusBuilder(BanchoClientManager manager) {
		this.manager = manager;
		
		Status status = manager.getUserStatus();
		beatmap = status.getBeatmap();
		state = status.getUserState();
		playmode = status.getPlaymode();
		mods = status.getMods();
	}
	
	public StatusBuilder setBeatmap(Beatmap beatmap) {
		this.beatmap = beatmap;
		return this;
	}
	
	public StatusBuilder setPlaymode(Playmode playmode) {
		this.playmode = playmode;
		return this;
	}
	
	public StatusBuilder setMods(Mods mods) {
		this.mods = mods;
		return this;
	}
	
	public StatusBuilder setStatus(UserState status) {
		this.state = status;
		return this;
	}
	
	public void send() {
		manager.setUserStatus(new StatusImpl(beatmap, state, playmode, mods));
	}

}
