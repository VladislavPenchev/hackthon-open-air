package com.mentormate.hackathon.controller;

import com.mentormate.hackathon.service.AuthenticationService;
import com.mentormate.hackathon.service.dto.response.JwtResponseDTO;
import com.mentormate.hackathon.service.dto.request.LoginRequestDTO;
import com.mentormate.hackathon.service.dto.request.RegisterRequestDTO;
import com.mentormate.hackathon.service.dto.response.RegisterResponseDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.Map;

/**
 * {@link AuthenticationController} contains functionality to register user in the system
 * <p>
 * Created by Vladislav Penchev on 2020/09/30
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/auth")
@CrossOrigin(origins = "http://localhost:3000")
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    @Operation(summary = "Register", description = "This request method create user in database", tags = {"Authentication"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "User is created successfully"),
            @ApiResponse(responseCode = "400", description = "The request body is not correct"),
            @ApiResponse(responseCode = "409", description = "Entity already exists"),
            @ApiResponse(responseCode = "500", description = "Internal Server Error")})
    @PostMapping("/register")
    public ResponseEntity<RegisterResponseDTO> register(@RequestBody @Valid RegisterRequestDTO registerRequestDTO) {
        return new ResponseEntity<>(authenticationService.signUp(registerRequestDTO), HttpStatus.CREATED);
    }

    @Operation(summary = "Login", description = "This request method login user", tags = {"Authentication"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Return jwt"),
            @ApiResponse(responseCode = "400", description = "The request body is not correct"),
            @ApiResponse(responseCode = "401", description = "Authentication Failure"),
            @ApiResponse(responseCode = "500", description = "Internal Server Error")})
    @PostMapping("/login")
    public ResponseEntity<JwtResponseDTO> login(@RequestBody @Valid LoginRequestDTO loginRequestDTO) {
        return ResponseEntity.ok(authenticationService.signIn(loginRequestDTO));
    }

    @Operation(summary = "logout", description = "This request method logout user.", tags = {"Authentication"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User is logout successfully"),
            @ApiResponse(responseCode = "403", description = "Operation is forbidden"),
            @ApiResponse(responseCode = "500", description = "Internal Server Error")})
    @PostMapping("/logout")
    public ResponseEntity<Map<String, String>> logout() {
        return ResponseEntity.ok(authenticationService.logout());
    }

    @Operation(summary = "Gets logged user", description = "This request method gets logged user.", tags = {"Get Me"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Get user information successfully"),
            @ApiResponse(responseCode = "500", description = "Internal Server Error")})
    @GetMapping("/me")
    public ResponseEntity<String> currentUserName(Authentication authentication) {
        return ResponseEntity.ok(authentication.getName());
    }

}
