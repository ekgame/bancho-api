package lt.ekgame.bancho.client.channels.impl;

import lt.ekgame.bancho.api.packets.client.PacketSendMessageChannel;
import lt.ekgame.bancho.client.channels.ChannelManager;
import lt.ekgame.bancho.client.channels.ChannelPublic;

public class ChannelPublicImpl implements ChannelPublic {
	
	private String name, description;
	private ChannelManager manager;
	private int userCount;
	
	public ChannelPublicImpl(String name, ChannelManager manager) {
		this.name = name.toLowerCase();
	}
	
	public void setUserCount(int userCount) {
		this.userCount = userCount;
	}
	
	public void setDescription(String description) {
		this.description = description;
	}

	@Override
	public void sendMessage(String message) {
		manager.sendMessagePacket(new PacketSendMessageChannel(message, name));
	}

	@Override
	public boolean isContextual() {
		return name.equals("#multiplayer") || name.equals("#spectator");
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public int getUserCount() {
		return userCount;
	}

	@Override
	public String getDescription() {
		return description;
	}
}
