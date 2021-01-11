package com.mentormate.hackathon.service.dto.request;

import com.mentormate.hackathon.persistence.entity.StatusType;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * Created by Vladislav Penchev on 2020/10/03
 */
@Data
@Validated
@NoArgsConstructor
@AllArgsConstructor
public class TimesheetUpdateRequestDTO {

    List<ActivityUpdateRequestDTO> activities;

    @NotNull(message = "Status type must not be empty.")
    @Schema(name = "statusType", description = "Status type of timesheet")
    private StatusType statusType;

    @Schema(name = "total", description = "Total hours about timesheet")
    @Min(value = 0, message = "The total hours must be greater than 0 length of characters.")
    @Max(value = 730, message = "The total hours must be less than 730.")
    private double total;
}
