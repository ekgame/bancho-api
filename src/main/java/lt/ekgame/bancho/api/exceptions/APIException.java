package lt.ekgame.bancho.api.exceptions;

public class APIException extends Exception {

	private static final long serialVersionUID = -4191416698780887853L;
	
	public APIException() {}
	
	public APIException(String error) {
		super(error);
	}
}
