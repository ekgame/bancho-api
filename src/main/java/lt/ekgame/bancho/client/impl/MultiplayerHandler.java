package lt.ekgame.bancho.client.impl;

import lt.ekgame.bancho.api.packets.Packet;
import lt.ekgame.bancho.api.packets.client.PacketCreateRoom;
import lt.ekgame.bancho.api.packets.client.PacketLeaveRoom;
import lt.ekgame.bancho.api.packets.client.PacketRoomFinishMap;
import lt.ekgame.bancho.api.packets.client.PacketRoomMapDoneLoading;
import lt.ekgame.bancho.api.packets.client.PacketRoomReady;
import lt.ekgame.bancho.api.packets.client.PacketRoomStartGame;
import lt.ekgame.bancho.api.packets.client.PacketRoomUnready;
import lt.ekgame.bancho.api.packets.client.PacketSignalMultiplayer;
import lt.ekgame.bancho.api.packets.client.PacketUpdateRoom;
import lt.ekgame.bancho.api.packets.server.PacketRoomEveryoneFinished;
import lt.ekgame.bancho.api.packets.server.PacketRoomEveryoneLoaded;
import lt.ekgame.bancho.api.packets.server.PacketRoomJoined;
import lt.ekgame.bancho.api.packets.server.PacketRoomUpdate;
import lt.ekgame.bancho.api.units.Beatmap;
import lt.ekgame.bancho.api.units.MatchSpecialMode;
import lt.ekgame.bancho.api.units.MultiplayerRoom;
import lt.ekgame.bancho.api.units.UserState;
import lt.ekgame.bancho.client.BanchoClient;
import lt.ekgame.bancho.client.PacketHandler;

public class MultiplayerHandler implements PacketHandler {
	
	private BanchoClientManager clientHandler;
	
	private MultiplayerRoom currentRoom;
	private BanchoClient bancho;
	private String roomPassword;
	private boolean enabledMultiplayer = false;
	private boolean isReady = false;
	
	public MultiplayerHandler(BanchoClient bancho, BanchoClientManager clientHandler) {
		this.bancho = bancho;
		this.clientHandler = clientHandler;
	}
	
	public void enableMultiplayer() {
		if (!enabledMultiplayer) {
			enabledMultiplayer = true;
			bancho.sendPacket(new PacketSignalMultiplayer());
		}
	}
	
	public void createRoom(String roomname, String password, int openSlots) {
		if (currentRoom != null)
			return;
		
		enableMultiplayer();
		roomPassword = password;
		MultiplayerRoom room = new MultiplayerRoom(roomname, password, openSlots, bancho.getClientManager());
		bancho.sendPacket(new PacketCreateRoom(room));
	}
	
	public boolean isHost() {
		return currentRoom != null && currentRoom.hostId == clientHandler.getUserId();
	}
	
	public void setReady(boolean ready) {
		isReady = ready;
		if (isReady)
			bancho.sendPacket(new PacketRoomReady());
		else
			bancho.sendPacket(new PacketRoomUnready());
	}
	
	public void startGame() {
		if (isHost()) {
			setReady(true);
			bancho.sendPacket(new PacketRoomStartGame());
			bancho.sendPacket(new PacketRoomMapDoneLoading());
			clientHandler.changeStatus().setBeatmap(currentRoom.getBeatmap()).send();
		}
	}

	@Override
	public void handlePacket(Packet packet) {
		if (packet instanceof PacketRoomUpdate && isHost()) {
			PacketRoomUpdate update = (PacketRoomUpdate) packet;
			if (update.room.matchId == getMatchId()) {
				currentRoom = update.room;
			}
		}
		
		if (packet instanceof PacketRoomJoined) {
			PacketRoomJoined roomUpdate = (PacketRoomJoined) packet;
			currentRoom = roomUpdate.room;
			clientHandler.changeStatus().setStatus(UserState.MULTIPLAYER).send();
		}
		
		if (packet instanceof PacketRoomEveryoneLoaded) {
			bancho.sendPacket(new PacketRoomFinishMap());
			clientHandler.changeStatus().setStatus(UserState.MULTIPLAYING).send();
		}
		
		if (packet instanceof PacketRoomEveryoneFinished) {
			clientHandler.changeStatus().setStatus(UserState.MULTIPLAYER).send();
			setReady(false);
		}
	}
	
	public void leaveRoom() {
		bancho.sendPacket(new PacketLeaveRoom());
		currentRoom = null;
		isReady = false;
	}
	
	public void setRoomName(String newName) {
		if (isHost()) {
			currentRoom.roomName = newName;
			sendRoomUpdate();
		}
	}
	
	public void setBeatmap(Beatmap beatmap) {
		if (isHost()) {
			currentRoom.setBeatmap(beatmap == null ? Beatmap.DEFAULT : beatmap);
			sendRoomUpdate();
		}
	}
	
	private void sendRoomUpdate() {
		if (isHost()) {
			bancho.sendPacket(new PacketUpdateRoom(currentRoom));
		}
	}
	
	public String getRoomPassword() {
		return roomPassword;
	}
	
	public int getMatchId() {
		return currentRoom == null ? -1 : currentRoom.matchId;
	}
	
	public boolean isReady() {
		return isReady;
	}
	
	public boolean isFreeModsEnabled() {
		return currentRoom != null && currentRoom.specialMode == MatchSpecialMode.FREE_MOD;
	}
	
	public void setFreeMods(boolean enabled) {
		if (isHost()) {
			currentRoom.specialMode = enabled ? MatchSpecialMode.FREE_MOD : MatchSpecialMode.NONE;
			sendRoomUpdate();
		}
	}
	
	public MultiplayerRoom getRoom() {
		return currentRoom;
	}
}
