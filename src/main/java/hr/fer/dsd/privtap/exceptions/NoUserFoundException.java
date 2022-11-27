package hr.fer.dsd.privtap.exceptions;

/**
 * Exception occurs when user does not exist in database
 */
public class NoUserFoundException extends NoDataFoundException {
    public NoUserFoundException(String message) {
        super(message);
    }
}
