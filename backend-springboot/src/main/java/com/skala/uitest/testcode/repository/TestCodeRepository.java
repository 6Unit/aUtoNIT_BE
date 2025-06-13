package com.skala.uitest.testcode.repository;

import com.skala.uitest.testcode.domain.TestCode;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TestCodeRepository extends JpaRepository<TestCode, String> {
    Optional<TestCode> findByTestCase_TestcaseId(String testcaseId);
}
