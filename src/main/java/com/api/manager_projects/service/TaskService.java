package com.api.manager_projects.service;

import com.api.manager_projects.dto.TaskRequestDTO;
import com.api.manager_projects.dto.TaskResponseDTO;
import com.api.manager_projects.entity.Project;
import com.api.manager_projects.entity.Task;
import com.api.manager_projects.repository.ProjectRepository;
import com.api.manager_projects.repository.TaskRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TaskService {
    private final TaskRepository taskRepository;
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

        return toResponseDTO(taskSaved);
    }

    @Transactional(readOnly = true)
    public List<TaskResponseDTO> getAllTasks() {
        return taskRepository.findAll().stream().map(this::toResponseDTO).toList();
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
