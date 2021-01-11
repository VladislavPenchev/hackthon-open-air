package com.mentormate.hackathon.service.dto.request;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.mentormate.hackathon.utils.ParseDeserializer;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * This class represents the Day of timesheets request dto
 *
 * @author Polina Usheva
 */
@Data
@Validated
@NoArgsConstructor
public class DayOfTimesheetRequestDTO {

    @NotBlank(message = "Id of day of timesheet day must not be null.")
    @Schema(name = "id", description = "Id of day of timesheet")
    private Long id;

    @DateTimeFormat(pattern = "YYYY-MM-DD")
    @NotNull(message = "Start date must not be empty")
    @JsonSerialize(using = ToStringSerializer.class)
    @JsonDeserialize(using = ParseDeserializer.class)
    private LocalDate date;

    @NotBlank(message = "Hours must not be empty.")
    @Min(value = 0, message = "The hours must not be less than 0.")
    @Max(value = 24, message = "The hours must be less than 24.")
    private Double hours;

}
