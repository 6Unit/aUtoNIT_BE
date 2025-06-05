package com.skala.uitest.backend_springboot.repository;

import com.skala.uitest.backend_springboot.domain.Scenario;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ScenarioRepository extends JpaRepository<Scenario, Long> {
}
