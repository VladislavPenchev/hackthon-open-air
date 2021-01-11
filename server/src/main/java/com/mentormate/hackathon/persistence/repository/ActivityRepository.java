package com.mentormate.hackathon.persistence.repository;

import com.mentormate.hackathon.persistence.entity.Activity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * The activity repository
 *
 * @author Polina Usheva
 */
@Repository
public interface ActivityRepository extends JpaRepository<Activity, Long> {
}
