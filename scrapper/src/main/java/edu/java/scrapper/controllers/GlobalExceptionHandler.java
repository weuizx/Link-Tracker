package edu.java.scrapper.controllers;

import edu.java.scrapper.controllers.dto.ApiErrorResponse;
import edu.java.scrapper.exceptions.ChatExistsException;
import edu.java.scrapper.exceptions.ChatNotFoundException;
import edu.java.scrapper.exceptions.InvalidRequestParametersException;
import edu.java.scrapper.exceptions.LinkExistsException;
import edu.java.scrapper.exceptions.LinkNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import java.util.Arrays;

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

    @ExceptionHandler(ChatNotFoundException.class)
    public ResponseEntity<ApiErrorResponse> handleChatNotFoundException(ChatNotFoundException e){
        ApiErrorResponse body = new ApiErrorResponse(
            "Чат не зарегистрирован",
            HttpStatus.NOT_FOUND.value(),
            "ChatNotFoundException",
            e.getMessage(),
            Arrays.stream(e.getStackTrace()).map(StackTraceElement::toString).toArray(String[]::new)
        );
        return ResponseEntity.status(404).body(body);
    }

    @ExceptionHandler(LinkNotFoundException.class)
    public ResponseEntity<ApiErrorResponse> handleLinkNotFoundException(LinkNotFoundException e){
        ApiErrorResponse body = new ApiErrorResponse(
            "Ссылка не найдена",
            HttpStatus.NOT_FOUND.value(),
            "LinkNotFoundException",
            e.getMessage(),
            Arrays.stream(e.getStackTrace()).map(StackTraceElement::toString).toArray(String[]::new)
        );
        return ResponseEntity.status(404).body(body);
    }

    @ExceptionHandler(ChatExistsException.class)
    public ResponseEntity<ApiErrorResponse> handleChatExistsException(ChatExistsException e){
        ApiErrorResponse body = new ApiErrorResponse(
            "Чат уже зарегистрирован",
            HttpStatus.BAD_REQUEST.value(),
            "ChatExistsException",
            e.getMessage(),
            Arrays.stream(e.getStackTrace()).map(StackTraceElement::toString).toArray(String[]::new)
        );
        return ResponseEntity.status(400).body(body);
    }

    @ExceptionHandler(LinkExistsException.class)
    public ResponseEntity<ApiErrorResponse> handleLinkExistsException(LinkExistsException e){
        ApiErrorResponse body = new ApiErrorResponse(
            "Ссылка уже отслеживается",
            HttpStatus.BAD_REQUEST.value(),
            "LinkExistsException",
            e.getMessage(),
            Arrays.stream(e.getStackTrace()).map(StackTraceElement::toString).toArray(String[]::new)
        );
        return ResponseEntity.status(400).body(body);
    }
}
