package lt.ekgame.bancho.client.events;

import lt.ekgame.bancho.client.channels.User;

public class EventUserDisconnected extends Event {
	
	private User user;
	
	public EventUserDisconnected(User user) {
		this.user = user;
	}
	
	public User getUser() {
		return user;
	}
}
