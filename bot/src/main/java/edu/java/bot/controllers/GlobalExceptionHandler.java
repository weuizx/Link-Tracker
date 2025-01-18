package edu.java.bot.controllers;

import edu.java.bot.controllers.dto.ApiErrorResponse;
import edu.java.bot.controllers.exceptions.InvalidRequestParametersException;
import java.util.Arrays;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(InvalidRequestParametersException.class)
    public ResponseEntity<ApiErrorResponse> handleInvalidRequestParametersException(InvalidRequestParametersException e){
        ApiErrorResponse body = new ApiErrorResponse(
            "Некорректные параметры запроса",
            HttpStatus.BAD_REQUEST.value(),
            "InvalidRequestParametersException",
            e.getMessage(),
            Arrays.stream(e.getStackTrace()).map(StackTraceElement::toString).toArray(String[]::new)
        );
        return ResponseEntity.status(400).body(body);
    }

}
