package lt.ekgame.bancho.api.packets.server;

import java.io.IOException;

import lt.ekgame.bancho.api.Bancho;
import lt.ekgame.bancho.api.packets.ByteDataInputStream;
import lt.ekgame.bancho.api.packets.ByteDataOutputStream;
import lt.ekgame.bancho.api.packets.Packet;

/**
 * Notifies the client of it's user id.
 */
public class PacketUserId extends Packet {
	
	public int userId;
	
	public PacketUserId() {}
	
	public PacketUserId(int statusCode) {
		this.userId = statusCode;
	}

	@Override
	public void read(ByteDataInputStream stream, int length) throws IOException {
		userId = stream.readInt();
	}
	
	@Override
	public void write(ByteDataOutputStream stream) throws IOException {
		stream.writeInt(userId);
	}

	@Override
	public int size(Bancho bancho) {
		return 4;
	}
	
	public int getUserId() {
		return userId;
	}

	public Status getStatus() {
		return Status.fromId(userId);
	}

	private enum Status {
		RECEIVING_DATA,
		AUTH_FAILED,
		OLD_VERSION,
		NO_MORE_ACCOUNTS, // Banned? Too many online? No idea
		ERROR_OCCURED,
		SUPPORTER_REQUIRED,
		UNKNOWN_1,
		UNKNOWN_2;
		
		public static Status fromId(int id) {
			switch (id) {
			case -8:
				return UNKNOWN_2;
			case -7:
				return UNKNOWN_1;
			case -6:
				return SUPPORTER_REQUIRED;	
			case -5:
				return ERROR_OCCURED;	
			case -4:
			case -3:	
				return NO_MORE_ACCOUNTS;	
			case -2:
				return OLD_VERSION;
			case -1:	
				return AUTH_FAILED;
			default:
				return RECEIVING_DATA;
			}
		}
	}
}
