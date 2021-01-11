package com.mentormate.hackathon.controller.handler.exception;

/**
 * The type No session found exception.
 */
public class NoSessionFoundException extends RuntimeException {
    /**
     * Instantiates a new No session found exception.
     *
     * @param message the message
     */
    public NoSessionFoundException(String message) {
        super(message);
    }
}
