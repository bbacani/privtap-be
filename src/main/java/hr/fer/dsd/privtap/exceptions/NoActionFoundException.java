package hr.fer.dsd.privtap.exceptions;

/**
 * Exception occurs when action does not exist in database
 */
public class NoActionFoundException extends NoDataFoundException {
    public NoActionFoundException(String message) {
        super(message);
    }
}
