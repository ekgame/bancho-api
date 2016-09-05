package lt.ekgame.bancho.client.events;

import lt.ekgame.bancho.client.channels.Channel;
import lt.ekgame.bancho.client.channels.User;

public class EventMessage extends Event {
	
	private String message;
	private User sender; 
	private Channel channel; 
	private boolean isAction;
	
	public EventMessage(String rawMessage, User sender, Channel channel) {
		this.message = rawMessage;
		this.sender = sender;
		this.channel = channel;
		if (rawMessage.startsWith("\u0001ACTION")) {
			this.message = rawMessage.substring(8, rawMessage.length()-1);
			this.isAction = true;
		}
	}
	
	public User getSender() {
		return sender;
	}
	
	public String getContent() {
		return message;
	}
	
	public Channel getChannel() {
		return channel;
	}
	
	public boolean isAction() {
		return isAction;
	}
	
	public boolean isPrivate() {
		return sender == channel;
	}
	
	public String toString() {
		String channelPrefix = !isPrivate() ? "["+getChannel().getName()+"] " : "";
		String user = getSender().getUsername() + (isAction() ? " " : ": ");
		return channelPrefix + user + getContent();
	}
}
