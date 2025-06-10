package com.skala.uitest.testcase.service;

import com.skala.uitest.scenario.repository.ScenarioRepository;
import com.skala.uitest.testcase.domain.TestCase;
import com.skala.uitest.testcase.dto.TestCaseDto;
import com.skala.uitest.testcase.repository.TestCaseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TestCaseService {

    private final TestCaseRepository testCaseRepository;
    private final ScenarioRepository scenarioRepository;

    // ✅ 저장 (FastAPI 생성 결과 저장)
    public List<TestCase> saveTestCases(List<TestCaseDto> dtos) {
        List<TestCase> entities = dtos.stream()
                .map(this::toEntity)
                .toList();
        return testCaseRepository.saveAll(entities);
    }

    // ✅ 시나리오별 조회
    public List<TestCaseDto> getTestCasesByScenario(String scenarioId) {
        return testCaseRepository.findAllByScenario_ScenarioId(scenarioId).stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    // ✅ 단건 수정
    public TestCase updateTestCase(String id, TestCaseDto dto) {
        Optional<TestCase> optional = testCaseRepository.findById(id);
        if (optional.isEmpty()) throw new IllegalArgumentException("해당 ID 없음: " + id);

        TestCase tc = optional.get();
        tc.setTestcaseName(dto.getTestcaseName());
        tc.setPreCondition(dto.getPreCondition());
        tc.setInputData(dto.getInputData());
        tc.setExpectedResult(dto.getExpectedResult());
        tc.setIsSuccess(dto.getIsSuccess());
        return testCaseRepository.save(tc);
    }

    // ✅ 삭제
    public void deleteTestCase(String id) {
        testCaseRepository.deleteById(id);
    }

    // DTO → Entity
    private TestCase toEntity(TestCaseDto dto) {
        return TestCase.builder()
                .testcaseId(dto.getTestcaseId())
                .scenario(scenarioRepository.getReferenceById(dto.getScenarioId()))
                .testcaseName(dto.getTestcaseName())
                .preCondition(dto.getPreCondition())
                .inputData(dto.getInputData())
                .expectedResult(dto.getExpectedResult())
                .isSuccess(dto.getIsSuccess())
                .createdAt(dto.getCreatedAt())
                .build();
    }

    // Entity → DTO
    private TestCaseDto toDto(TestCase tc) {
        return TestCaseDto.builder()
                .testcaseId(tc.getTestcaseId())
                .scenarioId(tc.getScenario().getScenarioId())
                .testcaseName(tc.getTestcaseName())
                .preCondition(tc.getPreCondition())
                .inputData(tc.getInputData())
                .expectedResult(tc.getExpectedResult())
                .isSuccess(tc.getIsSuccess())
                .createdAt(tc.getCreatedAt())
                .build();
    }
}
