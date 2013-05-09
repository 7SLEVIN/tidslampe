package exceptions;

@SuppressWarnings("serial")
public class DeleteNonExistingException extends NonExistingException {

	public DeleteNonExistingException() {
		super("Trying to delete nonexisting entry.");
	}
	
}
