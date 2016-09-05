package lt.ekgame.bancho.client.impl;

import lt.ekgame.bancho.api.packets.Packet;
import lt.ekgame.bancho.api.packets.client.PacketRequestUpdate;
import lt.ekgame.bancho.api.packets.client.PacketStatusUpdate;
import lt.ekgame.bancho.api.packets.server.PacketProtocolVersion;
import lt.ekgame.bancho.api.packets.server.PacketReceivingFinished;
import lt.ekgame.bancho.api.packets.server.PacketSilenceInfo;
import lt.ekgame.bancho.api.packets.server.PacketLoginReply;
import lt.ekgame.bancho.client.BanchoClient;
import lt.ekgame.bancho.client.PacketHandler;
import lt.ekgame.bancho.client.Status;

public class BanchoClientManager implements PacketHandler {
	
	private BanchoClient bancho;
	
	private Status currentStatus;
	
	private int protocolVersion = -1;
	private int userId = -1;
	
	private boolean isConnected = false;
	private boolean isSilenced = false;
	
	public BanchoClientManager(BanchoClient bancho) {
		this.bancho = bancho;
	}
	
	@Override
	public void handlePacket(Packet packet) {
		if (packet instanceof PacketProtocolVersion)
			protocolVersion = ((PacketProtocolVersion) packet).protocolVersion;
		
		if (packet instanceof PacketLoginReply) {
			PacketLoginReply reply = (PacketLoginReply) packet;
			if (reply.userId < 0)
				bancho.onLoginFailed(reply.getStatus());
			else {
				bancho.onLoginSuccess(reply.userId);
				userId = reply.userId;
			}
			//bancho.sendPacket(new PacketRequestUpdate());
		}
		
		if (packet instanceof PacketReceivingFinished) {
			setUserStatus(StatusImpl.DEFAULT);
			isConnected = true;
		}
		
		if (packet instanceof PacketSilenceInfo) {
			PacketSilenceInfo silenceInfo = (PacketSilenceInfo) packet;
			isSilenced = silenceInfo.silenceLength > 0;
			if (isSilenced)
				System.out.println("This account is silenced for " + silenceInfo.silenceLength + " minutes(?)");
		}
	}

	public int getProtocolVersion() {
		return protocolVersion;
	}
	
	public int getUserId() {
		return userId;
	}
	
	public boolean isConnected() {
		return isConnected;
	}
	
	public boolean isSilenced() {
		return isSilenced;
	}

	public void setUserStatus(Status status) {
		this.currentStatus = status;
		bancho.sendPacket(new PacketStatusUpdate(status.getUserState(), status.getBeatmap(), status.getMods(), status.getPlaymode()));
	}
	
	public Status getUserStatus() {
		return currentStatus;
	}
	
	public StatusBuilder changeStatus() {
		return new StatusBuilder(this);
	}
}
