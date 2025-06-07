package com.skala.uitest.scenario.repository;

import com.skala.uitest.scenario.domain.Scenario;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ScenarioRepository extends JpaRepository<Scenario, String> {
}
