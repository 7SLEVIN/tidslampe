package exceptions;

@SuppressWarnings("serial")
public class UpdateNonExistingException extends NonExistingException {

	public UpdateNonExistingException() {
		super("Trying to delete nonexisting entry.");
	}

}
