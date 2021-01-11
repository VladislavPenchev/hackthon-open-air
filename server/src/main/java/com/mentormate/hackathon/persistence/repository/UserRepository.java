package com.mentormate.hackathon.persistence.repository;

import com.mentormate.hackathon.persistence.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * {@link UserRepository} extends {@link JpaRepository} that it help us easy to operate with {@link User} data in database
 * <p>
 * Created by Vladislav Penchev on 2020/09/30
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    /**
     * @param email check if exists
     * @return {@link User} that is saved in our database
     */
    Optional<User> findByEmail(String email);
}

