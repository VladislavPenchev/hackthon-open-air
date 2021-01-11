package com.mentormate.hackathon.service.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

/**
 * This class represents the Project response dto
 *
 * @author Polina Usheva
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DayOfTimesheetResponseDTO {

    @Schema(name = "id", description = "Id of day of timesheet")
    private Long id;

    @Schema(name = "date", description = "Date of activity")
    private LocalDate date;

    @Schema(name = "hours", description = "Hours of activity")
    private Double hours;

}
