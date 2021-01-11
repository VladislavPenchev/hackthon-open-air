package com.mentormate.hackathon.service.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotBlank;

/**
 * {@link LoginRequestDTO} accept through the controller and it is used for user register.
 * <p>
 * Created by Vladislav Penchev on 2020/10/01
 */
@Data
@Validated
@NoArgsConstructor
@AllArgsConstructor
public class LoginRequestDTO {

    @NotBlank(message = "Email must not be empty.")
    @Schema(name = "email", description = "email of user")
    private String email;

    @NotBlank(message = "Password must not be empty.")
    @Schema(name = "password", description = "password of user")
    private String password;
}

