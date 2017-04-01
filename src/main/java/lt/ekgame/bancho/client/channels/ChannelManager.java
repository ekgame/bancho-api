package lt.ekgame.bancho.client.channels;

import java.util.Collection;

import lt.ekgame.bancho.api.packets.Packet;
import lt.ekgame.bancho.client.BanchoClient;

public interface ChannelManager {
	
	BanchoClient getBancho();
	
	User getSelf();
	
	User getUserById(int userId);
	
	User getUserByName(String username);
	
	Collection<User> getUsers();
	
	ChannelPublic getPublicChannel(String name);
	
	void sendMessagePacket(Packet packet);
	
	void requestUserPresence(int userId);
}
