package hr.fer.dsd.privtap.exceptions;

/**
 * Exception occurs when automation does not exist in database
 */
public class NoAutomationFoundException extends NoDataFoundException {
    public NoAutomationFoundException(String message) {
        super(message);
    }
}
