package lt.ekgame.bancho.client.channels;

public interface Channel {
	
	void sendMessage(String message);
	
	String getName();

}
