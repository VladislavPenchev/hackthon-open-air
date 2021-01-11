package com.mentormate.hackathon.persistence.repository;

import com.mentormate.hackathon.persistence.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * {@link RoleRepository} extends {@link JpaRepository} that it helps us easy to operate with {@link Role} data in database
 * <p>
 * Created by Vladislav Penchev on 2020/09/30
 */
@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
}

