package lt.ekgame.bancho.client.channels;

import lt.ekgame.bancho.api.packets.Packet;
import lt.ekgame.bancho.client.BanchoClient;

public interface ChannelManager {
	
	BanchoClient getBancho();
	
	User getSelf();
	
	User getUserById(int userId);
	
	User getUserByName(String username);
	
	ChannelPublic getPublicChannel(String name);
	
	void sendMessagePacket(Packet packet);
	
	void requestUserPresence(int userId);
}
