package lt.ekgame.bancho.api.exceptions;

public class LoginException extends APIException {

	private static final long serialVersionUID = 4552386077760769594L;
	
	public LoginException(String error) {
		super(error);
	}
}
