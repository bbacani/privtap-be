package hr.fer.dsd.privtap.exceptions;

/**
 * Exception occurs when trigger does not exist in database
 */
public class NoTriggerFoundException extends NoDataFoundException {
    public NoTriggerFoundException(String message) {
        super(message);
    }
}
