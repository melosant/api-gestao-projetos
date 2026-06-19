package com.api.manager_projects.service;

import com.api.manager_projects.dto.ProjectRequestDTO;
import com.api.manager_projects.dto.ProjectResponseDTO;
import com.api.manager_projects.entity.AuditTask;
import com.api.manager_projects.entity.Project;
import com.api.manager_projects.entity.Task;
import com.api.manager_projects.entity.User;
import com.api.manager_projects.repository.AuditTaskRepository;
import com.api.manager_projects.repository.ProjectRepository;
import com.api.manager_projects.repository.TaskRepository;
import com.api.manager_projects.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ProjectService {
    private final ProjectRepository projectRepository;
    private final UserRepository userRepository;
    private final TaskRepository taskRepository;
    private final AuditTaskRepository auditTaskRepository;

    @Transactional
    public ProjectResponseDTO createProject(ProjectRequestDTO dto) {
        User user = userRepository.findById(dto.createdBy())
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado!"));

        Project project = Project.builder()
                .user(user)
                .title(dto.title())
                .description(dto.description())
                .status(dto.status())
                .build();

        Project projectSaved = projectRepository.save(project);

        return toResponseDto(projectSaved);
    }

    @Transactional(readOnly = true)
    public List<ProjectResponseDTO> getAllProjects() {
        return projectRepository.findAll().stream().map(this::toResponseDto).toList();
    }

    @Transactional(readOnly = true)
    public List<ProjectResponseDTO> getAllProjectsByUser(UUID userId) {
        return projectRepository.findByUserId(userId)
                .stream()
                .map(this::toResponseDto)
                .toList();
    }

    @Transactional(readOnly = true)
    public ProjectResponseDTO getProjectById(UUID projectId) {
        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new RuntimeException("Projeto não encontrado!"));

        return toResponseDto(project);
    }

    @Transactional
    public ProjectResponseDTO updateProject(UUID projectId, ProjectRequestDTO dto) {
        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new RuntimeException("Projeto não encontrado!"));

        project.setTitle(dto.title());
        project.setDescription(dto.description());
        project.setStatus(dto.status());

        Project projectSaved = projectRepository.save(project);

        return toResponseDto(projectSaved);
    }

    @Transactional
    public void deleteProject(UUID projectId) {
        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new RuntimeException("Projeto não encontrado!"));
        List<Task> tasksSearched = taskRepository.findByProjectId(projectId);
        List<AuditTask> auditsTasksSearched = auditTaskRepository.findByProjectId(projectId);

        auditTaskRepository.deleteAll(auditsTasksSearched);
        taskRepository.deleteAll(tasksSearched);
        projectRepository.delete(project);
    }

    public ProjectResponseDTO toResponseDto(Project entity) {
        return new ProjectResponseDTO(
                entity.getId(),
                entity.getUser().getId(),
                entity.getTitle(),
                entity.getDescription(),
                entity.getStatus()
        );
    }
}
