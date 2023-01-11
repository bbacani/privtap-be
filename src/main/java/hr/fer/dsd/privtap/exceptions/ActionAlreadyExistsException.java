package hr.fer.dsd.privtap.exceptions;

/**
 * Exception occurs when action already exists in database
 */
public class ActionAlreadyExistsException extends ObjectAlreadyExistsException {
    public ActionAlreadyExistsException(String message) {
        super(message);
    }
}
