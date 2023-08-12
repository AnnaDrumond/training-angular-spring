package pt.airc.training.exceptions;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class AircTrainingExceptionHandler {

    @ExceptionHandler(value = {AircTrainingException.class})
    protected ResponseEntity<Object> handleConflict(AircTrainingException aircTrainingException) {
        return ResponseEntity.status(
            aircTrainingException.getHttpStatus()).body(aircTrainingException.getMessage());
    }
}
