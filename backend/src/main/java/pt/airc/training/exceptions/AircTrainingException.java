package pt.airc.training.exceptions;

import lombok.Getter;
import org.springframework.http.HttpStatus;


public class AircTrainingException extends RuntimeException {
    @Getter
    private HttpStatus httpStatus;
    public AircTrainingException(HttpStatus httpStatus, String message) {
        super(message);
        this.httpStatus = httpStatus;
    }
}
