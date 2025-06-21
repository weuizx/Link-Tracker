package edu.java.bot.controllers.exceptions;

public class InvalidRequestParametersException extends RuntimeException {

    public InvalidRequestParametersException(String message) {
        super(message);
    }
}
