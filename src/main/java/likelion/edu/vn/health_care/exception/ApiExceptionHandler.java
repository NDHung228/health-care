package likelion.edu.vn.health_care.exception;

import likelion.edu.vn.health_care.util.ResponseHandler;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import static likelion.edu.vn.health_care.util.ResponseHandler.generateResponse;

@RestControllerAdvice
public class ApiExceptionHandler {

    @ExceptionHandler(IndexOutOfBoundsException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public ErrorMessage todoException(Exception ex, WebRequest request) {
        return new ErrorMessage(10100, "Hung khong ton tai");
    }
}



