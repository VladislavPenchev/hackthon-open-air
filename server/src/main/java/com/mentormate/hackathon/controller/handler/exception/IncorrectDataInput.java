package com.mentormate.hackathon.controller.handler.exception;

/**
 * Custom Incorrect data input exception (Bad request)
 *
 * @author Polina Usheva
 */
public class IncorrectDataInput extends RuntimeException {
    public IncorrectDataInput(String message) {
        super(message);
    }
}
