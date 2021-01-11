package com.mentormate.hackathon.service.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * {@link JwtResponseDTO} is DTO that will submit to the controller. Serves as view.
 * 
 * Created by Vladislav Penchev on 2020/09/12
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
public class JwtResponseDTO {
    
    @Schema(name = "token", description = "JSON Web Token")
    private String token;

    @Schema(name = "user", description = "Email of user")
    private String user;
}
