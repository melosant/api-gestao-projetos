package com.api.manager_projects.service;

import com.api.manager_projects.dto.TaskRequestDTO;
import com.api.manager_projects.dto.TaskResponseDTO;
import com.api.manager_projects.entity.AuditTask;
import com.api.manager_projects.entity.Project;
import com.api.manager_projects.entity.Task;
import com.api.manager_projects.enums.TaskStatus;
import com.api.manager_projects.repository.AuditTaskRepository;
import com.api.manager_projects.repository.ProjectRepository;
import com.api.manager_projects.repository.TaskRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TaskService {
    private final TaskRepository taskRepository;
    private final AuditTaskRepository auditTaskRepository;
    private final ProjectRepository projectRepository;

    @Transactional
    public TaskResponseDTO createTask(TaskRequestDTO dto) {
        Project project = projectRepository.findById(dto.projectId())
                .orElseThrow(() -> new RuntimeException("Projeto não encontrado!"));

        Task task = Task.builder()
                .project(project)
                .title(dto.title())
                .description(dto.description())
                .status(dto.status())
                .build();

        Task taskSaved = taskRepository.save(task);

        AuditTask auditTask = AuditTask.builder()
                .user(project.getUser())
                .task(taskSaved)
                .build();

        auditTaskRepository.save(auditTask);

        return toResponseDTO(taskSaved);
    }

    @Transactional(readOnly = true)
    public List<TaskResponseDTO> getAllTasks() {
        return taskRepository.findAll().stream().map(this::toResponseDTO).toList();
    }

    @Transactional(readOnly = true)
    public List<TaskResponseDTO> getAllTasksByUserId(UUID userId) {
        return taskRepository.findByUserId(userId)
                .stream()
                .map(this::toResponseDTO)
                .toList();
    }

    @Transactional(readOnly = true)
    public TaskResponseDTO getTaskByTaskId(UUID taskId) {
        Task task = taskRepository.findById(taskId).
                orElseThrow(() -> new RuntimeException("Tarefa não encontrada!"));

        return toResponseDTO(task);
    }

    @Transactional(readOnly = true)
    public List<TaskResponseDTO> getTasksByProjectId(UUID projectId) {
        return taskRepository.findByProjectId(projectId)
                .stream()
                .map(this::toResponseDTO)
                .toList();
    }

    @Transactional
    public TaskResponseDTO updateTaskStatusToStarted(UUID taskId) {
        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new RuntimeException("Tarefa não encontrada!"));
        AuditTask auditTask = auditTaskRepository.findByTaskId(taskId)
                .orElseThrow(() -> new RuntimeException("Auditoria de Tarefa não encontrada!"));

        auditTask.setStartTime(LocalDateTime.now());
        task.setStatus(TaskStatus.STARTED);

        auditTaskRepository.save(auditTask);
        Task taskUpdated = taskRepository.save(task);

        return toResponseDTO(taskUpdated);
    }

    @Transactional
    public TaskResponseDTO updateTaskStatusToDone(UUID taskId) {
        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new RuntimeException("Tarefa não encontrada!"));
        AuditTask auditTask = auditTaskRepository.findByTaskId(taskId)
                .orElseThrow(() -> new RuntimeException("Auditoria de Tarefa não encontrada!"));

        auditTask.setEndTime(LocalDateTime.now());
        task.setStatus(TaskStatus.DONE);

        auditTaskRepository.save(auditTask);
        Task taskUpdated = taskRepository.save(task);

        return toResponseDTO(taskUpdated);
    }

    @Transactional
    public TaskResponseDTO updateTask(UUID taskId, TaskRequestDTO dto) {
        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new RuntimeException("Tarefa não encontrada!"));

        task.setTitle(dto.title());
        task.setDescription(dto.description());
        task.setStatus(dto.status());

        Task taskUpdated = taskRepository.save(task);

        return toResponseDTO(taskUpdated);
    }

    @Transactional
    public void deleteTask(UUID taskId) {
        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new RuntimeException("Tarefa não encontrada!"));
        AuditTask auditTask = auditTaskRepository.findByTaskId(taskId)
                .orElseThrow(() -> new RuntimeException("Auditoria de Tarefa não encontrada!"));

        auditTaskRepository.delete(auditTask);
        taskRepository.delete(task);
    }

    public TaskResponseDTO toResponseDTO(Task entity) {
        return new TaskResponseDTO(
                entity.getId(),
                entity.getProject().getId(),
                entity.getTitle(),
                entity.getDescription(),
                entity.getStatus()
        );
    }
}
