package com.skala.uitest.project.repository;

import com.skala.uitest.project.domain.Project;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProjectRepository extends JpaRepository<Project, Long> {
    Optional<Project> findByProjectNameAndProjectCode(String projectName, String projectCode);
}
