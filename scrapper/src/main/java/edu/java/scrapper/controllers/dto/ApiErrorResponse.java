package edu.java.scrapper.controllers.dto;

public record ApiErrorResponse(
    String description,
    int code,
    String exceptionName,
    String exceptionMessage,
    String[] stacktrace
){
}
