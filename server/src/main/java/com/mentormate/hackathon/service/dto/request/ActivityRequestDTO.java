package com.mentormate.hackathon.service.dto.request;

import com.mentormate.hackathon.persistence.entity.Project;
import com.mentormate.hackathon.persistence.entity.Task;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * This class represents the Activity request dto
 *
 * @author Polina Usheva
 */
@Data
@Validated
@NoArgsConstructor
public class ActivityRequestDTO {

    @NotNull(message = "Project must not be null.")
    @Schema(name = "project", description = "The project for the current activity")
    private ProjectRequestDTO project;

    @NotNull(message = "Task must not be null.")
    @Schema(name = "task", description = "The task for the current activity")
    private TaskRequestDTO task;
    
    @NotNull(message = "Timesheet days cannot be null")
    @Schema(name = "timesheetDays", description = "The timesheet days for the current activity")
    private List<DayOfTimesheetRequestDTO> timesheetDays;
}
