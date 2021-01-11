package com.mentormate.hackathon.service;

import com.mentormate.hackathon.persistence.entity.User;
import com.mentormate.hackathon.persistence.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * {@link UserService} that contains all business logic about user
 * <p>
 * Created by Vladislav Penchev on 2020/10/01
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;

    /**
     * {@inheritDoc}
     *
     * @throws UsernameNotFoundException when {@link User} with username not found
     */
    @Override
    public User loadUserByUsername(String email) throws UsernameNotFoundException {
        return this.checkIfUserExist(email);
    }
    
    public User checkIfUserExist(String email) {
        User user = userRepository.findByEmail(email).orElse(null);
        if (user == null) {
            log.info("User with email {} not found", email);
            throw new UsernameNotFoundException(String.format("User with email %s not found", email));
        }
        log.info("User with email {} login successfully", email);
        return user;
    }
}

