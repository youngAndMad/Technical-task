package danekerscode.server.exception.AdviceController;

import danekerscode.server.exception.ClientNotFoundException;
import danekerscode.server.exception.ClientRegisteredYetException;
import danekerscode.server.exception.InvalidLocationException;
import danekerscode.server.payload.ErrorResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class Handler {

    @ExceptionHandler(ClientNotFoundException.class)
    ResponseEntity<?> handle(ClientNotFoundException e) {
        return ResponseEntity.status(400).body(new ErrorResponse(e.getMessage()));
    }

    @ExceptionHandler(ClientRegisteredYetException.class)
    ResponseEntity<?> handle(ClientRegisteredYetException e) {
        return ResponseEntity.status(400).body(new ErrorResponse(e.getMessage()));
    }

    @ExceptionHandler(InvalidLocationException.class)
    ResponseEntity<?> handle(InvalidLocationException e) {
        System.out.println("I get this exception");
        return ResponseEntity.status(410).body(new ErrorResponse(e.getMessage()));
    }
}
