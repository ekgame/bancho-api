package lt.ekgame.bancho.client;

import lt.ekgame.bancho.api.Bancho;
import lt.ekgame.bancho.api.packets.Packet;
import lt.ekgame.bancho.api.packets.server.PacketLoginReply.Status;
import lt.ekgame.bancho.client.channels.ChannelManager;
import lt.ekgame.bancho.client.events.Event;
import lt.ekgame.bancho.client.impl.BanchoClientManager;

public interface BanchoClient extends Bancho {
	
	public void dispatchEvent(Event event);
	
	public void addEventHandler(EventHandler handler);
	
	public void removeEventHandler(EventHandler handler);

	
	public void sendPacket(Packet packet);

	public void addPacketHandler(PacketHandler handler);
	
	public void removePacketHandler(PacketHandler handler);
	
	
	public boolean isConnected();
	
	public BanchoClientManager getClientManager();
	
	public ChannelManager getChannelManager();

	public void onLoginFailed(Status status);

	public void onLoginSuccess(int userId);
}
