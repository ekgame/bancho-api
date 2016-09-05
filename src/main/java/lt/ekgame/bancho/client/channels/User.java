package lt.ekgame.bancho.client.channels;

public interface User extends Channel {

	int getUserId();
	
	String getUsername();
	
	boolean isFriend();
	
	boolean isAdmin();
	
	boolean isOwner();
	
	boolean isSupporter();

	boolean isOnline();
}
