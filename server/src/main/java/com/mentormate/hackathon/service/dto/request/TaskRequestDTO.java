package com.mentormate.hackathon.service.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotBlank;

/**
 * Created by Vladislav Penchev on 2020/10/08
 */
@Data
@Validated
@NoArgsConstructor
public class TaskRequestDTO {

    @NotBlank(message = "Id of task must not be null.")
    @Schema(name = "id", description = "Id of task")
    private Long id;

    @NotBlank(message = "Name of task must not be null.")
    @Schema(name = "name", description = "Name of task")
    private String name;
}
