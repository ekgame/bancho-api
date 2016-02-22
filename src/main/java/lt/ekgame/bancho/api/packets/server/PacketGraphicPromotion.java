package lt.ekgame.bancho.api.packets.server;

import java.io.IOException;

import lt.ekgame.bancho.api.Bancho;
import lt.ekgame.bancho.api.packets.ByteDataInputStream;
import lt.ekgame.bancho.api.packets.ByteDataOutputStream;
import lt.ekgame.bancho.api.packets.Packet;
import lt.ekgame.bancho.api.utils.DataUtils;

/**
 * The message is formatted as "image_url|location_url"
 * image_url is the image that will be displayed on the bottom of osu client.
 * location_url is the location that clicking the button will point to.
 * This is used for prototions (owc livestreams, tournament registrations, bestof voting, etc).
 * Will just be "|" if there is nothing to display.
 */
public class PacketGraphicPromotion extends Packet {

	String message;
	
	public PacketGraphicPromotion() {}
	
	public PacketGraphicPromotion(String imagePath, String locationPath) {
		imagePath = imagePath == null ? "" : imagePath;
		imagePath = locationPath == null ? "" : locationPath;
		message = String.format("%s|%s", imagePath, locationPath);
	}
	
	@Override
	public void read(ByteDataInputStream stream, int length) throws IOException {
		message = stream.readString();
	}

	@Override
	public void write(ByteDataOutputStream stream) throws IOException {
		stream.writeString(message);
	}

	@Override
	public int size(Bancho bancho) {
		return DataUtils.stringLen(message);
	}
}
