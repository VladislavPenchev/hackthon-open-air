package com.mentormate.hackathon.service.dto.response;

import com.mentormate.hackathon.persistence.entity.Task;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

/**
 * This class represents the Project response dto
 *
 * @author Polina Usheva
 */
@Data
@NoArgsConstructor
public class ProjectResponseDTO {

    @Schema(name = "id", description = "Id of project")
    private Long id;

    @Schema(name = "name", description = "Name of project")
    private String name;

    @Schema(description = "The tasks of the current project")
    private Set<Task> tasks = new HashSet<>();
}
