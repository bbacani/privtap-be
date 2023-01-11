package hr.fer.dsd.privtap.exceptions;

/**
 * Exception occurs when trigger already exists in database
 */
public class TriggerAlreadyExistsException extends ObjectAlreadyExistsException {
    public TriggerAlreadyExistsException(String message) {
        super(message);
    }
}
