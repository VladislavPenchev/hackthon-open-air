package com.mentormate.hackathon.controller.handler.exception;

/**
 * Custom entity not found exception
 *
 * @author Polina Usheva
 */
public class NotFoundException extends RuntimeException {
    public NotFoundException(String message) {
        super(message);
    }
}
