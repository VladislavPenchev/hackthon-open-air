package com.mentormate.hackathon.service.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * This class represents the Task response dto
 *
 * @author Polina Usheva
 */
@Data
@NoArgsConstructor
public class TaskResponseDTO {
    
    @Schema(name = "id", description = "Id of task")
    private Long id;
    
    @Schema(name = "name", description = "Name of task")
    private String name;
}
