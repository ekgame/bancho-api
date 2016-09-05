package lt.ekgame.bancho.client.channels.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import lt.ekgame.bancho.api.packets.Packet;
import lt.ekgame.bancho.api.packets.client.PacketRequestPresence;
import lt.ekgame.bancho.api.packets.client.PacketRequestPresenceAll;
import lt.ekgame.bancho.api.packets.server.PacketChannelInfo;
import lt.ekgame.bancho.api.packets.server.PacketChat;
import lt.ekgame.bancho.api.packets.server.PacketReceivingFinished;
import lt.ekgame.bancho.api.packets.server.PacketUserPresence;
import lt.ekgame.bancho.api.packets.server.PacketUsersOnline;
import lt.ekgame.bancho.api.packets.server.PacketUserJoined;
import lt.ekgame.bancho.api.packets.server.PacketUserLeft;
import lt.ekgame.bancho.api.packets.server.PacketUserStats;
import lt.ekgame.bancho.client.BanchoClient;
import lt.ekgame.bancho.client.PacketHandler;
import lt.ekgame.bancho.client.RateLimiter;
import lt.ekgame.bancho.client.channels.Channel;
import lt.ekgame.bancho.client.channels.ChannelManager;
import lt.ekgame.bancho.client.channels.ChannelPublic;
import lt.ekgame.bancho.client.channels.User;
import lt.ekgame.bancho.client.events.EventMessage;
import lt.ekgame.bancho.client.events.EventPostUpdate;
import lt.ekgame.bancho.client.impl.StatusImpl;

public class ChannelManagerImpl implements PacketHandler, ChannelManager {
	
	private BanchoClient client;
	private RateLimiter messageLimiter;
	
	private Map<String, ChannelPublic> channels = new HashMap<>();
	private Map<Integer, User> users = new HashMap<>();
	private List<Integer> requestedUserUpdates = new ArrayList<>();
	
	public ChannelManagerImpl(BanchoClient client, RateLimiter messageLimiter) {
		this.messageLimiter = messageLimiter;
		this.client = client;
	}
	
	@Override
	public void handlePacket(Packet packet) {
		if (packet instanceof PacketChannelInfo) {
			PacketChannelInfo channelInfo = (PacketChannelInfo) packet;
			ChannelPublicImpl channel = (ChannelPublicImpl) getPublicChannel(channelInfo.channelName);
			channel.setUserCount(channelInfo.numUsers);
			channel.setDescription(channelInfo.channelDescription);
			// TODO: map channels
		}
		
		if (packet instanceof PacketUserPresence) {
			PacketUserPresence user = (PacketUserPresence) packet;
			((UserImpl)getUserById(user.userId)).updatePresence(user.username, user.userFlags);
		}
		
		if (packet instanceof PacketUsersOnline) {
			PacketUsersOnline users = (PacketUsersOnline) packet;
			for (int userId : users.userIds)
				requestUserPresence(userId);
		}
		
		if (packet instanceof PacketUserJoined) {
			PacketUserJoined user = (PacketUserJoined) packet;
			requestUserPresence(user.userId);
		}
		
		if (packet instanceof PacketUserLeft) {
			PacketUserLeft user = (PacketUserLeft) packet;
			if (user.state == PacketUserLeft.LeftState.GONE)
				((UserImpl)getUserById(user.userId)).setOnline(false);
		}
		
		if (packet instanceof PacketUserStats) {
			PacketUserStats stats = (PacketUserStats) packet;
			// TODO: apply stats to users
		}
		
		if (packet instanceof PacketChat) {
			PacketChat chat = (PacketChat) packet;
			User user = getUserById(chat.userId);
			
			if (user != null) {
				Channel channel = chat.channel.equals(getSelf().getUsername()) ? user : getPublicChannel(chat.channel);
				getBancho().dispatchEvent(new EventMessage(chat.message, user, channel));
			}
		}
		
		if (packet instanceof PacketReceivingFinished) {
			client.addEventHandler((event) -> {
				if (event instanceof EventPostUpdate) {
					Packet presenceUpdate = getPresenceRequestPacket();
					if (presenceUpdate != null)
						client.sendPacket(presenceUpdate);
				}
			});
		}
	}
	
	@Override
	public void requestUserPresence(int userId) {
		synchronized (requestedUserUpdates) {
			requestedUserUpdates.add(userId);
		}
	}
	
	private Packet getPresenceRequestPacket() {
		synchronized (requestedUserUpdates) {
			if (requestedUserUpdates.size() > 256) {
				requestedUserUpdates.clear();
				client.sendPacket(new PacketRequestPresenceAll(0));
			}
			else if (requestedUserUpdates.size() > 0) {
				Packet packet = new PacketRequestPresence(requestedUserUpdates);
				requestedUserUpdates.clear();
				return packet;
			}
		}
		return null;
	}
	
	public User getSelf() {
		return getUserById(client.getClientManager().getUserId());
	}
	
	@Override
	public User getUserById(int userId) {
		if (users.containsKey(userId))
			return users.get(userId);
		User user = new UserImpl(this, userId);
		users.put(userId, user);
		return user;
	}

	@Override
	public ChannelPublic getPublicChannel(String name) {
		if (channels.containsKey(name))
			return channels.get(name);
		ChannelPublic channel = new ChannelPublicImpl(name, this);
		channels.put(name, channel);
		return channel;
	}

	@Override
	public void sendMessagePacket(Packet packet) {
		if (client.getClientManager().isConnected() && !client.getClientManager().isSilenced())
			messageLimiter.sendPacket(packet);
		else
			System.out.println("You can't send messages right now.");
	}

	@Override
	public BanchoClient getBancho() {
		return client;
	}
}
