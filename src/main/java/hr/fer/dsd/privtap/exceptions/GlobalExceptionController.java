package hr.fer.dsd.privtap.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Handles the exceptions across the whole application, not just to an individual Controller
 */
@ControllerAdvice
public class GlobalExceptionController {

    /**
     * Handles NoDataFoundException declared in it and sends custom response to the client
     *
     * @param exception exception thrown
     * @return custom response to the client
     */
    @ExceptionHandler({NoUserFoundException.class, NoAutomationFoundException.class,
            NoTriggerFoundException.class, NoActionFoundException.class})
    public ResponseEntity<Object> handleNoDataFoundException(Exception exception) {
        Map<String, Object> body = new LinkedHashMap<>();
        body.put("timestamp", LocalDateTime.now());
        body.put("message", exception.getMessage());
        return new ResponseEntity<>(body, HttpStatus.NOT_FOUND);
    }

    /**
     * Handles ObjectAlreadyExistsException declared in it and sends custom response to the client
     *
     * @param exception exception thrown
     * @return custom response to the client
     */
    @ExceptionHandler({UserAlreadyExistsException.class, AutomationAlreadyExistsException.class,
            TriggerAlreadyExistsException.class, ActionAlreadyExistsException.class})
    public ResponseEntity<Object> handleObjectAlreadyExistsException(Exception exception) {
        Map<String, Object> body = new LinkedHashMap<>();
        body.put("timestamp", LocalDateTime.now());
        body.put("message", exception.getMessage());
        return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
    }

    /**
     * Handles MethodArgumentNotValidException declared in it and sends custom response to the client
     *
     * @param exception exception thrown
     * @return custom response to the client
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Object> handleValidationExceptions(MethodArgumentNotValidException exception) {
        Map<String, Object> body = new LinkedHashMap<>();
        body.put("timestamp", LocalDateTime.now());
        List<ObjectError> errors = exception.getAllErrors();
        StringBuilder result = new StringBuilder();
        for(ObjectError error : errors) {
            result.append(error.getDefaultMessage());
            result.append(", ");
        }
        String message = result.length() > 0 ? result.substring(0, result.length() - 2): "";
        body.put("message", message);
        return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
    }
}
