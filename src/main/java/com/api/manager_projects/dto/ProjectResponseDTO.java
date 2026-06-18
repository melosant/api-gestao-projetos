package com.api.manager_projects.dto;

import com.api.manager_projects.enums.ProjectStatus;

import java.time.LocalDateTime;
import java.util.UUID;

public record ProjectResponseDTO(
        UUID id,
        UUID createdBy,
        String title,
        String description,
        ProjectStatus status
) {
}
