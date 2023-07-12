package danekerscode.server.exception;

import danekerscode.server.payload.ErrorResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class Handler {

    @ExceptionHandler(ClientNotFoundException.class)
    ResponseEntity<?> handle(ClientNotFoundException e){
        return ResponseEntity.status(400).body(new ErrorResponse(e.getMessage()));
    }

    @ExceptionHandler(ClientRegisteredYetException.class)
    ResponseEntity<?> handle(ClientRegisteredYetException e){
        return ResponseEntity.status(400).body(new ErrorResponse(e.getMessage()));
    }
}
