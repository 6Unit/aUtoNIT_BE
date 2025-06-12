package com.skala.uitest.scenario.repository;

import java.util.List;
import com.skala.uitest.scenario.domain.Scenario;
import org.springframework.data.jpa.repository.JpaRepository;
// import org.springframework.data.repository.query.Param;
// import org.springframework.data.jpa.repository.Query;

public interface ScenarioRepository extends JpaRepository<Scenario, String> {
    List<Scenario> findAllByProject_ProjectId(Long projectId);
    long countByProject_ProjectId(Long projectId);
}
