package com.mentormate.hackathon.service;

import com.mentormate.hackathon.persistence.entity.DayOfTimesheet;
import com.mentormate.hackathon.persistence.repository.DayOfTimesheetRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

/**
 * Represents the day of timesheet service. Contains all of the business logic.
 *
 * @author Polina Usheva
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class DayOfTimesheetService {

    private static final int DAYS_OF_WEEK = 7;
    private static final double DEFAULT_HOURS = 0;
    private static final int INDEX_OF_INCREMENT_OF_DAY = 1;

    private final DayOfTimesheetRepository dayOfTimesheetRepository;

    private final ModelMapper modelMapper;

    /**
     * Creates a new day of timesheet.
     *
     * @param fromDate
     * @return the day of timesheet entity
     */
    public List<DayOfTimesheet> create(LocalDate fromDate) {
        List<DayOfTimesheet> dayOfTimesheets = new ArrayList<>();
        Calendar calender = Calendar.getInstance();
        calender.setTime(Date.from(fromDate.atStartOfDay(ZoneId.systemDefault()).toInstant()));

        for (int i = 0; i < DAYS_OF_WEEK; i++) {
            TimeZone timeZone = calender.getTimeZone();
            ZoneId zoneId = timeZone == null ? ZoneId.systemDefault() : timeZone.toZoneId();
            LocalDate currentDate = LocalDate.ofInstant(calender.toInstant(), zoneId);
            DayOfTimesheet currentDayOfTimesheet = new DayOfTimesheet(currentDate, DEFAULT_HOURS);
            dayOfTimesheets.add(currentDayOfTimesheet);
            calender.add(Calendar.DAY_OF_YEAR, INDEX_OF_INCREMENT_OF_DAY);
        }

        return this.dayOfTimesheetRepository.saveAll(dayOfTimesheets);
    }

}
