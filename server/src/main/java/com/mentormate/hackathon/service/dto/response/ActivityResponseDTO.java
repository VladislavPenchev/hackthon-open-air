package com.mentormate.hackathon.service.dto.response;

import com.mentormate.hackathon.persistence.entity.DayOfTimesheet;
import com.mentormate.hackathon.persistence.entity.Project;
import com.mentormate.hackathon.persistence.entity.Task;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * This class represents the Activity response dto
 *
 * @author Polina Usheva
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ActivityResponseDTO {

    @Schema(name = "id", description = "Id of day of timesheet")
    private Long id;

    @Schema(name = "project", description = "Current Project")
    private Project project;

    @Schema(name = "task", description = "All task of Project")
    private Task task;

    @Schema(name = "timesheetDays", description = "Days of timesheets")
    private List<DayOfTimesheet> timesheetDays;
}
