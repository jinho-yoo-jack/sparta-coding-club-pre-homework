package sparta.coding.club.prehomework.global;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.apache.coyote.BadRequestException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.ServletWebRequest;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.NoSuchFileException;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public final ResponseEntity<ErrorResponse> handleBadReqExceptions(Exception ex, ServletWebRequest request) throws IOException {
        String requestPath = request.getRequest().getRequestURI();
        String requestMethod = request.getRequest().getMethod();
        String params = "";
        String message = ex.getMessage();
        if("".equals(message)) message = ((MethodArgumentNotValidException) ex).getMessage();
        if ("GET".equals(requestMethod)) params = requestPath.substring(requestPath.lastIndexOf("/") + 1);
        ErrorResponse response = new ErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), message, params, requestPath);

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }


}
