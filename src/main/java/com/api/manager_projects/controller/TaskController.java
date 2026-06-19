package com.api.manager_projects.controller;

import com.api.manager_projects.dto.TaskRequestDTO;
import com.api.manager_projects.dto.TaskResponseDTO;
import com.api.manager_projects.service.TaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/tasks")
@RequiredArgsConstructor
public class TaskController {
    private final TaskService taskService;

    @PostMapping
    public ResponseEntity<TaskResponseDTO> createTask(@RequestBody TaskRequestDTO dto) {
        TaskResponseDTO taskCreated = taskService.createTask(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(taskCreated);
    }

    @GetMapping
    public ResponseEntity<List<TaskResponseDTO>> getAllTasks() {
        List<TaskResponseDTO> tasksSearched = taskService.getAllTasks();
        return ResponseEntity.ok(tasksSearched);
    }

    @PatchMapping("/task/{taskId}/start")
    public ResponseEntity<TaskResponseDTO> updateStatusTaskToStarted(@PathVariable UUID taskId) {
        TaskResponseDTO taskUpdated = taskService.updateTaskStatusToStarted(taskId);
        return ResponseEntity.ok(taskUpdated);
    }

    @PatchMapping("/task/{taskId}/done")
    public ResponseEntity<TaskResponseDTO> updateStatusTaskToDone(@PathVariable UUID taskId) {
        TaskResponseDTO taskUpdated = taskService.updateTaskStatusToDone(taskId);
        return ResponseEntity.ok(taskUpdated);
    }

    @PutMapping("/task/{taskId}")
    public ResponseEntity<TaskResponseDTO> updateTask(@PathVariable UUID taskId,
                                                      @RequestBody TaskRequestDTO dto) {
        TaskResponseDTO taskUpdated = taskService.updateTask(taskId, dto);
        return ResponseEntity.ok(taskUpdated);
    }

    @DeleteMapping("/task/{taskId}")
    public ResponseEntity<Void> deleteTask(@PathVariable UUID taskId) {
        taskService.deleteTask(taskId);
        return ResponseEntity.noContent().build();
    }
}
