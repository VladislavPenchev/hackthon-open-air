package com.mentormate.hackathon.controller.handler.exception;

/**
 * {@link EntityAlreadyExists} will throw when the passed entity already exists
 * <p>
 * Created by Vladislav Penchev on 2020/09/30
 */
public class EntityAlreadyExists extends RuntimeException {

    public EntityAlreadyExists(String message) {
        super(message);
    }
}
