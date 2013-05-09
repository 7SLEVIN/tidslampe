package exceptions;

@SuppressWarnings("serial")
public class NonExistingException extends Exception {

	public NonExistingException(String message) {
		super(message);
	}
}
