package com.mentormate.hackathon.service.dto.response;

import com.mentormate.hackathon.persistence.entity.Activity;
import com.mentormate.hackathon.persistence.entity.StatusType;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

/**
 * Created by Vladislav Penchev on 2020/10/02
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TimesheetResponseDTO {

    @Schema(name = "id", description = "id of timesheet")
    private Long id;

    @Schema(name = "from", description = "start date of timesheet")
    private LocalDate from;

    @Schema(name = "to", description = "end date of timesheet")
    private LocalDate to;

    @Schema(name = "type of status", description = "Status of timesheet")
    private StatusType statusType;

    @Schema(name = "activities", description = "Collection of all activities")
    private List<Activity> activities;

    @Schema(name = "total", description = "All hours about timesheet")
    private double total;
}
