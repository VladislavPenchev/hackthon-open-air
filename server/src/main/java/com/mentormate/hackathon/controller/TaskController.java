package com.mentormate.hackathon.controller;

import com.mentormate.hackathon.service.TaskService;
import com.mentormate.hackathon.service.dto.response.TaskResponseDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * The Task controller.
 *
 * <p>This controller is responsible for all of the CRUD operations of the Task entity
 *
 * @author Polina Usheva
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/tasks")
@CrossOrigin(origins = "http://localhost:3000")
public class TaskController {

    private final TaskService taskService;

    @Operation(description = "This request is used for getting all of the tasks ", tags = {"Tasks"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Returns all of the tasks"),
            @ApiResponse(responseCode = "500", description = "Internal Server Error")})
    @GetMapping
    public ResponseEntity<List<TaskResponseDTO>> getAllTasks() {

        return ResponseEntity.ok(taskService.getAll());
    }
}
