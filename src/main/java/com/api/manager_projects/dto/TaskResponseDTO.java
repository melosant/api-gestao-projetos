package com.api.manager_projects.dto;

import com.api.manager_projects.enums.TaskStatus;

import java.time.LocalDateTime;
import java.util.UUID;

public record TaskResponseDTO(
        UUID id,
        UUID projectId,
        String projectName,
        String title,
        String description,
        TaskStatus status,
        LocalDateTime updatedAt,
        LocalDateTime createdAt
) {
}
