package com.api.manager_projects.dto;

import com.api.manager_projects.enums.ProjectStatus;

import java.util.UUID;

public record ProjectRequestDTO(
        UUID createdBy,
        String title,
        String description,
        ProjectStatus status
) {
}
