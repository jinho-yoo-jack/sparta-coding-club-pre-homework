package sparta.coding.club.prehomework.global;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.ServletWebRequest;
import sparta.coding.club.prehomework.global.exception.FailInsertToPersistence;

import java.io.IOException;
import java.util.NoSuchElementException;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public final ResponseEntity<ErrorResponse> methodArgumentNotValidException(Exception ex, ServletWebRequest request) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(makeErrorMessage(ex, request, HttpStatus.BAD_REQUEST));
    }

    @ExceptionHandler({IOException.class, NoSuchElementException.class, NoSuchMethodException.class, FailInsertToPersistence.class, IllegalArgumentException.class})
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public final ResponseEntity<ErrorResponse> handleBadReqExceptions(Exception ex, ServletWebRequest request) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(makeErrorMessage(ex, request, HttpStatus.INTERNAL_SERVER_ERROR));
    }

    private ErrorResponse makeErrorMessage(Exception ex, ServletWebRequest request, HttpStatus responseCode) {
        String requestPath = request.getRequest().getRequestURI();
        String requestMethod = request.getRequest().getMethod();
        String params = "";
        String message = ex.getMessage();
        if ("".equals(message)) message = ((MethodArgumentNotValidException) ex).getMessage();
        if ("GET".equals(requestMethod)) params = requestPath.substring(requestPath.lastIndexOf("/") + 1);

        return new ErrorResponse(responseCode.value(), message, params, requestPath);
    }


}
