package hr.fer.dsd.privtap.exceptions;

/**
 * Exception occurs when automation already exists in database
 */
public class AutomationAlreadyExistsException extends ObjectAlreadyExistsException {
    public AutomationAlreadyExistsException(String message) {
        super(message);
    }
}
