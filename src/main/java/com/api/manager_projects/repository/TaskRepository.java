package com.api.manager_projects.repository;

import com.api.manager_projects.entity.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.UUID;

public interface TaskRepository extends JpaRepository<Task, UUID> {
    List<Task> findByProjectId(UUID projectId);

    @Query("SELECT t FROM tasks t " +
           "WHERE t.project.user.id = :userId")
    List<Task> findByUserId(UUID userId);
}
