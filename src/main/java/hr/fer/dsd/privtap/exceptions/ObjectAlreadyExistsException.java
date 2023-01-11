package hr.fer.dsd.privtap.exceptions;

/**
 * Class for inheritance
 */
public abstract class ObjectAlreadyExistsException extends RuntimeException {
    public ObjectAlreadyExistsException(String message) {
        super(message);
    }
}
