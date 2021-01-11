package com.mentormate.hackathon.controller.handler;

import com.mentormate.hackathon.controller.handler.exception.EntityAlreadyExists;
import com.mentormate.hackathon.controller.handler.exception.NotFoundException;
import com.mentormate.hackathon.controller.handler.exception.UnAuthorizedException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.HashMap;
import java.util.Map;

/**
 * A {@link ControllerAdvice} allows you to use exactly the same exception handling techniques
 * but apply them across the whole application, not just to an individual controller.
 * <p>
 * Created by Vladislav Penchev on 2020/09/30
 */
@Slf4j
@ControllerAdvice
public class HttpExceptionHandlers {

    /**
     * Handles properties validation exceptions.
     *
     * @param e MethodArgumentNotValidException
     * @return json with message about existing error
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleValidationExceptions(MethodArgumentNotValidException e) {
        Map<String, String> errors = new HashMap<>();
        e.getBindingResult()
                .getAllErrors()
                .forEach(error -> {
                    String errorMessage = error.getDefaultMessage();
                    errors.put("message", errorMessage);
                });
        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }

    /**
     * Handle {@link EntityAlreadyExists} when the passed entity already exists
     *
     * @param e current exception
     * @return json with message about existing error
     */
    @ExceptionHandler(EntityAlreadyExists.class)
    public ResponseEntity<Map<String, String>> entityAlreadyExists(Exception e) {
        log.info("Entity already exists.");
        return new ResponseEntity<>(Map.of("message", e.getMessage()), HttpStatus.CONFLICT);
    }

    /**
     * Handle {@link AuthenticationException} when user is not authorized
     *
     * @param e current exception
     * @return json with message about existing error
     */
    @ExceptionHandler(UnAuthorizedException.class)
    public ResponseEntity<Map<String, String>> unauthorizedException(AuthenticationException e) {
        return new ResponseEntity<>(Map.of("message", e.getMessage()), HttpStatus.UNAUTHORIZED);
    }

    /**
     * Handles a custom entity not found exception
     *
     * @param e current exception
     * @return json with message about existing error
     */
    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<Map<String, String>> entityNotFound(NotFoundException e) {
        log.info("Entity not found.");
        return new ResponseEntity<>(Map.of("message", e.getMessage()), HttpStatus.NOT_FOUND);
    }

}
