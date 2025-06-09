package com.skala.uitest.testcase.controller;

import com.skala.uitest.testcase.dto.TestCaseDto;
import com.skala.uitest.testcase.service.TestCaseService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/testcase")
@RequiredArgsConstructor
@Tag(name = "TestCase API", description = "테스트케이스 관리 API")
public class TestCaseController {

    private final TestCaseService testCaseService;

    @Operation(summary = "테스트케이스 저장", description = "FastAPI에서 생성된 테스트케이스들을 저장합니다.")
    @PostMapping
    public void saveTestCases(@RequestBody List<TestCaseDto> dtos) {
        testCaseService.saveTestCases(dtos);
    }

    @Operation(summary = "테스트케이스 목록 조회", description = "시나리오 ID 기준으로 테스트케이스 목록을 조회합니다.")
    @GetMapping
    public List<TestCaseDto> getByScenario(@RequestParam String scenarioId) {
        return testCaseService.getTestCasesByScenario(scenarioId);
    }

    @Operation(summary = "테스트케이스 수정", description = "테스트케이스 ID를 기준으로 단일 항목을 수정합니다.")
    @PatchMapping("/{id}")
    public void update(@PathVariable String id, @RequestBody TestCaseDto dto) {
        testCaseService.updateTestCase(id, dto);
    }

    @Operation(summary = "테스트케이스 삭제", description = "테스트케이스 ID를 기준으로 단일 항목을 삭제합니다.")
    @DeleteMapping("/{id}")
    public void delete(@PathVariable String id) {
        testCaseService.deleteTestCase(id);
    }
}
