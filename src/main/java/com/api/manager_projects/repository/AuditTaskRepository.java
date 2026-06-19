package com.api.manager_projects.repository;

import com.api.manager_projects.entity.AuditTask;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface AuditTaskRepository extends JpaRepository<AuditTask, UUID> {
    Optional<AuditTask> findByTaskId(UUID taskId);
}
