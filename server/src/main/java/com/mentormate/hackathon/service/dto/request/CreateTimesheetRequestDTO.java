package com.mentormate.hackathon.service.dto.request;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.mentormate.hackathon.utils.ParseDeserializer;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;

/**
 * Created by Vladislav Penchev on 2020/10/02
 */

@Data
@Validated
@NoArgsConstructor
@AllArgsConstructor
public class CreateTimesheetRequestDTO {

    @DateTimeFormat(pattern = "YYYY-MM-DD")
    @NotNull(message = "Start date must not be empty")
    @JsonSerialize(using = ToStringSerializer.class)
    @JsonDeserialize(using = ParseDeserializer.class)
    private LocalDate fromDate;
}
