package com.skala.uitest.testcase.repository;

import com.skala.uitest.testcase.domain.TestCase;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TestCaseRepository extends JpaRepository<TestCase, String> {
    List<TestCase> findAllByScenarioId(String scenarioId);
}
