package emaillist.exception;

import lombok.extern.slf4j.Slf4j;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import emaillist.dto.JsonResult;
import jakarta.servlet.http.HttpServletResponse;

import java.io.PrintWriter;
import java.io.StringWriter;

@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {
    
    @ExceptionHandler({NoHandlerFoundException.class, NoResourceFoundException.class})
    public ResponseEntity<JsonResult<String>> handlerNoResourceFoundException(HttpServletResponse response) {
    	return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(JsonResult.fail("unknown url"));
    }    

    @ExceptionHandler(Exception.class)
    public ResponseEntity<JsonResult<String>> handler(Exception e) {
        // logging
        StringWriter errors = new StringWriter();
        e.printStackTrace(new PrintWriter(errors));
        log.error(errors.toString());

        return ResponseEntity
                .internalServerError()
                .body(JsonResult.fail(errors.toString()));
    }
}