package com.mentormate.hackathon.persistence.repository;

import com.mentormate.hackathon.persistence.entity.Project;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * The project repository
 *
 * @author Polina Usheva
 */
@Repository
public interface ProjectRepository extends JpaRepository<Project, Long> {

    /**
     * Finds a project by a name
     *
     * @param name the name of the project
     * @return optional of the project
     */
    Optional<Project> findByName(String name);
}
