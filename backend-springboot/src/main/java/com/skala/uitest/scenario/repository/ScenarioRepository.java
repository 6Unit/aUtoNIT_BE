package com.skala.uitest.scenario.repository;

import com.skala.uitest.scenario.domain.Scenario;
import org.springframework.data.jpa.repository.JpaRepository;
// import org.springframework.data.repository.query.Param;
// import org.springframework.data.jpa.repository.Query;

public interface ScenarioRepository extends JpaRepository<Scenario, String> {
    // @Query("SELECT COUNT(s) > 0 FROM Scenario s WHERE s.project.projectId = :projectId AND s.createdAt IS NOT NULL")
    // boolean hasCreatedScenario(@Param("projectId") Long projectId);
}
