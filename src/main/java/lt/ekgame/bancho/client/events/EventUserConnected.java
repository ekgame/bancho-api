package lt.ekgame.bancho.client.events;

import lt.ekgame.bancho.client.channels.User;

public class EventUserConnected extends Event {
	
	private User user;
	
	public EventUserConnected(User user) {
		this.user = user;
	}
	
	public User getUser() {
		return user;
	}

}
