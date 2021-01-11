package com.mentormate.hackathon.service;

import com.mentormate.hackathon.controller.handler.exception.NotFoundException;
import com.mentormate.hackathon.persistence.entity.Task;
import com.mentormate.hackathon.persistence.repository.TaskRepository;
import com.mentormate.hackathon.service.dto.response.TaskResponseDTO;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Represents the task service. Contains all of the business logic.
 *
 * @author Polina Usheva
 */
@Service
@RequiredArgsConstructor
public class TaskService {

    private final TaskRepository taskRepository;
    private final ModelMapper modelMapper;

    /**
     * Finds all of the tasks
     *
     * @return list of tasks
     */
    public List<Task> findAll() {
        return taskRepository.findAll();
    }

    /**
     * Finds a task by an enum name
     *
     * @param name the name of the task
     * @return the task
     */
    public Task findByName(String name) {

        Optional<Task> taskOptional = taskRepository.findByName(name);

        if (taskOptional.isEmpty()) {
            throw new NotFoundException(String.format("Task with name %s - not found ", name));
        }

        return taskOptional.get();
    }

    /**
     * Gets a list of all of the tasks
     *
     * @return list of response dto's
     */
    public List<TaskResponseDTO> getAll() {

        return taskRepository.findAll()
                .stream()
                .map(feature -> modelMapper.map(feature, TaskResponseDTO.class))
                .collect(Collectors.toUnmodifiableList());
    }

    /**
     * Seed all tasks in database
     */
    public void seedTasks() {
        if (taskRepository.findAll().isEmpty()) {

            Task defaultTask = new Task("");
            taskRepository.save(defaultTask);
            
            Task administrative = new Task("Administrative");
            taskRepository.save(administrative);

            Task benchTime = new Task("Bench time");
            taskRepository.save(benchTime);

            Task learning = new Task("Learning");
            taskRepository.save(learning);

            Task research = new Task("Research");
            taskRepository.save(research);
        }
    }
    
}
