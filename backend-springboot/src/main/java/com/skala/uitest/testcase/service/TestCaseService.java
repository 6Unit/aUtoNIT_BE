package com.skala.uitest.testcase.service;

import jakarta.transaction.Transactional;

import com.skala.uitest.scenario.domain.Scenario;
import com.skala.uitest.scenario.repository.ScenarioRepository;
import com.skala.uitest.testcase.domain.TestCase;
import com.skala.uitest.testcase.dto.TestCaseCreateRequestDto;
import com.skala.uitest.testcase.dto.TestCaseDetailDto;
import com.skala.uitest.testcase.dto.TestCaseDto;
import com.skala.uitest.testcase.dto.TestCaseListDto;
import com.skala.uitest.testcase.dto.TestCaseUpdateRequestDto;
import com.skala.uitest.testcase.repository.TestCaseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
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

    // ✅ 시나리오별 조회 (테스트케이스 ID + 이름만 반환)
    public List<TestCaseListDto> getTestCasesByScenario(String scenarioId) {
        return testCaseRepository.findAllByScenario_ScenarioId(scenarioId).stream()
                .map(tc -> TestCaseListDto.builder()
                        .testcaseId(tc.getTestcaseId())
                        .testcaseName(tc.getTestcaseName())
                        .build())
                .collect(Collectors.toList());
    }

    // ✅ 단건 조회
    @Transactional
    public TestCaseDetailDto getTestCaseDetailById(String id) {
        TestCase tc = testCaseRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 테스트케이스 없음: " + id));
        return TestCaseDetailDto.builder()
                .testcaseId(tc.getTestcaseId())
                .testcaseName(tc.getTestcaseName())
                .uiFlow(tc.getUiFlow())
                .inputData(tc.getInputData())
                .expectedResult(tc.getExpectedResult())
                .build();
    }
    
    // ✅ 단건 생성
    public TestCaseDto createTestCaseFromScenario(String scenarioId, TestCaseCreateRequestDto dto) {
        Scenario scenario = scenarioRepository.findById(scenarioId)
            .orElseThrow(() -> new IllegalArgumentException("시나리오 ID가 존재하지 않습니다: " + scenarioId));
    
        // 시나리오에 속한 테스트케이스 개수 조회 → 다음 번호 계산
        long count = testCaseRepository.countByScenario_ScenarioId(scenarioId);
        String nextId = String.format("tc-%03d", count + 1);  // tc-001, tc-002 등

        TestCase entity = TestCase.builder()
            .testcaseId(nextId)
            .scenario(scenario)
            .testcaseName(dto.getTestcaseName())
            .uiFlow(dto.getUiFlow())
            .inputData(dto.getInputData())
            .expectedResult(dto.getExpectedResult())
            .isSuccess(false)
            .createdAt(LocalDateTime.now())
            .build();
    
        TestCase saved = testCaseRepository.save(entity);
        return TestCaseDto.fromEntity(saved);
    }
    
    

    // ✅ 단건 수정
    public TestCaseUpdateRequestDto updateTestCase(String id, TestCaseUpdateRequestDto dto) {
        TestCase tc = testCaseRepository.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("해당 ID 없음: " + id));
    
        tc.setTestcaseName(dto.getTestcaseName());
        tc.setUiFlow(dto.getUiFlow());
        tc.setInputData(dto.getInputData());
        tc.setExpectedResult(dto.getExpectedResult());
    
        TestCase updated = testCaseRepository.save(tc);
        return TestCaseUpdateRequestDto.fromEntity(updated); // ✅ Dto로 변환해서 반환
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
                .uiFlow(dto.getUiFlow())
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
                .uiFlow(tc.getUiFlow())
                .inputData(tc.getInputData())
                .expectedResult(tc.getExpectedResult())
                .isSuccess(tc.getIsSuccess())
                .createdAt(tc.getCreatedAt())
                .build();
    }
}
