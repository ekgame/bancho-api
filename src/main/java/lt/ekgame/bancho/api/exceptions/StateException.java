package lt.ekgame.bancho.api.exceptions;

public class StateException extends RuntimeException {

	private static final long serialVersionUID = -7420771345526066831L;

	public StateException(String error) {
		super(error);
	}
}
