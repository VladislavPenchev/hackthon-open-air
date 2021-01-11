package com.mentormate.hackathon.service.dto.request;

import com.mentormate.hackathon.persistence.entity.DayOfTimesheet;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * Created by Vladislav Penchev on 2020/10/03
 */
@Data
@Validated
@NoArgsConstructor
public class ActivityUpdateRequestDTO {
    
    @NotNull(message = "Id of activity must not be null.")
    @Schema(name = "id", description = "Id of activity")
    private Long id;

    @Schema(name = "project", description = "Project of activity")
    private ProjectRequestDTO project;
    
    @Schema(name = "task", description = "task of Activity")
    private TaskRequestDTO task;

    @Schema(name = "timesheetDays", description = "Collection of timesheet days")
    private List<DayOfTimesheet> timesheetDays;
}
