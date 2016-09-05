package lt.ekgame.bancho.client.channels.impl;

import lt.ekgame.bancho.api.packets.client.PacketSendMessageUser;
import lt.ekgame.bancho.client.channels.ChannelManager;
import lt.ekgame.bancho.client.channels.User;
import lt.ekgame.bancho.client.events.EventUserConnected;
import lt.ekgame.bancho.client.events.EventUserDisconnected;

public class UserImpl implements User {
	
	private ChannelManager manager;
	
	private int userId;
	private String username;
	private int userFlags;
	
	private boolean isOnline = false;
	
	public UserImpl(ChannelManager manager, int userId) {
		this.manager = manager;
		this.userId = userId;
	}
	
	public UserImpl(ChannelManager manager, int userId, String username, int userFlags) {
		this(manager, userId);
		this.username = username;
		this.userFlags = userFlags;
	}
	
	@Override
	public void sendMessage(String message) {
		manager.sendMessagePacket(new PacketSendMessageUser(message, username));
	}
	
	@Override
	public String getName() {
		return username;
	}
	
	public void updatePresence(String username, int userFlags) {
		this.username = username;
		this.userFlags = userFlags;
		if (!isOnline) setOnline(true);
	}
	
	public void setOnline(boolean online) {
		this.isOnline = online;
		manager.getBancho().dispatchEvent(
			isOnline ? new EventUserConnected(this) : new EventUserDisconnected(this)
		);
	}
	
	@Override
	public boolean isOnline() {
		return isOnline;
	}

	@Override
	public int getUserId() {
		return userId;
	}

	@Override
	public String getUsername() {
		return username;
	}

	@Override
	public boolean isFriend() {
		return (userFlags & (1 << 3)) > 0;
	}

	@Override
	public boolean isAdmin() {
		return (userFlags & (1 << 1)) > 0;
	}

	@Override
	public boolean isOwner() {
		return (userFlags & (1 << 4)) > 0;
	}

	@Override
	public boolean isSupporter() {
		return (userFlags & (1 << 2)) > 0;
	}
}
