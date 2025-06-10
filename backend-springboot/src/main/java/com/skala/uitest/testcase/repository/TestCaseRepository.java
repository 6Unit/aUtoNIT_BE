package com.skala.uitest.testcase.repository;

import com.skala.uitest.testcase.domain.TestCase;
import org.springframework.data.jpa.repository.JpaRepository;
// import org.springframework.data.jpa.repository.Query;
// import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TestCaseRepository extends JpaRepository<TestCase, String> {

    // ✅ 연관 관계 기반 자동 쿼리
    List<TestCase> findAllByScenario_ScenarioId(String scenarioId);

    // ✅ JPQL에서 경로 기반 접근
    // @Query("""
    // SELECT COUNT(tc) > 0 
    // FROM TestCase tc
    // WHERE tc.scenario.project.projectId = :projectId
    //   AND tc.createdAt IS NOT NULL
    // """)
    // boolean hasCreatedTestcase(@Param("projectId") Long projectId);
}
