package com.api.manager_projects.controller;

import com.api.manager_projects.dto.ProjectRequestDTO;
import com.api.manager_projects.dto.ProjectResponseDTO;
import com.api.manager_projects.service.ProjectService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/projects")
@RequiredArgsConstructor
public class ProjectController {
    private final ProjectService projectService;

    @PostMapping
    public ResponseEntity<ProjectResponseDTO> createProject(@RequestBody ProjectRequestDTO dto) {
        ProjectResponseDTO responseDTO = projectService.createProject(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(responseDTO);
    }

    @GetMapping
    public ResponseEntity<List<ProjectResponseDTO>> getAllProjects() {
        List<ProjectResponseDTO> projectsSearched = projectService.getAllProjects();
        return ResponseEntity.ok(projectsSearched);
    }

    @GetMapping("/board/{userId}")
    public ResponseEntity<List<ProjectResponseDTO>> getAllProjectsByUser(@PathVariable UUID userId) {
        List<ProjectResponseDTO> projectsSearched = projectService.getAllProjectsByUser(userId);
        return ResponseEntity.ok(projectsSearched);
    }

    @GetMapping("/project/{projectId}")
    public ResponseEntity<ProjectResponseDTO> getProjectById(@PathVariable UUID projectId) {
        ProjectResponseDTO projectSearched = projectService.getProjectById(projectId);
        return ResponseEntity.ok(projectSearched);
    }

    @PutMapping("/project/{projectId}")
    public ResponseEntity<ProjectResponseDTO> updateProject(@PathVariable UUID projectId,
                                                            @RequestBody ProjectRequestDTO dto) {
        ProjectResponseDTO responseDTO = projectService.updateProject(projectId, dto);
        return ResponseEntity.ok(responseDTO);
    }

    @DeleteMapping("/project/{projectId}")
    public ResponseEntity<Void> deleteProject(@PathVariable UUID projectId) {
        projectService.deleteProject(projectId);
        return ResponseEntity.noContent().build();
    }
}
