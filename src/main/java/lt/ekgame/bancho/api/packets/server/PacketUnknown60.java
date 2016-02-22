package lt.ekgame.bancho.api.packets.server;

import java.util.List;

/**
 * Likely to be a list of online users ids.
 */
public class PacketUnknown60 extends PacketUnknown61 {
	
	public PacketUnknown60() {
		super();
	}
	
	public PacketUnknown60(int... integers) {
		super(integers);
	}
	
	public PacketUnknown60(List<Integer> integers) {
		super(integers);
	}
	
}
