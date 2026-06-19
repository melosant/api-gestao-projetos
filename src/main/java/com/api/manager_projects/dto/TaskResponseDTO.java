package com.api.manager_projects.dto;

import com.api.manager_projects.enums.ProjectStatus;
import com.api.manager_projects.enums.TaskStatus;

import java.util.UUID;

public record TaskResponseDTO(
        UUID id,
        UUID projectId,
        String title,
        String description,
        TaskStatus status
) {
}
