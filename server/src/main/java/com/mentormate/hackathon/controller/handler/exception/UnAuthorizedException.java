package com.mentormate.hackathon.controller.handler.exception;

/**
 * Created by Vladislav Penchev on 2020/10/04
 */
public class UnAuthorizedException extends RuntimeException{
    public UnAuthorizedException(String message) {
        super(message);
    }
}
