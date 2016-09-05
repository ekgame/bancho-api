package lt.ekgame.bancho.client.channels;

public interface ChannelPublic extends Channel {
	
	public boolean isContextual();
	
	public String getDescription();
	
	public int getUserCount();

}
