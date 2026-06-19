package com.api.manager_projects.repository;

import com.api.manager_projects.entity.AuditTask;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface AuditTaskRepository extends JpaRepository<AuditTask, UUID> {
    Optional<AuditTask> findByTaskId(UUID taskId);

    @Query("SELECT at FROM audit_tasks at WHERE at.task.project.id = :projectId")
    List<AuditTask> findByProjectId(UUID projectId);
}
