package hr.fer.dsd.privtap.exceptions;

/**
 * Class for inheritance
 */
public abstract class NoDataFoundException extends RuntimeException {
    public NoDataFoundException(String message) {
        super(message);
    }
}
