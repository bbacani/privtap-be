package hr.fer.dsd.privtap.exceptions;

/**
 * Exception occurs when user already exists in database
 */
public class UserAlreadyExistsException extends ObjectAlreadyExistsException {
    public UserAlreadyExistsException(String message) {
        super(message);
    }
}
