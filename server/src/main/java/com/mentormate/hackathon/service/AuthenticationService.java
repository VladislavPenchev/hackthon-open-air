package com.mentormate.hackathon.service;

import com.mentormate.hackathon.controller.handler.exception.EntityAlreadyExists;
import com.mentormate.hackathon.persistence.entity.Role;
import com.mentormate.hackathon.persistence.entity.RoleType;
import com.mentormate.hackathon.persistence.entity.User;
import com.mentormate.hackathon.persistence.repository.RoleRepository;
import com.mentormate.hackathon.persistence.repository.UserRepository;
import com.mentormate.hackathon.service.dto.request.LoginRequestDTO;
import com.mentormate.hackathon.service.dto.request.RegisterRequestDTO;
import com.mentormate.hackathon.service.dto.response.JwtResponseDTO;
import com.mentormate.hackathon.service.dto.response.RegisterResponseDTO;
import com.mentormate.hackathon.utils.JwtUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * {@link AuthenticationService} that contains all business logic about user authentication
 * <p>
 * Created by Vladislav Penchev on 2020/09/30
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final ModelMapper modelMapper;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtUtils jwtUtils;

    private static final Map<RoleType, Role> ROLES = new HashMap<>();

    /**
     * Seed all roles in database when {@link AuthenticationService} is add in spring context.
     */
    @PostConstruct
    protected void postConstruct() {
        this.seedRoles();
        roleRepository.findAll()
                .forEach(role -> ROLES.put(role.getName(), role));
    }

    /**
     * Used for create user registration. Assign role manager if this is the first created user
     *
     * @param registerRequestDTO used for user register
     */
    public RegisterResponseDTO signUp(RegisterRequestDTO registerRequestDTO) {
        Role role = ROLES.get(RoleType.ROLE_REGULAR);
        if (userRepository.findByEmail(registerRequestDTO.getEmail()).isPresent()) {
            throw new EntityAlreadyExists(String.format("Username %s already exist", registerRequestDTO.getEmail()));
        }
        User user = new User(registerRequestDTO.getEmail(), passwordEncoder.encode(registerRequestDTO.getPassword()), Set.of(role));
        user = userRepository.save(user);
        log.info("Created user with username: {}", user.getUsername());
        return modelMapper.map(user, RegisterResponseDTO.class);
    }

    /**
     * Used for sign in user.
     *
     * @param loginRequestDTO used for user login
     * @return {@link JwtResponseDTO} contains JSON Web Token
     */
    public JwtResponseDTO signIn(LoginRequestDTO loginRequestDTO) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequestDTO.getEmail(), loginRequestDTO.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);
        return new JwtResponseDTO(jwt, authentication.getName());
    }

    /**
     * Used for user logout.
     *
     * @return {@link Map} with key message and value contains information about successful logout
     */
    public Map<String, String> logout() {
        SecurityContextHolder.clearContext();
        return Map.of("message", "logout successfully");
    }

    /**
     * Seed all roles in database
     */
    private void seedRoles() {
        if (roleRepository.findAll().isEmpty()) {
            Role roleRegular = new Role(RoleType.ROLE_REGULAR);
            roleRepository.save(roleRegular);
        }
    }

}
