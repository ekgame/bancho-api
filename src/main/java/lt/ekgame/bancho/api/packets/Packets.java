package lt.ekgame.bancho.api.packets;

import lt.ekgame.bancho.api.packets.client.*;
import lt.ekgame.bancho.api.packets.server.*;

public enum Packets {
	
	// ### CLIENT ###
	PACKET_STATUS_UPDATE    (0x00, PacketStatusUpdate.class),
	PACKET_SEND_CHAT_CHANNEL(0x01, PacketSendMessageChannel.class),
	PACKET_DISCONNECT       (0x02, PacketDisconnect.class),
	PACKET_REQUEST_UPDATE   (0x03, PacketRequestUpdate.class),
	PACKET_IDLE             (0x04, PacketIdle.class),
	PACKET_START_SPECTATING (0x10, PacketStartSpectating.class),
	PACKET_STOP_SPECTATING  (0x11, PacketStopSpectating.class),
	PACKET_SEND_CHAT_USER   (0x19, PacketSendMessageUser.class),
	PACKET_UNKNOWN_1D       (0x1d, PacketUnknown1D.class),
	PACKET_SIGNAL_MP        (0x1e, PacketSignalMultiplayer.class),
	PACKET_CREATE_ROOM      (0x1f, PacketCreateRoom.class),
	PACKET_LEAVE_ROOM       (0x21, PacketLeaveRoom.class),
	PACKET_UPDATE_ROOM      (0x29, PacketUpdateRoom.class),
	PACKET_JOIN_CHANNEL     (0x3f, PacketJoinChannel.class),
	PACKET_UNKNOWN_44       (0x44, PacketUnknown44.class),
	PACKET_LEAVE_CHANNEL    (0x4e, PacketLeaveChannel.class),
	PACKET_REQUEST_USERS    (0x55, PacketRequestUserInfo.class),
	
	PACKET_READY            (0x27, PacketRoomReady.class),
	PACKET_START_GAME       (0x2c, PacketRoomStartGame.class),
	PACKET_FINISHED_MAP     (0x31, PacketRoomFinishMap.class),
	PACKET_MAP_DONE_LOADING (0x34, PacketRoomMapDoneLoading.class),
	PACKET_UNREADY          (0x37, PacketRoomUnready.class),
	
	PACKET_REQ_PRESENCE     (0x61, PacketRequestPresence.class),
	PACKET_REQ_PRESENCE_ALL (0x62, PacketRequestPresenceAll.class),
 	
	// ### SERVER ### 
	PACKET_LOGIN_REPLY      (0x05, PacketLoginReply.class),
	PACKET_CHAT             (0x07, PacketChat.class),
	PACKET_UNKNOWN_0B       (0x0b, PacketUserStats.class),
	PACKET_UNKNOWN_0C       (0x0c, PacketUserLeft.class),
	PACKET_REPLAY_DATA      (0x0f, PacketReplayData.class),
	PACKET_MP_ROOM_UPDATE   (0x1a, PacketRoomUpdate.class),
	PACKET_UNKNOWN_1B       (0x1b, PacketUnknown1B.class),
	PACKET_UNKNOWN_1C       (0x1c, PacketUnknown1C.class),
	PACKET_ROOM_UPDATE      (0x24, PacketRoomJoined.class),
	PACKET_ROOM_HOST        (0x32, PacketRoomBecameHost.class),
	PACKET_CHANNEL_JOINED   (0x40, PacketChannelJoined.class),
	PACKET_CHANNEL_INFO     (0x41, PacketChannelInfo.class),
	PACKET_CHANNEL_INFO_JOIN(0x43, PacketChannelInfoAutojoin.class),
	PACKET_USER_TYPE        (0x47, PacketUserType.class),
	PACKET_FRIENDS_LIST     (0x48, PacketFriendsList.class),
	PACKET_PROTOCOL_VERSION (0x4b, PacketProtocolVersion.class),
	PACKET_PROMOTION        (0x4c, PacketGraphicPromotion.class),
	PACKET_USER_INFO        (0x53, PacketUserPresence.class),
	PACKET_UNKNOWN_59       (0x59, PacketReceivingFinished.class),
	PACKET_SILENCE_INFO     (0x5c, PacketSilenceInfo.class),
	PACKET_USER_SILENCED    (0x5e, PacketUserSilenced.class),
	PACKET_PRESENCE_SINGLE  (0x5f, PacketUserJoined.class),
	PACKET_PRESENCE_BUNDLE  (0x60, PacketUsersOnline.class),
	
	PACKET_SCORE_UPDATE     (0x30, PacketRoomScoreUpdate.class),
	PACKET_EVERYONE_LOADED  (0x35, PacketRoomEveryoneLoaded.class),
	PACKET_EVERYONE_FINISHED(0x3a, PacketRoomEveryoneFinished.class);
	
	
	private int id;
	private Class<? extends Packet> klass;
	
	Packets(int id, Class<? extends Packet> klass) {
		this.id = id;
		this.klass = klass;
	}
	
	public static Class<? extends Packet> getById(int id) {
		for (Packets packet : Packets.values())
			if (packet.id == id)
				return packet.klass;
		return null;
	}
	
	public static int getId(Packet packet) {
		for (Packets p : Packets.values())
			if (p.klass == packet.getClass())
				return p.id;
		return -1;
	}
}
