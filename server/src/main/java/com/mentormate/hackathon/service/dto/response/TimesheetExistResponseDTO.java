package com.mentormate.hackathon.service.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by Vladislav Penchev on 2020/10/13
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TimesheetExistResponseDTO {

    @Schema(name = "isTimesheetExist", description = "is timesheet exist")
    private boolean isTimesheetExist;
}
