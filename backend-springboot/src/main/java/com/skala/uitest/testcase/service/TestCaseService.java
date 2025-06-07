package com.skala.uitest.testcase.service;

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

    // 전체 시나리오 대상 생성
    public List<TestCase> createTestCasesBulk(List<TestCaseDto> dtos) {
        return testCaseRepository.saveAll(dtos.stream().map(this::toEntity).toList());
    }

    // 특정 시나리오에 대한 생성
    public List<TestCase> createTestCasesByScenario(List<TestCaseDto> dtos, String scenarioId) {
        List<TestCase> entities = dtos.stream()
                .map(dto -> {
                    dto.setScenarioId(scenarioId);
                    return toEntity(dto);
                })
                .collect(Collectors.toList());
        return testCaseRepository.saveAll(entities);
    }

    // 시나리오별 조회
    public List<TestCaseDto> getTestCasesByScenario(String scenarioId) {
        return testCaseRepository.findAllByScenarioId(scenarioId).stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    // 단건 수정
    public TestCase updateTestCase(String id, TestCaseDto dto) {
        Optional<TestCase> optional = testCaseRepository.findById(id);
        if (optional.isEmpty()) throw new IllegalArgumentException("해당 ID 없음: " + id);

        TestCase tc = optional.get();
        tc.setTestcaseName(dto.getTestcaseName());
        tc.setTestcasePreFlow(dto.getTestcasePreFlow());
        tc.setTestcaseInputData(dto.getTestcaseInputData());
        tc.setTestcaseExpected(dto.getTestcaseExpected());
        tc.setIsSuccess(dto.getIsSuccess());
        return testCaseRepository.save(tc);
    }

    // 삭제
    public void deleteTestCase(String id) {
        testCaseRepository.deleteById(id);
    }

    private TestCaseDto toDto(TestCase tc) {
        return TestCaseDto.builder()
                .testcaseId(tc.getTestcaseId())
                .scenarioId(tc.getScenarioId())
                .testcaseName(tc.getTestcaseName())
                .testcasePreFlow(tc.getTestcasePreFlow())
                .testcaseInputData(tc.getTestcaseInputData())
                .testcaseExpected(tc.getTestcaseExpected())
                .isSuccess(tc.getIsSuccess())
                .createdAt(tc.getCreatedAt())
                .build();
    }

    private TestCase toEntity(TestCaseDto dto) {
        return TestCase.builder()
                .testcaseId(dto.getTestcaseId())
                .scenarioId(dto.getScenarioId())
                .testcaseName(dto.getTestcaseName())
                .testcasePreFlow(dto.getTestcasePreFlow())
                .testcaseInputData(dto.getTestcaseInputData())
                .testcaseExpected(dto.getTestcaseExpected())
                .isSuccess(dto.getIsSuccess())
                .createdAt(dto.getCreatedAt())
                .build();
    }
}
