package com.mentormate.hackathon.service;

import com.mentormate.hackathon.controller.handler.exception.NotFoundException;
import com.mentormate.hackathon.persistence.entity.Activity;
import com.mentormate.hackathon.persistence.entity.StatusType;
import com.mentormate.hackathon.persistence.entity.Timesheet;
import com.mentormate.hackathon.persistence.entity.User;
import com.mentormate.hackathon.persistence.repository.ActivityRepository;
import com.mentormate.hackathon.persistence.repository.TimesheetRepository;
import com.mentormate.hackathon.service.dto.request.CreateTimesheetRequestDTO;
import com.mentormate.hackathon.service.dto.request.TimesheetUpdateRequestDTO;
import com.mentormate.hackathon.service.dto.response.TimesheetExistResponseDTO;
import com.mentormate.hackathon.service.dto.response.TimesheetResponseDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Created by Vladislav Penchev on 2020/10/02
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class TimesheetService {

    private static final double DEFAULT_TIMESHEET_TOTAL = 0.0;
    private static final int INDEX_OF_FIRST_DAY_OF_TIMESHEET = 0;
    private static final int INDEX_OF_LAST_DAY_OF_TIMESHEET = 6;

    private final ActivityService activityService;
    private final TimesheetRepository timesheetRepository;
    private final ModelMapper modelMapper;
    private final UserService userService;
    private final ActivityRepository activityRepository;

    /**
     * Create new {@link Timesheet} in our application
     *
     * @param createTimesheetRequestDTO {@link CreateTimesheetRequestDTO} information needed for creation of new {@link Timesheet}
     * @param userEmail                 email of current login user
     * @return {@link Timesheet} that is saved in our database converted to {@link TimesheetResponseDTO}
     */
    public TimesheetResponseDTO createTimesheet(CreateTimesheetRequestDTO createTimesheetRequestDTO, String userEmail) {
        log.info("Start creating timesheet");
        User user = userService.checkIfUserExist(userEmail);
        Activity currentActivity = activityService.create(createTimesheetRequestDTO.getFromDate());
        LocalDate fromTimesheetDay = currentActivity.getTimesheetDays().get(INDEX_OF_FIRST_DAY_OF_TIMESHEET).getDate();
        LocalDate toTimesheetDay = currentActivity.getTimesheetDays().get(INDEX_OF_LAST_DAY_OF_TIMESHEET).getDate();
        Timesheet timesheet = Timesheet.builder()
                .activities(List.of(currentActivity))
                .statusType(StatusType.OPEN)
                .total(DEFAULT_TIMESHEET_TOTAL)
                .fromDate(fromTimesheetDay)
                .toDate(toTimesheetDay)
                .user(user)
                .build();
        TimesheetResponseDTO createTimesheetResponseDTO = modelMapper.map(timesheetRepository.save(timesheet), TimesheetResponseDTO.class);
        log.info("End creating timesheet and saved in database with id: {}", createTimesheetResponseDTO.getId());
        return createTimesheetResponseDTO;
    }

    /**
     * Create new {@link Timesheet} in our application
     *
     * @param timesheetId id of the {@link Timesheet}
     * @param userEmail   email of current login user
     * @return {@link Timesheet} that is saved in our database converted to {@link TimesheetResponseDTO}
     */
    public TimesheetResponseDTO addActivityToTimesheet(Long timesheetId, String userEmail) {
        log.info("Start creating timesheet");
        User user = userService.checkIfUserExist(userEmail);
        Timesheet timesheet = checkIfTimesheetExists(timesheetId, user.getId());
        Activity currentActivity = activityService.create(timesheet.getFromDate());
        timesheet.getActivities().add(currentActivity);
        TimesheetResponseDTO createTimesheetResponseDTO = modelMapper.map(timesheetRepository.save(timesheet), TimesheetResponseDTO.class);
        log.info("End creating timesheet and saved in database with id: {}", createTimesheetResponseDTO.getId());
        return createTimesheetResponseDTO;
    }

    /**
     * Provide all {@link Timesheet}'s for current user
     *
     * @param userEmail email of current login user
     * @return {@link Page<TimesheetResponseDTO>} of our {@link Timesheet}'s converted to {@link TimesheetResponseDTO}'s
     */
    public Page<TimesheetResponseDTO> getAll(int page, int size, String userEmail) {
        log.info("Fetch all timesheets");
        User user = userService.checkIfUserExist(userEmail);
        return timesheetRepository.findAllByUser_Id(PageRequest.of(page, size), user.getId()).map(timesheet -> modelMapper.map(timesheet, TimesheetResponseDTO.class));
    }

    /**
     * Get {@link Timesheet} by current login user and given timesheet id
     *
     * @param timesheetId id of the {@link Timesheet}
     * @param userEmail   email of current login user
     * @return {@link TimesheetResponseDTO} corresponding {@link Timesheet}
     */
    public TimesheetResponseDTO getById(Long timesheetId, String userEmail) {
        log.info("Get timesheet by id: {}", timesheetId);
        User user = userService.checkIfUserExist(userEmail);
        Timesheet timesheet = checkIfTimesheetExists(timesheetId, user.getId());
        return modelMapper.map(timesheet, TimesheetResponseDTO.class);
    }

    /**
     * Update all information for {@link Timesheet} by its id
     *
     * @param timesheetId               Id of the {@link Timesheet}
     * @param userEmail                 email of current login user
     * @param timesheetUpdateRequestDTO {@link TimesheetUpdateRequestDTO} new information that will be persisted into database
     * @return Updated {@link TimesheetResponseDTO} corresponding {@link Timesheet}
     */
    public TimesheetResponseDTO updateTimesheetById(Long timesheetId, TimesheetUpdateRequestDTO timesheetUpdateRequestDTO,
                                                    String userEmail) {
        log.info("Start updating timesheet with id: {}", timesheetId);
        User user = userService.checkIfUserExist(userEmail);
        Timesheet timesheet = checkIfTimesheetExists(timesheetId, user.getId());
        List<Activity> activities = timesheetUpdateRequestDTO.getActivities()
                .stream().map(activity -> {
                    Activity currentActivity = modelMapper.map(activity, Activity.class);
                    currentActivity.getProject().setTasks(Set.of(currentActivity.getTask()));
                    return currentActivity;
                }).collect(Collectors.toList());
        timesheet.setActivities(activities);
        timesheet.setTotal(timesheetUpdateRequestDTO.getTotal());
        timesheet = timesheetRepository.save(timesheet);
        TimesheetResponseDTO timesheetResponseDTO = modelMapper.map(timesheet, TimesheetResponseDTO.class);
        log.info("Updated timesheet with id: {}", timesheet);
        return timesheetResponseDTO;
    }

    /**
     * Update status for {@link Timesheet} by its id
     *
     * @param timesheetId Id of the {@link Timesheet}
     * @param userEmail   email of current login user
     * @return Updated {@link TimesheetResponseDTO} corresponding {@link Timesheet}
     */
    public TimesheetResponseDTO updateTimesheetStatus(Long timesheetId, String userEmail) {
        log.info("Start updating timesheet with id: {}", timesheetId);
        User user = userService.checkIfUserExist(userEmail);
        Timesheet timesheet = checkIfTimesheetExists(timesheetId, user.getId());
        timesheet.setStatusType(StatusType.SUBMITTED);
        timesheet = timesheetRepository.save(timesheet);
        log.info("Updated timesheet with id: {}", timesheet);
        return modelMapper.map(timesheet, TimesheetResponseDTO.class);
    }

    /**
     * Delete {@link Timesheet} by Id
     *
     * @param timesheetId Id of the {@link Timesheet} that we want to delete
     * @param userEmail   email of current login user
     * @return Deleted {@link TimesheetResponseDTO} corresponding {@link Timesheet}
     */
    public TimesheetResponseDTO deleteTimesheetById(Long timesheetId, String userEmail) {
        log.info("Start deleting timesheet with id: {}", timesheetId);
        User user = userService.checkIfUserExist(userEmail);
        Timesheet timesheet = checkIfTimesheetExists(timesheetId, user.getId());
        timesheetRepository.delete(timesheet);
        TimesheetResponseDTO timesheetResponseDTO = modelMapper.map(timesheet, TimesheetResponseDTO.class);
        log.info("Deleted timesheet with id: {}", timesheetId);
        return timesheetResponseDTO;
    }

    /**
     * Delete {@link Activity} of {@link Timesheet} by it's id
     *
     * @param activityId  Id of the {@link Activity} that we want to delete
     * @param timesheetId Id of the {@link Timesheet}
     * @param userEmail   email of current login user
     * @return Deleted {@link TimesheetResponseDTO} corresponding {@link Timesheet}
     */
    public TimesheetResponseDTO deleteActivityFromTimesheet(Long timesheetId, Long activityId, String userEmail) {
        log.info("Start deleting timesheet with id: {}", timesheetId);
        User user = userService.checkIfUserExist(userEmail);
        Timesheet timesheet = checkIfTimesheetExists(timesheetId, user.getId());
        activityRepository.deleteById(activityId);
        log.info("Deleted timesheet with id: {}", timesheetId);
        return modelMapper.map(timesheet, TimesheetResponseDTO.class);
    }

    /**
     * Check if {@link Timesheet} exist by fromDate
     *
     * @param userEmail email of current login user
     * @return {@link TimesheetExistResponseDTO}
     */
    public TimesheetExistResponseDTO checkIfTimesheetExistsByFromDate(CreateTimesheetRequestDTO createTimesheetRequestDTO, String userEmail) {
        log.info("Fetch timesheet by from date");
        userService.checkIfUserExist(userEmail);
        boolean isTimesheetExist = !timesheetRepository.findTimesheetByFromDate(createTimesheetRequestDTO.getFromDate()).isEmpty();
        return new TimesheetExistResponseDTO(isTimesheetExist);
    }

    /**
     * Check if {@link Timesheet} exists
     *
     * @param userId      email of current login user
     * @param timesheetId Id of the {@link java.sql.Time} that we want to check if exists
     * @return existing {@link Timesheet}
     * @throws NotFoundException if {@link Timesheet} doesn't exist.
     */
    public Timesheet checkIfTimesheetExists(Long timesheetId, Long userId) {
        return timesheetRepository.findByIdAndUser_Id(timesheetId, userId)
                .orElseThrow(() -> new NotFoundException(String.format("Timesheet with id %d of user with id %d is not found !", timesheetId, userId)));
    }
}
