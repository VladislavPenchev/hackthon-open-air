package com.mentormate.hackathon.persistence.repository;

import com.mentormate.hackathon.persistence.entity.DayOfTimesheet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * The day of timesheet repository
 *
 * @author Polina Usheva
 */
@Repository
public interface DayOfTimesheetRepository extends JpaRepository<DayOfTimesheet, Long> {
}
