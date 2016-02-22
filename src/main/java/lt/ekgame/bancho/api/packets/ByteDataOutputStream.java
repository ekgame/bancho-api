package lt.ekgame.bancho.api.packets;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.charset.Charset;

import lt.ekgame.bancho.api.Bancho;

public class ByteDataOutputStream extends OutputStream {
	
	private OutputStream output;
	private Bancho bancho;
	
	public ByteDataOutputStream(OutputStream stream, Bancho bancho) {
		output = new DataOutputStream(stream);
		this.bancho = bancho;
	}
	
	public int getProtocolVersion() {
		return bancho.getProtocolVersion();
	}

	@Override
	public void write(int integer) throws IOException {
		writeInt(integer);
	}
	
	public void writeInt(int number) throws IOException {
		output.write(new byte[]{
			(byte) (number & 0xFF),
			(byte) ((number >> 8) & 0xFF),
			(byte) ((number >> 16) & 0xFF),
			(byte) ((number >> 24) & 0xFF),
		});
	}
	
	public void writeShort(short number) throws IOException {
		output.write(new byte[]{
			(byte) (number & 0xFF),
			(byte) ((number >> 8) & 0xFF)
		});
	}
	
	public void writeBytes(byte[] array) throws IOException {
		output.write(array);
	}
	
	public void writeByte(byte number) throws IOException {
		output.write(new byte[]{number});
	}
	
	public void write7BitInteger(int integer) throws IOException {
		long v = integer & 0xFFFFFFFF; // support negative numbers
        while (v >= 0x80) { 
        	writeByte((byte) (v | 0x80));
            v >>= 7;
        }
        writeByte((byte)v); 
	}
	
	public void writeString(String str) throws IOException {
		if (str == null) {
			writeByte((byte) 0);
			return;
		}
		
		writeByte((byte) 11); // identify a string
		byte[] bytes = str.getBytes(Charset.forName("UTF-8"));
		write7BitInteger(bytes.length);
		output.write(bytes);
	}

	public void writeFloat(float value) throws IOException {
		byte[] bytes = ByteBuffer.allocate(4).order(ByteOrder.LITTLE_ENDIAN).putFloat(value).array();
		writeBytes(bytes);
	}

	public void writeLong(long number) throws IOException {
		output.write(new byte[]{
			(byte) (number & 0xFF),
			(byte) ((number >> 8) & 0xFF),
			(byte) ((number >> 16) & 0xFF),
			(byte) ((number >> 24) & 0xFF),
			(byte) ((number >> 32) & 0xFF),
			(byte) ((number >> 40) & 0xFF),
			(byte) ((number >> 48) & 0xFF),
			(byte) ((number >> 56) & 0xFF),
		});
	}
}
