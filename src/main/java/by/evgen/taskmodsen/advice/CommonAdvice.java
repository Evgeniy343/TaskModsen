package by.evgen.taskmodsen.advice;

import by.evgen.taskmodsen.exception.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class CommonAdvice {

    @ExceptionHandler({EventNotFoundException.class, LocationNotFoundException.class
            , OrganizerNotFoundException.class})
    public ResponseEntity<Response> handleNotFoundException(RuntimeException e) {
        Response response = new Response(e.getMessage());
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler({EventDuplicateException.class, LocationDuplicateException.class
            , OrganizerDuplicateException.class, IncorrectDataException.class})
    public ResponseEntity<Response> handleDuplicateException(RuntimeException e) {
        Response response = new Response(e.getMessage());
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }
}
