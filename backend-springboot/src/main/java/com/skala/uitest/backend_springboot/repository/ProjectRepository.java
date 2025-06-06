package com.skala.uitest.backend_springboot.repository;

import com.skala.uitest.backend_springboot.domain.Project;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProjectRepository extends JpaRepository<Project, String> {
}
